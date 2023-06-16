/** A class for off-by-N comparators. */

public class OffByN implements CharacterComparator {

    private int N;

    public OffByN(int N) {
        this.N = N;
    }

    /**
     *
     * @param x
     * @param y
     * @return  true if the difference of exactly N.
     */
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return (diff == N || diff == -N);
    }
}
