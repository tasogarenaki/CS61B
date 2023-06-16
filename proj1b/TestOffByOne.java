import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testOffByOne() {
        OffByOne obo = new OffByOne();

        /** Test Lower */
        assertTrue(obo.equalChars('a', 'b'));
        assertFalse(obo.equalChars('a', 'm'));

        /** Test Upper */
        assertTrue(obo.equalChars('Y', 'X'));
        assertFalse(obo.equalChars('K', 'R'));

        /** Test Lower and Upper */
        assertFalse(obo.equalChars('a', 'A'));
        assertFalse(obo.equalChars('X', 'y'));

        /** Test Non Letters */
        assertTrue(obo.equalChars('&', '%'));
        assertFalse(obo.equalChars('!', '('));
    }
}
