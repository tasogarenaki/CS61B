import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;

public class BinaryTrie implements Serializable {
    private Node root;

    private class Node implements Comparable<Node>, Serializable {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    /**
     * Constructs a BinaryTrie from a frequency table of characters.
     *
     * @param frequencyTable A map containing character frequencies.
     *                      Keys are characters, and values are their corresponding frequencies.
     *                      This constructor builds a binary trie based on the provided frequencies.
     */
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<Node>();
        Set<Map.Entry<Character, Integer>> chars = frequencyTable.entrySet();

        /* Initialize priority queue with singleton trees. */
        for (Map.Entry<Character, Integer> entry : chars) {
            Character c = entry.getKey();
            Integer f = entry.getValue();
            pq.insert(new Node(c, f, null, null));
        }

        /* Merge two smallest trees. */
        while (pq.size() > 1) {
            Node left  = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        root = pq.delMin();
    }

    /**
     * The longestPrefixMatch method finds the longest prefix that
     * matches the given querySequence and returns a Match object for that Match.
     * @param querySequence
     * @return
     */
    public Match longestPrefixMatch(BitSequence querySequence) {
        Node current = root;
        Node child = null;
        BitSequence bs = new BitSequence();

        for (int i = 0; i < querySequence.length() && current != null; i++) {
            int bit = querySequence.bitAt(i);

            child = (bit == 0) ? current.left : current.right;
            if (child == null) {
                return new Match(bs, current.ch);
            }

            current = child;
            bs = bs.appended(bit);
        }

        return new Match(bs, (current != null) ? current.ch : null);
    }

    /**
     * @return the inverse of the coding trie.
     */
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> ret = new HashMap<>();
        helperBuildLookupTable(root, ret, new BitSequence());
        return ret;
    }

    private void helperBuildLookupTable(Node node, Map<Character, BitSequence> map, BitSequence sequence) {
        if (node == null) {
            return;
        }

        BitSequence leftSequence = sequence.appended(0);
        BitSequence rightSequence = sequence.appended(1);

        helperBuildLookupTable(node.left, map, leftSequence);
        helperBuildLookupTable(node.right, map, rightSequence);

        char ch = node.ch;
        if (ch != '\0') {
            map.put(ch, sequence);
        }
    }
}
