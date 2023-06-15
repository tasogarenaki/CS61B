import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    /* These test Palindrome.isPalindrome(String word) */

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindromeLengthZero() {
        String wordOfLengthZero = "";
        assertTrue(palindrome.isPalindrome(wordOfLengthZero));
    }

    @Test
    public void testIsPalindromeLengthOne() {
        String wordOfLengthOne = "a";
        assertTrue(palindrome.isPalindrome(wordOfLengthOne));
    }

    @Test
    public void testIsPalindromeIsPalindrome() {
        String wordIsPalindrome= "racecar";
        assertTrue(palindrome.isPalindrome(wordIsPalindrome));
    }

    @Test
    public void testIsPalindromeIsPalindrome2() {
        String wordIsPalindrome= "noon";
        assertTrue(palindrome.isPalindrome(wordIsPalindrome));
    }

    @Test
    public void testIsPalindromeIsNotPalindrome() {
        String wordIsNotPalindrome= "cat";
        assertFalse(palindrome.isPalindrome(wordIsNotPalindrome));
    }











}
