package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation
 * Model of a percolation system using an N-by-N grid of sites.
 * Each site is either open or blocked. A full site is an open site that can be connected
 * to an open site in the top row via a chain of neighboring (left, right, up, down) open sites.
 * The system percolates if there is a full site in the bottom row.
 *
 * @author Terry
 */

    /*
    Note:
    union(int p, int q) !-> union((x,y), (x2,y2)), so need translate if -> just mark each cell with a number -> row * N + col
     */
public class Percolation {
    private int N;
    private WeightedQuickUnionUF fullSet;
    private WeightedQuickUnionUF percolateSet;
    private boolean[] openSet;
    private int openNum = 0;

    /**
     * create N-by-N grid, with all sites initially blocked
     * @param N
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size must be a natural number.");
        }

        this.N = N;
        /* The second-to-last one is the always full sentinel.
        The last one is the sentinel that connects to all nodes in the bottom row. */
        fullSet = new WeightedQuickUnionUF(N * N + 1);

        /* To prevent the backwash problem. */
        percolateSet = new WeightedQuickUnionUF(N * N + 2);

        /* Initially, all are blocked. */
        openSet = new boolean[N * N];
    }

    public void open(int row, int col)       // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col)  // is the site (row, col) open?
    public boolean isFull(int row, int col)  // is the site (row, col) full?
    public int numberOfOpenSites()           // number of open sites
    public boolean percolates()              // does the system percolate?
    public static void main(String[] args)   // use for unit testing (not required)

    /**
     * Convert the coordinates to the index of the grid for union(int p, int q).
     * @param row
     * @param col
     * @return the index of the grid
     */
    private int corToIndex(int row, int col) {
        return row * N + col;
    }



}
