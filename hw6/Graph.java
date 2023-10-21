import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Graph {

    private final Set<Node> nodes;
    private final Trie trie;

    private Graph(Trie trie) {
        this.nodes = new HashSet<>();
        this.trie = trie;
    }

    /**
     * Creates a BoggleGraph from a 2D array of letters and a Trie dictionary.
     *
     * @param letters The grid of letters to create the BoggleGraph from.
     * @param trie The Trie dictionary to validate words against.
     * @return A BoggleGraph representing the provided letters and dictionary.
     */
    public static Graph create(char[][] letters, Trie trie) {
        Graph boggleGraph = new Graph(trie);
        int height = letters.length;
        int width = letters[0].length;
        Node[][] grid = new Node[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Node(letters[i][j]);
                boggleGraph.nodes.add(grid[i][j]);
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                addNeighborsInGrid(grid, i, j, height, width);
            }
        }
        return boggleGraph;
    }

    /**
     * Adds neighboring nodes in the grid to a given node.
     */
    private static void addNeighborsInGrid(Node[][] grid, int row, int col, int height, int width) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                int r = row + x;
                int c = col + y;
                if (r >= 0 && r < height && c >= 0 && c < width) {
                    grid[row][col].addNeighbor(grid[r][c]);
                }
            }
        }
    }

    /**
     * Discovers valid words in the Boggle graph.
     *
     * @return A list of valid words found in the Boggle graph.
     */
    public List<String> findValidWords() {
        List<String> validWords = new ArrayList<>();
        for (Node n : nodes) {
            List<String> words = exploreWordsFrom(n, trie);
            validWords.addAll(words);
        }
        return validWords;
    }

    /**
     * Find valid words starting at a given node in the Boggle graph by using BFS.
     */
    private List<String> exploreWordsFrom(Node start, Trie trie) {
        Queue<Path> fringe = new LinkedList<>();
        List<String> result = new ArrayList<>();

        Path seed = new Path(start);
        fringe.add(seed);

        while(!fringe.isEmpty()) {
            Path path = fringe.poll();
            String word = path.string();

            if (trie.contains(word)) {
                result.add(word);
            }
            if (trie.containsPrefix(word)) {
                Node end = path.end();
                for (Node neighbor : end.neighbors()) {
                    if (!path.contains(neighbor)) {
                        Path extended = (Path) path.clone();
                        extended.extend(neighbor);
                        fringe.add(extended);
                    }
                }
            }
        }
        return result;
    }
}
