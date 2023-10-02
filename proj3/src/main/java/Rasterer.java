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




}
