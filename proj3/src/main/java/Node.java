import java.util.Iterator;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * Node
 *
 * This represents a Node element with coordinates and a unique ID.
 * It is connected to its neighboring nodes.
 *
 * @author Terry
 */
public class Node extends Coordinate {
    /* The unique id of a node. */
    long id;
    /* Neighbor nodes. */
    private Set<Node> neighbors;

    Node(long id, double lon, double lat) {
        super(lon, lat);
        this.id = id;
        this.neighbors = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Node other = (Node) o;
        return this.lon == other.lon && this.lat == other.lat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lon, lat);
    }

    /**
     * Check for neighbor nodes.
     * @return true if there are neighbor nodes.
     */
    boolean hasNeighbors() {
        return neighbors != null && !neighbors.isEmpty();
    }

    /**
     * Adds a neighbor node to this node if it's not already present
     * and adds this node as a neighbor of the other node.
     * Note: boolean HashSet.add(E e)
     *
     * @param neighbor the node to add as a neighbor.
     * @return true if the neighbor node was not already a neighbor.
     */
    boolean addNeighbor(Node neighbor) {
        if (this.neighbors == null) {
            this.neighbors = new HashSet<>();
        }
        if (neighbor.neighbors == null) {
            neighbor.neighbors = new HashSet<>();
        }
        neighbor.neighbors.add(this);
        return this.neighbors.add(neighbor);
    }

    Iterable<Node> neighbors() {
        if (neighbors == null) {
            return new HashSet<>();
        } else {
            return neighbors;
        }
    }

    /**
     * Remove a neighbor node from this node.
     * @param neighbor  the neighbor node to be removed.
     * @return  true if the node was removed.
     */
    boolean removeNeighbor(Node neighbor) {
        return neighbors.remove(neighbor);
    }
}
