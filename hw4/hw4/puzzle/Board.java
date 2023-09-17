package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

/**
 * Board
 *
 * Implements an N-puzzle (in an NxN board) to be solved by the Solver class.
 *
 * @author Terry
 */
public class Board implements WorldState{
    private final int BLANK = 0;
    private int[][] tiles;
    private int[][] goal;
    private int N;

    /** Constructs a board from an N-by-N array of tiles where
     *  tiles[i][j] = tile at row i, column j. */
    public Board(int[][] tiles) {
        N = tiles[0].length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        this.goal = new int[N][N];
        initializeGoal();
    }
    /** Returns value of tile at row i, column j (or 0 if blank). */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException("Argument for tileAt out of bounds");
        }
        return tiles[i][j];
    }

    /** Returns the board size N. */
    public int size() {
        return N;
    }

    /** Returns the neighbors of the current board. */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int blankX = -1;
        int blankY = -1;

        // Find the coordinates of the blank tile
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == BLANK) {
                    blankX = i;
                    blankY = j;
                }
            }
        }

        int[][] newBoard = new int[size()][size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                newBoard[i][j] = tileAt(i, j);
            }
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (Math.abs(i - blankX) + Math.abs(j - blankY) == 1) {
                    newBoard[blankX][blankY] = newBoard[i][j];
                    newBoard[i][j] = BLANK;
                    neighbors.enqueue(new Board(newBoard));
                    newBoard[i][j] = newBoard[blankX][blankY];
                    newBoard[blankX][blankY] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** Hamming estimate described below. */
    public int hamming() {
        int hammingDistance = 0;

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) != BLANK && tileAt(i, j) != goal[i][j]) {
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    /** Manhattan estimate described below. */
    public int manhattan() {
        int manhattanDistance = 0;

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == BLANK) {
                    continue;
                }
                int tileValue = tileAt(i, j);
                int goalX, goalY; // Declare the variables here

                if (tileValue != BLANK) {
                    goalX = (tileValue - 1) / size();
                    goalY = (tileValue - 1) % size();
                } else {
                    // Handle the blank tile
                    goalX = size() - 1;
                    goalY = size() - 1;
                }
                manhattanDistance += Math.abs(i - goalX) + Math.abs(j - goalY);
            }
        }

        return manhattanDistance;
    }


    /** Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to Gradescope. */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /** Returns true if this board's tile values are the same position as y's. */
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y instanceof Board) {
            Board b = (Board) y;
            if (b.size() != size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                for (int j = 0; j < size(); j++) {
                    if (tileAt(i, j) != b.tileAt(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int ret = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                ret = ret * 255 + tileAt(i, j);
            }
        }
        return ret;
    }

    /** Returns the string representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    /** Initialize the goal board. */
    private void initializeGoal() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                goal[i][j] = (i * size() + j + 1) % (size() * size());
            }
        }
    }
}
