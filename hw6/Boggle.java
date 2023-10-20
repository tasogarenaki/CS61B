import java.util.List;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k <= 0) {
            throw new IllegalArgumentException("k is non-positive");
        }

        In dict = new In(dictPath);
        String[] words = dict.readAllLines();
        Trie dictionary = Trie.create(words);

        In board = new In(boardFilePath);
        String[] boardLines = board.readAllLines();
        int expectedLineLength = boardLines[0].length();
        char[][] letters = new char[boardLines.length][];
        for (int i = 0; i < boardLines.length; i++) {
            if (expectedLineLength != boardLines[i].length()) {
                throw new IllegalArgumentException("The input board is not rectangular");
            }
            letters[i] = boardLines[i].toCharArray();
        }








        return null;
    }
}
