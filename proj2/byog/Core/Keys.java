package byog.Core;

/**
 * Keys used to play the game.
 * Note: Here, only uppercase letters are listed, but as requested,
 * there should be no distinction between, for example, 'n' or 'N'.
 * This normalization will be implemented in Game.java.
 * @author Terry
 */
public class Keys {
    /* Game Control Keys. */
    public static final char NEW_GAME = 'N';
    public static final char LOAD_GAME = 'L';
    public static final char PRE_QUIT_SAVE = ':';
    public static final char QUIT_SAVE = 'Q';

    /* Direction Keys. */
    public static final char UP = 'W';
    public static final char DOWN = 'S';
    public static final char LEFT = 'A';
    public static final char RIGHT = 'D';

    /* Decision Keys. */
    public static final char YES = 'Y';
    public static final char NO = 'N';
}
