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

import javax.swing.plaf.nimbus.State;
import java.util.Map;
import java.util.Random;




public class Game {
    /* Game Menu Properties. */
    private static final String TITLE = "CS61B: THE GAME";
    private static final String INITIAL_COMMAND_NEW_GAME = "New Game (N)";
    private static final String INITIAL_COMMAND_LOAD_GAME = "Load Game (L)";
    private static final String INITIAL_COMMAND_QUIT = "Quit(Q)";
    private static final String SAVE_GAME = "SAVE GAME? (Y/N)";
    private static final String QUITE = "QUITTING...";

    /* */
    private static final int WINDOW_WIDTH = (MapGenerator.WIDTH - 1) / 2;
    private static final int WINDOW_HEIGHT = MapGenerator.HEIGHT - 6;
    private static final int DISPLAY_WIDTH = MapGenerator.WIDTH;
    private static final int DISPLAY_HEIGHT = MapGenerator.HEIGHT + 2;
    private static final int VERTICAL_SEPARATION = 5;
    private static final int TITLE_FONT_SIZE = 70;
    private static final int INITIAL_COMMANDS_FONT_SIZE = 50;
    private static final int HUD_FONT_SIZE = 15;
    private static final int ENVIRONMENT_SIZE = 15;

    /* Game Properties. */
    private long seed;
    private GameState gameState;
    private MapGenerator.Coordinate player;
    private enum States {COMMAND, GAME};
    private States state;

    TERenderer ter = new TERenderer();



    public Game() {
        ter.initialize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        // TODO: debug -> save does not work
        gameState = new GameState();
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        // TODO: for keyboard
        String first_command = "" + Keys.NEW_GAME + Keys.LOAD_GAME + Keys.QUIT_SAVE;
        char command = 0;
        while (first_command.indexOf(command) == -1) {
            displayMenu();
            command = readKey();
            state = States.COMMAND;
        }
        processKey(command);
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
        processChar(input);
        return gameState.world;
    }

    /**
     * Process commands with string.
     * @param input the String to be processed.
     */
    private void processChar(String input) {
        /* Uses iterator to process every char of input. */
        StringCharacterIterator it = new StringCharacterIterator(input.toUpperCase());
        char c;
        while (it.current() != StringCharacterIterator.DONE) {
            c = it.current();
            switch (c) {
                /* Game States. */
                case Keys.NEW_GAME:
                    seed = 0;
                    break;
                case Keys.LOAD_GAME:
                    gameState = GameState.loadWorld();
                    player = getPlayer();
                    break;
                /* Save and Quite via String. */
                case Keys.PRE_QUIT_SAVE:
                    if (it.next() == Keys.QUIT_SAVE) {
                        GameState.saveWorld(gameState);
                        System.exit(0);
                        break;
                    }
                    break;
                /* Quite via Keyboard. */
                case Keys.QUIT_SAVE:
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


    private void processKey(char command) {
        while (true) {
            if (state == States.COMMAND) {
                // TODO: L
                if (command == Keys.NEW_GAME) {
                    /* Read seed. */
                    Long seed = null;
                    while (true) {
                        displayMessage("ENTER SEED");
                        String temp = "";
                        while (true) {
                            char key = readKey();
                            if (key == 0) {
                                continue;
                            } else if (key == '\n') {
                                break;
                            }
                            temp = temp + key;
                            displayMessage("SEED: " + temp);
                        }

                        try {
                            seed = Long.parseLong(temp);
                        } catch (NumberFormatException e) {
                            seed = null;
                        }

                        if (seed != null) {
                            break;
                        } else {
                            displayMessage("INVALID SEED");
                            StdDraw.pause(1000);
                        }
                    }

                    Font font = new Font("Sans Serif", Font.PLAIN, ENVIRONMENT_SIZE);
                    StdDraw.setFont(font);
                    gameState.rand = new Random(seed);
                    gameState.world = MapGenerator.generateWorld(gameState.rand);
                    player = getPlayer();
                    ter.renderFrame(gameState.world);









                } else if (command == Keys.LOAD_GAME) {




                } else {
                    displayMessage(SAVE_GAME);
                    while (true) {
                        command = readKey();
                        if (command == Keys.YES) {
                            GameState.saveWorld(gameState);
                            displayMessage(QUITE);
                            System.exit(0);
                            break;
                        } else if (command == Keys.NO) {
                            displayMessage(QUITE);
                            System.exit(0);
                            break;
                        }
                    }
                }
                state = States.GAME;



            } else if (state == States.GAME) {
                // TODO: directions
                renderHUD();
                command = readKey();
                switch (command) {
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
                    case Keys.QUIT_SAVE:
                        state = States.COMMAND;
                        break;
                    default:
                        break;

                }
            }

        }






        /*
        if (state == States.COMMAND) {
            // TODO: N, L, Q
            if (command == Keys.NEW_GAME) {
                // TODO: seed

            }
            state = States.GAME;
        } else if (state == States.GAME) {
            // TODO: directions
            while (command != Keys.QUIT_SAVE) {
                renderHUD();
                command = readKey();
                switch (command) {

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
                    case Keys.QUIT_SAVE:
                        displayMessage(SAVE_GAME);
                        while (true) {
                            command = readKey();
                        }


                    default:
                        break;

                }



            }

        }
         */



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

    /**
     * Capture a key input from the user via the keyboard.
     * @return a single character read from the keyboard, or 0 if no key was detected.
     */
    private char readKey() {
        return StdDraw.hasNextKeyTyped()
                ? java.lang.Character.toUpperCase(StdDraw.nextKeyTyped())
                : 0;
    }

    /**
     * Show the initial menu when playing with a keyboard.
     */
    private void displayMenu() {
        StdDraw.setPenColor(StdDraw.WHITE);
        Font font_size = new Font("Sans Serif", Font.PLAIN, TITLE_FONT_SIZE);
        Font commands_size = new Font("Sans Serif", Font.PLAIN, INITIAL_COMMANDS_FONT_SIZE);
        StdDraw.setFont(font_size);
        StdDraw.text(WINDOW_WIDTH, WINDOW_HEIGHT, TITLE);
        StdDraw.setFont(commands_size);
        StdDraw.text(WINDOW_WIDTH, WINDOW_HEIGHT - VERTICAL_SEPARATION, INITIAL_COMMAND_NEW_GAME);
        StdDraw.text(WINDOW_WIDTH, WINDOW_HEIGHT - VERTICAL_SEPARATION * 2, INITIAL_COMMAND_LOAD_GAME);
        StdDraw.text(WINDOW_WIDTH, WINDOW_HEIGHT - VERTICAL_SEPARATION * 3, INITIAL_COMMAND_QUIT);
        StdDraw.show();
    }

    private void renderHUD() {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();

        if (mouseX < MapGenerator.WIDTH && mouseY < MapGenerator.HEIGHT) {
            String desc = gameState.world[mouseX][mouseY].description();
            Font font = StdDraw.getFont();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(font.deriveFont(Font.BOLD, HUD_FONT_SIZE));
            StdDraw.textLeft(2, DISPLAY_HEIGHT - 1, desc);
            StdDraw.show();
            //StdDraw.pause(10);
        }
    }



    private void displayMessage(String message) {
        Font font = StdDraw.getFont();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font.deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        //StdDraw.text(MapGenerator.WIDTH / 2, MapGenerator.HEIGHT / 2, message);
        StdDraw.text(DISPLAY_WIDTH / 2, DISPLAY_HEIGHT / 2, message);
        StdDraw.setFont(font);
        StdDraw.show();
    }





}
