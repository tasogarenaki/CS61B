import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByN {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    // Your tests go here.
    @Test
    public void testOffByN() {
        CharacterComparator offByN = new OffByN(5);

        /** Test Lower */
        assertTrue(offByN.equalChars('a', 'f'));
        assertFalse(offByN.equalChars('f', 'h'));

        /** Test Upper */
        assertTrue(offByN.equalChars('A', 'F'));
        assertFalse(offByN.equalChars('F', 'H'));

        /** Test Lower and Upper */
        assertFalse(offByN.equalChars('a', 'A'));
        assertFalse(offByN.equalChars('X', 'y'));

        /** Test Non Letters */
        assertFalse(offByN.equalChars('&', '%'));
        assertFalse(offByN.equalChars('!', '('));
    }
}
