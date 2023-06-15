
/** A class for palindrome operations. */
public class Palindrome {

    /**
     * Converts a String to a Deque.
     * @param word the String to be converted.
     * @return     a Deque containing the Characters of the String words.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (Character c : word.toCharArray()) {
            deque.addLast(c);
        }
        return deque;
    }

    /**
     * Checks if a String is a palindrome.
     * @param word the String to be checked
     * @return     true or false whether the String is a palindrome or not.
     */
    public boolean isPalindrome(String word){
        Deque<Character> chars = wordToDeque(word);
        return helperPalindrome(chars);
    }


    /**
     * Checks whether the characters satisfies the conditions for a palindrome.
     * @param chars is a Deque.
     * @return      true or false whether the String is a palindrome or not.
     */
    private boolean helperPalindrome(Deque<Character> chars) {
        if (chars.size() == 1 || chars.size() == 0) {
            return true;
        }
        Character first = chars.removeFirst();
        Character last = chars.removeLast();
        return first.equals(last) && last.equals(first);
    }
}
