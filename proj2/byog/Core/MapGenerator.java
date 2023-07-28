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
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;


    /* Set font size to displayed. */
    private static final int TITLE_FONT_SIZE = 40;
    private static final int INITIAL_COMMANDS_FONT_SIZE = 30;
    private static final int HUD_FONT_SIZE = 16;



    protected static class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }


    }

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

    public static TETile[][] generateWorld(Random rand) {
        World world = new World();
        TETile[][] map = world.map;

        /* Initialize the world. */
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                map[x][y] = Tileset.NOTHING;
            }
        }


        return null;
    }


    protected static Coordinate getPlayer() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < WIDTH; y++) {

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
