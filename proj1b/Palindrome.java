
/** A class for palindrome operations. */
public class Palindrome {

    /**
     * Converts a String to a Deque.
     * @param word  the String to be converted.
     * @return      a Deque containing the caracters of the String words.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (Character c : word.toCharArray()) {
            deque.addLast(c);
        }
        return deque;
    }
}
