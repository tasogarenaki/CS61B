package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    /* After a cycle is found, the cycle should be connected with purple lines.
    There should be no edges connecting the part of the graph that doesn't contain a cycle.
    Furthermore, these purple lines are related to 'edgeTo', so we cannot use 'edgeTo' at the beginning. */
    private int[] tempEdge;
    private boolean foundCycle = false;
    private Maze maze;
    private Stack<Integer> fringe;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1, 1);
        fringe = new Stack<>();
    }

    @Override
    public void solve() {
        int v = s;
        tempEdge = new int[maze.V()];
        fringe.push(v);
        while (!fringe.isEmpty()) {
            v = fringe.pop();
            marked[v] = true;
            for (int u : maze.adj(v)) {
                if (foundCycle) {
                    return;
                } else if (marked[u] && tempEdge[v] != u) {
                    tempEdge[u] = v;
                    drawCycles(u);
                    foundCycle = true;
                } else if (!marked[u]) {
                    tempEdge[u] = v;
                    fringe.push(u);
                }
            }
            announce();
        }
    }

    // Helper methods go here
    private void drawCycles(int start) {
        int pointer = start;
        while (true) {
            edgeTo[pointer] = tempEdge[pointer];
            pointer = tempEdge[pointer];
            if (pointer == start) {
                break;
            }
        }
        announce();
    }
}

