package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.text.StringCharacterIterator;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;



    /* Game modes */
    private static final int STRINGMODE = 0;
    private static final int KEYBOARDMODE = 1;


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



        return null;
    }


    /**
     * Process commands.
     * @param input
     * @param mode
     */
    private void processChar(String input, int mode) {
        /* Uses iterator to process every char of input. */
        StringCharacterIterator it = new StringCharacterIterator(input.toUpperCase());
        char c;

        while (it.current() != Keys.QUIT_SAVE) {
            c = it.current();
            switch (c) {
                case Keys.NEW_GAME:
                    // TODO: for keyboard


                case Keys.PRE_QUIT_SAVE:
                    if (it.next() == Keys.QUIT_SAVE) {
                        // TODO: save the word then quite
                    }

                case Keys.UP:

                default:
                if(Character.isDigit(c)) {
                    // TODO: for seeds

                }

            }
            it.next();
        }





    }
}
