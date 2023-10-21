import java.util.Set;
import java.util.HashSet;

public class Node {

    public final Character letter;
    private final Set<Node> neighbors;

    Node(char letter) {
        this.letter = letter;
        this.neighbors = new HashSet<>();
    }

    public Iterable<Node> neighbors() {
        return neighbors;
    }

    public boolean addNeighbor(Node node) {
        if (node == null) {
            throw new NullPointerException("Cannot add null neighbor Node.");
        }
        if (this.equals(node)) {
            return false;
        }
        neighbors.add(node);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
