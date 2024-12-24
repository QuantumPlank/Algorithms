import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int number;
    private int tests;
    private double[] experiments;
    private static final double sigma = 1.98; 
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("No trials or grid");
        }
        Percolation pc;
        number = n;
        tests = trials;
        experiments = new double[tests];
        for (int i = 0; i < tests; i++) {
            pc = new Percolation(number);
            int openedSites = 0;
            while (!pc.percolates())  {
                int row = 0;
                int col = 0;
                do {
                    row = StdRandom.uniformInt(number) + 1;
                    col = StdRandom.uniformInt(number) + 1;
                } while (pc.isOpen(row, col));
                pc.open(row, col);
                openedSites += 1;
            }
            experiments[i] = ((double) openedSites) / (number * number);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(experiments);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(experiments);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (sigma * stddev() / Math.sqrt((double) tests));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (sigma * stddev() / Math.sqrt((double) tests));
    }

    // test client (see below)
    public static void main(String[] args) {
        int number = Integer.parseInt(args[0]);
        int tests = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(number, tests);
        StdOut.println("mean                    = " + String.valueOf(ps.mean()));
        StdOut.println("stddev                  = " + String.valueOf(ps.stddev()));
        StdOut.println("95% confidence interval = " + "[" + String.valueOf(ps.confidenceLo()) + ", " + String.valueOf(ps.confidenceHi()) + "]" );
    }

}
