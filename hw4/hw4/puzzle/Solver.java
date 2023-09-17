package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;
import java.util.List;

/**
 * Solver
 *
 * Solves a puzzle (that can be) expressed in terms of a graph by using the A* Search Algorithm.
 * This solver traverses the graph of states, finding a path from start to goal
 * that is saved as a chain of SearchNode instances.
 *
 * @author Terry
 */
public class Solver {
    private final MinPQ<SearchNode> searchNodes;
    private SearchNode path = null;


    /**
     * Constructor which solves the puzzle, computing everything necessary
     * for moves() and solution() to not have to solve the problem again.
     * Solves the puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial
     */
    public Solver(WorldState initial) {
        //TODO
        searchNodes = new MinPQ<>(new SearchNodeComparator());
        searchNodes.insert(new SearchNode(initial, null));



    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        // TODO
        return 0;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * o the solution.
     */
    public Iterable<WorldState> solution() {
        //TODO

    }

}
