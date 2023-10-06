import java.util.*;

/**
 * Way
 *
 * Represents a "way" element as described in OSM.
 *
 * @author Terry
 */
public class Way {
    final long id;
    final int maxSpeed;
    final String name;
    private List<Node> nodes;   // Nodes that compose the Way element.

    Way(long id, int maxSpeed, String name) {
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.name = name;
        nodes = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Way other = (Way) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Adds a node to the graph and connects it to the previous node, if any.
     *
     * @param node The node to be added.
     * @return True if the node was added successfully, false otherwise.
     */
    boolean addNode(Node node) {
        if (nodes.size() > 0) {
            Node pre = nodes.get(nodes.size() - 1);
            /** Connect the current node to the previous node. */
            pre.addNeighbor(node);
            node.addNeighbor(pre);
        }
        return nodes.add(node);
    }

    boolean contains(Node node) {
        return nodes.contains(node);
    }
}
