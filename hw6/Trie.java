import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Trie
 *
 * Trie (retrieval tree) data structure implementation for
 * storing words using ASCII characters from 'a' to 'z' and space ' '.
 * This implementation uses a Map<Character, Trie.CharNode> to store the children of a node.
 *
 * @author Terry
 */
public class Trie {

    private static final char FIRST_VALID_LETTER = 'a';
    private static final char LAST_VALID_LETTER = 'z';
    private static final char SPACE_CHAR = ' ';
    private CharNode sentinel;

    Trie() {
        sentinel = new CharNode(SPACE_CHAR);
    }

    public void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        for (int i = 0; i < word.length(); i++) {
            if (!isValidChar(word.charAt(i))) {
                return;
            }
        }
        CharNode charNode = sentinel;
        for (int i = 0; i < word.length(); i++) {
            charNode = charNode.addNextLetter(word.charAt(i));
        }
        charNode.end = true;
    }

    public static Trie create(String[] words) {
        Trie trie = new Trie();
        for (String w : words) {
            trie.addWord(w);
        }
        return trie;
    }


    boolean isValidChar(char c) {
        return (FIRST_VALID_LETTER <= c && c <= LAST_VALID_LETTER) || c == SPACE_CHAR;
    }

    /**
     * Returns a list of all words that start with the given prefix.
     *
     * @param prefix The prefix to search for.
     * @return A list of words that start with the prefix.
     */
    List<String> wordsByPrefix(String prefix) {
        if (prefix.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> results = new ArrayList<>();
        CharNode charNode = sentinel;
        for (int i = 0; i < prefix.length(); i++) {
            char letter = prefix.charAt(i);
            if (!charNode.nextLetterMap.containsKey(letter)) {
                return results;
            }
            charNode = charNode.nextLetterMap.get(letter);
        }
        prefix = prefix.substring(0, prefix.length() - 1);
        List<String> suffixes = getAllWords(charNode);
        for (String suffix : suffixes) {
            results.add(prefix + suffix);
        }
        return results;
    }

    private List<String> getAllWords(CharNode charNode) {
        List<String> words = new ArrayList<>();
        String word = "";
        getAllWords(charNode, word, words);
        return words;
    }

    private void getAllWords(CharNode charNode, String word, List<String> words) {
        if (charNode == null) {
            return;
        }
        if (charNode.end) {
            words.add(word + charNode.letter);
        }
        if (charNode.nextLetterMap == null) {
            return;
        }
        for (CharNode cn : charNode.nextLetterMap.values()) {
            getAllWords(cn, word + charNode.letter, words);
        }
    }

    private static class CharNode implements Comparable<CharNode> {
        final char letter;
        boolean end;
        Map<Character, CharNode> nextLetterMap;

        CharNode(char letter) {
            this.letter = Character.toLowerCase(letter);
            end = false;
            nextLetterMap = null;
        }

        @Override
        public int compareTo(CharNode other) {
            if (other == null) {
                throw new NullPointerException("CharNode to compare to cannot be null.");
            }
            return this.letter - other.letter;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o.getClass() != this.getClass()) {
                return false;
            }
            CharNode other = (CharNode) o;
            return this.letter == other.letter;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(letter);
        }

        CharNode addNextLetter(char l) {
            if (nextLetterMap == null) {
                nextLetterMap = new TreeMap<>();
            }
            if (!nextLetterMap.containsKey(l)) {
                nextLetterMap.put(l, new CharNode(l));
            }
            return nextLetterMap.get(l);
        }
    }
}
