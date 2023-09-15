package lab11.graphs;

import java.util.ArrayList;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private ArrayList<Integer> fringe;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new ArrayList<>();
    }

    /** Estimate of the Manhattan distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        int min = fringe.get(0);
        int index = 0;

        if (fringe.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < fringe.size(); i++) {
            if (h(min) > h(fringe.get(i))) {
                min = fringe.get(i);
                index = i;
            }
        }
        fringe.remove(index);
        return min;
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        int v = s;
        fringe.add(v);
        while (!targetFound) {
            v = findMinimumUnmarked();
            marked[v] = true;
            announce();
            for (int u : maze.adj(v)) {
                if (!marked[u]) {
                    edgeTo[u] = v;
                    distTo[u] = distTo[v] + 1;
                    targetFound = (v == t);
                    fringe.add(u);
                }
                if (targetFound) {
                    return;
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

