import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    private final int experiment_time;  //T
    private int size;   //N
    private double[] sum_average;   //this array is using for calculating the mean and stddev

    // Perform T independent experiments (Monte Carlo simulations) on an
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        Percolation perc;
        size = N;  // the size of the grid
        experiment_time = T;   // number of executions
        sum_average = new double[experiment_time];

        // Creating experiment_time of perc
        // Open sites randomly
        for (int i = 0; i < experiment_time; i++) {
            perc = new Percolation(size);
            while (!perc.percolates()) {
                int x = StdRandom.uniform(0, size);
                int j = StdRandom.uniform(0, size);
                if (!perc.isOpen(x, j)) {
                    perc.open(x, j);
                }
            }
            sum_average[i] = (double) perc.numberOfOpenSites() / (size * size);
        }
    }

    // Sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(sum_average);
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(sum_average);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / Math.sqrt(experiment_time);
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(experiment_time);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}

