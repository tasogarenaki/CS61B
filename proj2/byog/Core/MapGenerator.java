package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Random;

import java.awt.*;

public class MapGenerator {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private static final int ROOMMAXLEN = 8;

    private static final int MIN_X = 0;
    private static final int MIN_Y = 0;

    private static final int ROOM_LIMIT = 200;   // maximum number of rooms




    /* Set font size to displayed. */
    private static final int TITLE_FONT_SIZE = 40;
    private static final int INITIAL_COMMANDS_FONT_SIZE = 30;
    private static final int HUD_FONT_SIZE = 16;





    private static class World {
        private List<Room> rooms;
        private List<Hallway> hallways;
        private TETile[][] map;

        World() {
            rooms = new ArrayList<>();
            hallways = new ArrayList<>();
            map = new TETile[WIDTH][HEIGHT];
        }
    }


    private static class Region {
        List<Connect> connects;
        boolean connected;
    }

    private static class Room extends Region {
        // TODO: generate rooms

    }


    protected static class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Connects the rooms.
     */
    private static class Connect {
        Coordinate coord;
        Region connectTo;

        Connect(Coordinate coord, Region connectTo) {
            this.coord = coord;
            this.connectTo = connectTo;
        }
    }






    public static TETile[][] generateWorld(Random rand) {
        World world = new World();
        TETile[][] map = world.map;

        /* Initialize the world. */
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                map[x][y] = Tileset.NOTHING;
            }
        }
        // TODO: generate the world with rooms and connect them.


        return world.map;
    }

    private static void generateRoom(World world, Random rand) {
        for (int i = 0; i < ROOM_LIMIT; i++) {
            /* Set room size. */
            int botLeftX = RandomUtils.uniform(rand, MIN_X, WIDTH);
            int botLeftY = RandomUtils.uniform(rand, MIN_Y, HEIGHT);
            int topRightX = 2 * RandomUtils.uniform(rand, (botLeftX + 1) / 2,
                    Integer.min(WIDTH, botLeftX + ROOMMAXLEN) / 2);
            int topRightY = 2 * RandomUtils.uniform(rand, (botLeftY + 1) / 2,
                    Integer.min(HEIGHT, botLeftY + ROOMMAXLEN) / 2);

            /* Generate World via Coordinates. */
            Room rand = new Room(new Coordinate(botLeftX, botLeftY),
                    new Coordinate(topRightX, topRightY));

            if (!isRoomOverlap(world, rand)) {
                world.rooms.add(rand);
            }
        }
        fillRoom(world);
    }





    // TODO: Check if is new room is overlapped



    // TODO: fill the room











    protected static Coordinate getPlayer() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                // TODO: find the player and return it

            }
        }

        return null;

    }











    protected static void displayMessage(String message) {
        Font font = StdDraw.getFont();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font.deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        StdDraw.text(WIDTH / 2, HEIGHT / 2, message);
        StdDraw.setFont(font);
        StdDraw.show();
    }





}
