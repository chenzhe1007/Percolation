/**
 * The purpose of the class is to run monte-carlo simulation
 * to calculate the threshold on which a material will percolate
 * or be capable of conducting electricity
 * @author  Zhe CHen
 * @version 1.0
 * @since   2017-12-28
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private static final double ciRatio = 1.96; // holds the confidence interval ration for 95%
    private double mean, stddev; // holds mean and standard deviation for the simulation run
    private int trials; // number of trials the test runs
    /**
     * constructor method: it will instantiate a Percolation object and run trial to get the opensite fraction when percolate
     * @param n: number of sites on each dimension
     * @param trials: number of trials the simulation should run
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        double []results = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; ++i) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                perc.open(row, col);
            }
            results[i] = perc.numberOfOpenSites() / (n * n + 0.0);
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
    }


    /**
     * calculate the mean for all trials
     * @return (double), mean value of all trial results
     */
    public double mean() {
        return mean;
    }

    /**
     * calculate the standard deviation for all trials
     * @return (double), standard deviation for all trials
     */
    public double stddev() {
        return stddev;
    }

    /**
     * calculate the confidence interval on the lower side
     * @return (double), confidence interval lower
     */
    public double confidenceLo() {
        return mean - ciRatio * stddev / Math.sqrt(trials);
    }


    /**
     * calculate the confidence interval on the upper side
     * @return (double), confidence interval upper
     */
    public double confidenceHi() {
        return mean + ciRatio * stddev / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats a = new PercolationStats(n, t);
        System.out.println("mean:                   = " + a.mean());
        System.out.println("stddev                  = " + a.stddev());
        System.out.println("95% confidence interval = [" + a.confidenceLo() + ", "+ a.confidenceHi() + "]");
    }
}
