import java.util.Objects;
import java.util.Map;
import java.util.HashMap;

/**
 * Location
 *
 * It consists of a Node and attributes.
 * Since we found this <tag...> INSIDE a node,
 * we should probably remember to which node this tag belongs.
 *
 * @author Terry
 */
public class Location {
    final long id;  // location id
    final Node node;
    private Map<String, String> attributes;

    Location(Node node) {
        this.id = node.id;
        this.node = node;
        this.attributes = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Location other = (Location) o;
        return this.node.id == other.node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(node.id);
    }

    String setAttribute(String k, String v) {
        return attributes.put(k, v);
    }

    String getAttributeValue(String key) {
        return attributes.get(key);
    }

    boolean containsAttributeKey(String key) {
        return attributes.containsKey(key);
    }
}
