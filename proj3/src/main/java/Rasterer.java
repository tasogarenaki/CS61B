import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    /**
     * The root upper left/lower right longitudes and latitudes represent the bounding box of
     * the root tile (depth 0).
     * Longitude == x-axis; latitude == y-axis.
     */
    private static final Coordinate ROOT_UL = new Coordinate(MapServer.ROOT_ULLON, MapServer.ROOT_ULLAT);
    private static final Coordinate ROOT_LR = new Coordinate(MapServer.ROOT_LRLON, MapServer.ROOT_LRLAT);

    /** Each tile is 256x256 pixels. */
    private static final int TILE_SIZE = MapServer.TILE_SIZE;

    /** Min and Max Zoom levels for rendering image tiles. */
    private static final int DEPTH_MIN = 0, DEPTH_MAX = 7;

    /** The longitudinal distance per pixel (LonDPP) for each image depth. */
    private static final double[] LONDPP_AT_DEPTH = new double[8];
    static {
        double lonDppFactor = lonDPP(ROOT_LR.lon, ROOT_UL.lon, TILE_SIZE);
        // double lonDppFactor = (ROOT_LR.lon - ROOT_UL.lon) / TILE_SIZE;
        for (int depth = 0; depth < 8; depth++) {
            LONDPP_AT_DEPTH[depth] = lonDppFactor / numTilesAtDepth(depth);
        }
    }

    /** Names of the query parameters. */
    private static final String QUERY_PARAM_ULLON = "ullon", QUERY_PARAM_ULLAT = "ullat", QUERY_PARAM_LRLON = "lrlon",
            QUERY_PARAM_LRLAT = "lrlat", QUERY_PARAM_WIDTH = "w", QUERY_PARAM_HEIGHT = "h";


    /** Names of the raster parameters. */
    private static final String RASTER_RENDER_GRID = "render_grid", RASTER_ULLON = "raster_ul_lon",
            RASTER_ULLAT = "raster_ul_lat", RASTER_LRLON = "raster_lr_lon", RASTER_LRLAT = "raster_lr_lat",
            RASTER_DEPTH = "depth", RASTER_QUERY_SUCCESS = "query_success";

    /** File format. */
    private static final String IMAGE_FILE_FORMAT = ".png";

    public Rasterer() { }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     *
     * File name format: dD_xk_yk, where D represents depth, and k equals 2^D - 1.
     * The number of tiles per side is 2^D.
     * tileLonLength   : The length in longitude units of a tile.
     * tileLatLength   : The length in latitude units of a tile.
     * xIndexLeft      : The leftmost index of a tile in the x direction (longitude).
     * xIndexRight     : The rightmost index of a tile in the x direction (longitude).
     * yIndexUpper     : The topmost index of a tile in the y direction (latitude).
     * yIndexLower     : The bottommost index of a tile in the y direction (latitude).
     * The implementation with the assistance of this image:
     * https://sp18.datastructur.es/materials/proj/proj3/rastering_example.png
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();

        double ulLon = params.get(QUERY_PARAM_ULLON);
        double ulLat = params.get(QUERY_PARAM_ULLAT);
        double lrLon = params.get(QUERY_PARAM_LRLON);
        double lrLat = params.get(QUERY_PARAM_LRLAT);
        double width = params.get(QUERY_PARAM_WIDTH);

        double querLonDPP = lonDPP(lrLon, ulLon, (int) width);
        int depth = getDepth(querLonDPP);
        boolean success = validateCoordinates(ulLon, ulLat, lrLon, lrLat);

        if (!success) {
            results.put(RASTER_QUERY_SUCCESS, success);
            results.put(RASTER_RENDER_GRID, null);
            results.put(RASTER_DEPTH, -1);
            results.put(RASTER_ULLON, -1);
            results.put(RASTER_ULLAT, -1);
            results.put(RASTER_LRLON, -1);
            results.put(RASTER_LRLAT, -1);
        } else {
            Coordinate ul = new Coordinate(ulLon, ulLat);
            Coordinate lr = new Coordinate(lrLon, lrLat);
            String[][] renderGrid = getRenderGrid(ul.lon, ul.lat, lr.lon, lr.lat, depth);
            double[] boundingBox = calculateBoundingBox(ul.lon, ul.lat, lr.lon, lr.lat, depth);
            results.put(RASTER_QUERY_SUCCESS, success);
            results.put(RASTER_RENDER_GRID, renderGrid);
            results.put(RASTER_DEPTH, depth);
            results.put(RASTER_ULLON, boundingBox[0]);
            results.put(RASTER_ULLAT, boundingBox[1]);
            results.put(RASTER_LRLON, boundingBox[2]);
            results.put(RASTER_LRLAT, boundingBox[3]);
        }
        return results;
    }

    /**
     * Calculates the number of tiles at the given depth,
     * where each increase in depth results in a doubling of the tiles per side.
     */
    private static int numTilesAtDepth(int depth) {
        return (int) Math.pow(2, depth);
    }

    /**
     * Calculate the longitudinal distance per pixel (LonDPP).
     * @param lrlon lower right longitude
     * @param ullon upper left longitude
     * @param width width of the image in pixels
     */
    private static double lonDPP(double lrlon, double ullon, int width) {
        return Math.abs(lrlon - ullon) / width;
    }

    /**
     * Choose the parparite Depth eventuell LonDPP.
     *
     * Choose the highest available LonDPP that is less than or equal to the requested LonDPP.
     * If the requested LonDPP is lower than the lowest available LonDPP (i.e., depth 7 images),
     * use the lowest available LonDPP.
     * @param lonDPP Query Box LonDPP
     */
    private int getDepth(double lonDPP) {
        int selectedDepth = DEPTH_MAX;
        for (int depth = DEPTH_MAX; depth >= DEPTH_MIN; depth--) {
            if (LONDPP_AT_DEPTH[depth] <= lonDPP) {
                selectedDepth = depth;
            }
        }

        selectedDepth = Math.max(selectedDepth, DEPTH_MIN);
        return selectedDepth;
    }

    /**
     * Check if the query box has valid coordinates.
     * @return true if the query box overlaps the raster box.
     */
    private boolean validateCoordinates(double ulLon, double ulLat, double lrLon, double lrLat) {
        return (ulLon < lrLon && lrLat < ulLat) &&
                (ulLon < ROOT_LR.lon && ROOT_UL.lon < lrLon) &&
                (ROOT_LR.lat < ulLat && lrLat < ROOT_UL.lat);
    }

    /**
     * Generates a render grid of tile file names within the specified bounding box at the given depth.
     *
     * @param ulLon  The longitude coordinate of the upper-left corner of the query box.
     * @param ulLat  The latitude coordinate of the upper-left corner of the query box.
     * @param lrLon  The longitude coordinate of the lower-right corner of the query box.
     * @param lrLat  The latitude coordinate of the lower-right corner of the query box.
     * @param depth  The image depth (zoom level) for which to generate the render grid.
     * @return A 2D array representing the render grid of tile file names.
     */
    private String[][] getRenderGrid(double ulLon, double ulLat, double lrLon, double lrLat, int depth) {
        int[] indices = calculateIndices(ulLon, ulLat, lrLon, lrLat, depth);
        int gridLengthX = indices[1] - indices[0] + 1;
        int gridLengthY = indices[3] - indices[2] + 1;
        String[][] renderGrid = new String[gridLengthY][gridLengthX];   // [y][x]

        for (int y = 0; y < gridLengthY; y++) {
            int yTile = indices[2] + y;
            for (int x = 0; x < gridLengthX; x++) {
                int xTile = indices[0] + x;
                renderGrid[y][x] = "d" + depth + "_x" + xTile + "_y" + yTile + IMAGE_FILE_FORMAT;
            }
        }
        return renderGrid;
    }

    /**
     * Calculates the bounding box coordinates based on the upper-left (ulLon, ulLat) and
     * lower-right (lrLon, lrLat) coordinates, the image depth, and the tile lengths.
     *
     * @param ulLon  The longitude coordinate of the upper-left corner of the query box.
     * @param ulLat  The latitude coordinate of the upper-left corner of the query box.
     * @param lrLon  The longitude coordinate of the lower-right corner of the query box.
     * @param lrLat  The latitude coordinate of the lower-right corner of the query box.
     * @param depth  The image depth (zoom level) of the tiles on which to calculate the index.
     * @return An array containing the raster coordinates [raster_ul_lon, raster_ul_lat, raster_lr_lon, raster_lr_lat].
     */
    private double[] calculateBoundingBox(double ulLon, double ulLat, double lrLon, double lrLat, int depth) {
        double tileLongitudeLength = tileLonLength(depth);
        double tileLatitudeLength = tileLatLength(depth);
        int[] indices = calculateIndices(ulLon, ulLat, lrLon, lrLat, depth);
        double[] boundingBox = new double[4];

        // Calculate raster coordinates
        boundingBox[0] = ROOT_UL.lon + indices[0] * tileLongitudeLength;
        boundingBox[1] = ROOT_LR.lat + (numTilesAtDepth(depth) - indices[2]) * tileLatitudeLength;
        boundingBox[2] = ROOT_UL.lon + (indices[1] + 1) * tileLongitudeLength;
        boundingBox[3] = ROOT_LR.lat + (numTilesAtDepth(depth) - indices[3] - 1) * tileLatitudeLength;

        return boundingBox;
    }

    /**
     * Calculates the indices for the bounding box coordinates based on the upper-left (ulLon, ulLat)
     * and lower-right (lrLon, lrLat) coordinates, and the image depth.
     *
     * @param ulLon  The longitude coordinate of the upper-left corner of the query box.
     * @param ulLat  The latitude coordinate of the upper-left corner of the query box.
     * @param lrLon  The longitude coordinate of the lower-right corner of the query box.
     * @param lrLat  The latitude coordinate of the lower-right corner of the query box.
     * @param depth  The image depth (zoom level) of the tiles on which to calculate the index.
     * @return An array containing the indices [X-Index-Left, X-Index-Right, Y-Index-Upper, Y-Index-Lower].
     */
    private int[] calculateIndices(double ulLon, double ulLat, double lrLon, double lrLat, int depth) {
        double tileLongitudeLength = tileLonLength(depth);
        double tileLatitudeLength = tileLatLength(depth);
        int[] indices = new int[4];

        // X-Index-Left, X-Index-Right, Y-Index-Upper, Y-Index-Lower
        indices[0] = (int) Math.max((ulLon - ROOT_UL.lon) / tileLongitudeLength, 0);
        indices[1] = (int) Math.min((lrLon - ROOT_UL.lon) / tileLongitudeLength, numTilesAtDepth(depth) - 1);
        indices[2] = (int) Math.max((ROOT_UL.lat - ulLat) / tileLatitudeLength, 0);
        indices[3] = (int) Math.min((ROOT_UL.lat - lrLat) / tileLatitudeLength, numTilesAtDepth(depth) - 1);

        return indices;
    }

    /**
     * Calculates the longitudinal length of a single tile at the given image depth (zoom level).
     *
     * @param depth  The image depth (zoom level) for which to calculate the tile's longitudinal length.
     * @return The longitudinal length of a single tile at the specified image depth.
     */
    private double tileLonLength(int depth) {
        return (ROOT_LR.lon - ROOT_UL.lon) / numTilesAtDepth(depth);
    }

    /**
     * Calculates the latitudinal length of a single tile at the given image depth (zoom level).
     *
     * @param depth  The image depth (zoom level) for which to calculate the tile's latitudinal length.
     * @return The latitudinal length of a single tile at the specified image depth.
     */
    private double tileLatLength(int depth) {
        return (ROOT_UL.lat - ROOT_LR.lat) / numTilesAtDepth(depth);
    }
}
