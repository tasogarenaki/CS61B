import java.util.List;
import java.util.ArrayList;

public class Path implements Cloneable {

    private final List<Node> nodes;
    private String string;

    Path() {
        nodes = new ArrayList<>();
        string = "";
    }

    Path(Node node) {
        this();
        this.extend(node);
    }

    public boolean extend(Node node) {
        if (node == null) {
            throw new NullPointerException("Cannot add null Node to Path.");
        }
        if (this.nodes.contains(node)) {
            return false;
        }
        nodes.add(node);
        string += node.letter;
        return true;
    }

    public boolean contains(Node node) {
        return nodes.contains(node);
    }

    public Node end() {
        return nodes.get(nodes.size() - 1);
    }

    public String string() {
        return string;
    }

    @Override
    public Object clone() {
        Path path = new Path();
        for (Node node : this.nodes) {
            path.nodes.add(node);
        }
        path.string = this.string;
        return path;
    }
}
