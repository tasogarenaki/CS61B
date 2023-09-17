package hw4.puzzle;

public class SearchNode {
    /* Current world state. */
    private final WorldState state;
    /* Previous search node in the graph. */
    private final SearchNode prev;
    /* The number of nodes traversed from the start node up to this node. */
    private final int moves;

    public SearchNode(WorldState state, SearchNode prev) {
        this.state = state;
        this.prev = prev;
        this.moves = prev == null ? 0 : prev.moves + 1;
    }

    public WorldState state() {
        return state;
    }

    public SearchNode prev() {
        return prev;
    }

    public int distanceFromStart() {
        return moves;
    }

    public int estimatedDistanceToGoal() {
        return state.estimatedDistanceToGoal();
    }
}
