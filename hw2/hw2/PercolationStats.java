package hw2;


import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * PercolationStats
 * Estimates the percolation threshold (mu) using the Monte Carlo Method.
 * @author Terry
 */
public class PercolationStats {
    private double[] thresholds;
    private int T;



    /* perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Grid size must be a natural number.");
        }
        this.T = T;
        thresholds = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation perc = pf.make(N);
            int n = 0;
            while (!perc.percolates()) {
                int row, col;
                do {
                    row = StdRandom.uniform(N);
                    col = StdRandom.uniform(N);
                } while (perc.isOpen(row, col));
                perc.open(row, col);
                n++;
            }
            thresholds[i] = (double) n / (N * N);
        }
    }

    /**
     * sample mean of percolation threshold
     * mu = (x1 + x2 + ... + xT) / T
     */
    public double mean() {
        double theMean = StdStats.mean(thresholds);
        return theMean;
    }

    /**
     * sigma^2: sample standard deviation of percolation threshold
     */
    public double stddev() {
        double theDev = StdStats.stddev(thresholds);
        return theDev;
    }

    /**
     * Low endpoint of 95% confidence interval
     */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * High endpoint of 95% confidence interval
     */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
