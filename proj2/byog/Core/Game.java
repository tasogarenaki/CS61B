package byog.Core;

import java.awt.Font;
import java.awt.Color;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.text.StringCharacterIterator;
import edu.princeton.cs.introcs.StdDraw;
import java.util.Random;

/**
 * Game.
 * The main methods of playing the game.
 * @author Terry
 */
public class Game {
    /* Properties of the Game Menu. */
    private static final String TITLE = "CS61B: THE GAME";
    private static final String INITIAL_COMMAND_NEW_GAME = "New Game (N)";
    private static final String INITIAL_COMMAND_LOAD_GAME = "Load Game (L)";
    private static final String INITIAL_COMMAND_QUIT = "Quit(Q)";
    private static final String SAVE_GAME = "SAVE GAME? (Y/N)";
    private static final String QUITE = "QUITTING...";
    private static final String WIN = "Congratulations, you won!";
    private static final String TIME_OUT = "Oh, time's up, you lost.";
    private static final String MOVE_OUT = "Max moves reached, game over.";

    /* Properties of Window and Font Sizes. */
    private static final int WINDOW_WIDTH = (MapGenerator.WIDTH - 1) / 2;
    private static final int WINDOW_HEIGHT = MapGenerator.HEIGHT - 6;
    private static final int DISPLAY_WIDTH = MapGenerator.WIDTH;
    private static final int DISPLAY_HEIGHT = MapGenerator.HEIGHT + 2;
    private static final int VERTICAL_SEPARATION = 5;
    private static final int TITLE_FONT_SIZE = 70;
    private static final int INITIAL_COMMANDS_FONT_SIZE = 50;
    private static final int HUD_FONT_SIZE = 15;
    private static final int ENVIRONMENT_SIZE = 15;

    /* Properties of Game States. */
    private static final int PAUSE = 1000;
    private static final int ALLOWED_MOVE = 400;   // steps
    private static final int ALLOWED_TIME = 180; // s
    private static final int TIME_CORRECT = 245;
    private long seed;
    private GameState gameState;
    private MapGenerator.Coordinate player;
    private enum States {COMMAND, GAME}
    private States state;
    TERenderer ter = new TERenderer();


