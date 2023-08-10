package byog.Core;

import java.awt.Font;
import java.awt.Color;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab6.MemoryGame;

import java.text.StringCharacterIterator;

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Map;
import java.util.Random;

public class Game {

    private long seed;

    /* Game modes */
    private static final int STRINGMODE = 0;
    private static final int KEYBOARDMODE = 1;

    private GameState gameState;
    private MapGenerator.Coordinate player;

    TERenderer ter = new TERenderer();




    public Game() {
        ter.initialize(byog.Core.MapGenerator.WIDTH, byog.Core.MapGenerator.HEIGHT);
        gameState = new GameState();
        StdDraw.enableDoubleBuffering();
    }


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        processChar(input, STRINGMODE);
        return gameState.world;
    }


    private void drawMenu() {
        StdDraw.setPenColor(StdDraw.WHITE);
        Font font1 = new Font("Sans Serif", Font.PLAIN, 70);
        Font font2 = new Font("Sans Serif", Font.PLAIN, 50);
        StdDraw.setFont(font1);
        StdDraw.text(40, 25, "CS61B: THE GAME");
        StdDraw.setFont(font2);
        StdDraw.text(40, 20, "New Game (N)");
        StdDraw.text(40, 15, "Load Game (L)");
        StdDraw.text(40, 10, "Quit (Q)");
        StdDraw.show();
    }


    /**
     * Process commands.
     * @param input the String to be processed.
     * @param mode  the mode of the game.
     */
    private void processChar(String input, int mode) {
        /* Uses iterator to process every char of input. */
        StringCharacterIterator it = new StringCharacterIterator(input.toUpperCase());
        char c;

        while (it.current() != Keys.QUIT_SAVE) {
            c = it.current();
            switch (c) {
                case Keys.NEW_GAME:
                    if (mode == KEYBOARDMODE) {
                        // TODO: for keyboard
                    }
                    seed = 0;
                    break;

                case Keys.LOAD_GAME:
                    if (mode == KEYBOARDMODE) {
                        // TODO: for keyboard
                    }
                    gameState = GameState.loadWorld();
                    player = getPlayer();
                    break;

                /* Save and Quite via String. */
                case Keys.PRE_QUIT_SAVE:
                    if (mode == STRINGMODE) {
                        if (it.next() == Keys.QUIT_SAVE) {
                            GameState.saveWorld(gameState);
                            System.exit(0);
                            break;
                        }
                    }
                    break;

                /* Quite via Keyboard. */
                case Keys.QUIT_SAVE:
                    if (mode == KEYBOARDMODE) {
                        MapGenerator.displayMessage("SAVE GAME? (Y/N)");
                        while (true) {
                            if (it.next() == Keys.YES) {
                                GameState.saveWorld(gameState);
                                System.exit(0);
                                break;
                            } else if (it.next() == Keys.NO) {
                                System.exit(0);
                                break;
                            }
                        }
                    }
                    /* Quite via string without save the game. */
                    System.exit(0);
                    break;

                /* Move the player. */
                case Keys.UP:
                    movePlayer(MapGenerator.NORTH);
                    break;
                case Keys.DOWN:
                    movePlayer(MapGenerator.SOUTH);
                    break;
                case Keys.LEFT:
                    movePlayer(MapGenerator.WEST);
                    break;
                case Keys.RIGHT:
                    movePlayer(MapGenerator.EAST);
                    break;

                /* Set the seeds. */
                default:
                    if(Character.isDigit(c)) {
                        // TODO: for keyboard
                        if (mode == KEYBOARDMODE) {

                        }

                        seed = seed * 10 + c - '0';     // Note that c is a char digit, e.g. '1' - '0' = 49 - 48 = 1

                        /* seed: #####S, after 'S' should all seeds set to the world. */
                        if (it.next() == Keys.DOWN) {
                            gameState.rand = new Random(seed);
                            gameState.world = MapGenerator.generateWorld(gameState.rand);
                            player = getPlayer();
                            it.next();
                            break;
                        }
                    }
                    break;
            }
            it.next();
        }
    }


    /**
     * Find the player and return it.
     * @return
     */
    private MapGenerator.Coordinate getPlayer() {
        for (int x = 0; x < MapGenerator.WIDTH; x++) {
            for (int y = 0; y < MapGenerator.HEIGHT; y++) {
                if (gameState.world[x][y].equals(Tileset.PLAYER)) {
                    return new MapGenerator.Coordinate(x, y);
                }
            }
        }
        return null;
    }



    /**
     * Move the Player to a new coordinate.
     * @param direction the direction the player should move in.
     */
    private void movePlayer(int direction) {
        MapGenerator.Coordinate new_coord = MapGenerator.applyDir(direction, 1, player);
        if (gameState.world[new_coord.x][new_coord.y].equals(Tileset.FLOOR)) {
            gameState.world[player.x][player.y] = Tileset.FLOOR;
            player = new_coord;
            gameState.world[new_coord.x][new_coord.y] = Tileset.PLAYER;
        }
    }






}
