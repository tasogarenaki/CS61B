import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

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
        if (k <= 0 || dictPath.isEmpty()) {
            throw new IllegalArgumentException(k <= 0 ? "k is non-positive" : "The dictionary file does not exist.");
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

        Graph boggleGraph = Graph.create(letters, dictionary);
        List<String> validWords = boggleGraph.findValidWords();

        List<String> result = validWords.stream()
                .distinct()
                .sorted(Comparator.comparing(String::length).reversed()     // Sort by word length in descending order
                        .thenComparing(String::compareTo))                  // Then, sort by lexicographical order
                .collect(Collectors.toList());

        int length = result.size() > k ? k : result.size();
        return result.subList(0, length);
    }

    public static void main(String[] args) {
        String boardFilePath = "exampleBoard.txt";
        List<String> result = solve(7, boardFilePath);
        System.out.println(result);
    }
}
