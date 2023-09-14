package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements a BFS for a Maze represented as an undirected graph.
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    /* A queue for implementing BFS. */
    private Queue<Integer> fringe;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        fringe = new LinkedList<>();
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        int v = s;
        fringe.add(v);
        targetFound = (v == t);
        while (!targetFound && !fringe.isEmpty()) {
            v = fringe.remove();
            marked[v] = true;
            announce();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    targetFound = (v == t);
                    fringe.add(w);
                }
                if (targetFound) {
                    return;
                }
            }
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}