    public Game() {
        ter.initialize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        gameState = new GameState();
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
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
        // THIS LINE IS ONLY REMOVED TO BE ABLE TO RUN WITH THE AUTOGRADER
        //ter.renderFrame(gameState.world);
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
                /* Start a New Game. */
                case Keys.NEW_GAME:
                    seed = 0;
                    break;
                /* Load a Saved Game. */
                case Keys.LOAD_GAME:
                    gameState = GameState.loadWorld();
                    player = getPlayer();
                    break;
                /* Save and Quit. */
                case Keys.PRE_QUIT_SAVE:
                    if (it.next() == Keys.QUIT_SAVE) {
                        GameState.saveWorld(gameState);
                        System.exit(0);
                        break;
                    }
                    break;
                /* Quit without Saving. */
                case Keys.QUIT_SAVE:
                    System.exit(0);
                    break;

                /* Move the Player. */
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

                /* Set the Seeds. */
                default:
                    if(Character.isDigit(c)) {
                        seed = seed * 10 + c - '0';     // Note that c is a char digit, e.g. '1' - '0' = 49 - 48 = 1
                        /* Seed: #####S. After 'S', all seeds should be set to the world. */
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
     * Play the game using the keyboard.
     * @param command the Key to be processed.
     */
    private void processKey(char command) {
        int step = 0;
        double time = 0;
        boolean door = false;
        while (true) {
            /* Game States: Start, Load, Quit the game. */
            if (state == States.COMMAND) {
                if (command == Keys.NEW_GAME) {
                    /* Read Seed. */
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

                        /* Check whether the seed is valid. */
                        try {
                            seed = Long.parseLong(temp);
                        } catch (NumberFormatException e) {
                            seed = null;
                        }
                        if (seed != null) {
                            break;
                        } else {
                            displayMessage("INVALID SEED");
                            StdDraw.pause(PAUSE);
                        }
                    }

                    /* Define the size of the game environment. */
                    Font font = new Font("Times New Roman", Font.PLAIN, ENVIRONMENT_SIZE);
                    StdDraw.setFont(font);

                    /* Configure the game properties. */
                    gameState.rand = new Random(seed);
                    gameState.world = MapGenerator.generateWorld(gameState.rand);
                    player = getPlayer();
                } else if (command == Keys.LOAD_GAME) {
                    if ((gameState = GameState.loadWorld()) == null) {
                        displayMessage("There's no saved game.");
                        StdDraw.pause(PAUSE);
                        displayMessage(QUITE);
                        StdDraw.pause(PAUSE);
                        System.exit(0);
                    } else {
                        gameState = GameState.loadWorld();
                        player = getPlayer();
                    }
                } else {
                    displayMessage(SAVE_GAME);
                    while (true) {
                        command = readKey();
                        if (command == Keys.YES) {
                            /* Trying to save a null world will cause an error. */
                            if (gameState.world != null) {
                                GameState.saveWorld(gameState);
                            } else {
                                displayMessage("There's no game to save.");
                                StdDraw.pause(PAUSE);
                            }
                            displayMessage(QUITE);
                            StdDraw.pause(PAUSE);
                            System.exit(0);
                            break;
                        } else if (command == Keys.NO) {
                            displayMessage(QUITE);
                            StdDraw.pause(PAUSE);
                            System.exit(0);
                            break;
                        }
                    }
                }
                state = States.GAME;
            /* Move the Player. */
            } else if (state == States.GAME) {
                ter.renderFrame(gameState.world);
                renderHUD(time, step);
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
                        state = States.COMMAND;
                        break;
                    default:
                        break;
                }

                /* Gold Points: make it possible to win or lose the game; there should be three ways to do so. */
                /* Count Time. */
                time++;
                /* Count Steps. */
                switch (command) {
                    case Keys.UP:
                    case Keys.DOWN:
                    case Keys.LEFT:
                    case Keys.RIGHT:
                        step++;
                }
                /* Check if the player finds the door. */
                for (int i = 0; i < 4; i++) {
                    MapGenerator.Coordinate neighbour = MapGenerator.applyDir(i, 1, player);
                    if (gameState.world[neighbour.x][neighbour.y].equals(Tileset.LOCKED_DOOR)) {
                        door = true;
                    }
                }

            }
            if (step > ALLOWED_MOVE || door || time / TIME_CORRECT > ALLOWED_TIME) {
                String message = (step > ALLOWED_MOVE) ? MOVE_OUT : (door ? WIN : TIME_OUT);
                displayMessage(message);
                StdDraw.pause(PAUSE * 2);
                displayMessage(QUITE);
                StdDraw.pause(PAUSE);
                System.exit(0);
                break;
            }
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
     * Move the Player to a new position.
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
        Font font_size = new Font("Times New Roman", Font.PLAIN, TITLE_FONT_SIZE);
        Font commands_size = new Font("Times New Roman", Font.PLAIN, INITIAL_COMMANDS_FONT_SIZE);
        StdDraw.setFont(font_size);
        StdDraw.text(WINDOW_WIDTH, WINDOW_HEIGHT, TITLE);
        StdDraw.setFont(commands_size);
        StdDraw.text(WINDOW_WIDTH, WINDOW_HEIGHT - VERTICAL_SEPARATION, INITIAL_COMMAND_NEW_GAME);
        StdDraw.text(WINDOW_WIDTH, WINDOW_HEIGHT - VERTICAL_SEPARATION * 2, INITIAL_COMMAND_LOAD_GAME);
        StdDraw.text(WINDOW_WIDTH, WINDOW_HEIGHT - VERTICAL_SEPARATION * 3, INITIAL_COMMAND_QUIT);
        StdDraw.show();
    }

    /**
     * Read the current position of the mouse on the game window
     * and set a value indicating what type of tile is below the mouse pointer.
     */
    private void renderHUD(double time, int step) {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();

        /* Show remaining time and steps. */
        Font font = StdDraw.getFont();
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font.deriveFont(Font.BOLD, HUD_FONT_SIZE));
        StdDraw.textLeft(DISPLAY_WIDTH / 2 - 2, DISPLAY_HEIGHT - 1,
                "Time Left: " + Math.round(ALLOWED_TIME - time / TIME_CORRECT) + " s");
        StdDraw.textLeft(DISPLAY_WIDTH - 10, DISPLAY_HEIGHT - 1, "Step Left: " + (ALLOWED_MOVE - step));
        StdDraw.show();

        /* A bug may occur if the mouse is outside the range of the world. */
        if (mouseX < MapGenerator.WIDTH && mouseY < MapGenerator.HEIGHT) {
            String desc = gameState.world[mouseX][mouseY].description();
            StdDraw.textLeft(2, DISPLAY_HEIGHT - 1, desc);
            StdDraw.show();
        }
    }

    /**
     * Display a message to the player.
     * @param message to be displayed.
     */
    private void displayMessage(String message) {
        Font font = StdDraw.getFont();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font.deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        StdDraw.text(DISPLAY_WIDTH / 2, DISPLAY_HEIGHT / 2, message);
        StdDraw.setFont(font);
        StdDraw.show();
    }
}
