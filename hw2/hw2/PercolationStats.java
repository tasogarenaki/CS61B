package hw2;

/**
 * PercolationStats
 * Estimates the percolation threshold (mu) using the Monte Carlo Method.
 * @author Terry
 */
public class PercolationStats {
    /* perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Grid size must be a natural number.");
        }
    }
    public double mean()                                           // sample mean of percolation threshold
    public double stddev()                                         // sample standard deviation of percolation threshold
    public double confidenceLow()                                  // low endpoint of 95% confidence interval
    public double confidenceHigh()                                 // high endpoint of 95% confidence interval
}
