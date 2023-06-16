/**
 * A class for off-by-1 comparators.
 * Compares characters for a difference of exactly one.
 */
public class OffByOne implements CharacterComparator {
    /**
     * @param x
     * @param y
     * @return  true if the difference of exactly one.
     */
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return (diff == 1 || diff == -1);
    }
}
