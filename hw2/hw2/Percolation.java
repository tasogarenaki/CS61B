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
public class Percolation {

    public Percolation(int N)                // create N-by-N grid, with all sites initially blocked
    public void open(int row, int col)       // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col)  // is the site (row, col) open?
    public boolean isFull(int row, int col)  // is the site (row, col) full?
    public int numberOfOpenSites()           // number of open sites
    public boolean percolates()              // does the system percolate?
    public static void main(String[] args)   // use for unit testing (not required)
}
