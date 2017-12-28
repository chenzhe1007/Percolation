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

    private double[] results = null; // holds the fraction of open site on each run for all trials
    private static final double ciRatio = 1.96; // holds the confidence interval ration for 95%
    /**
     * constructor method: it will instantiate a Percolation object and run trial to get the opensite fraction when percolate
     * @param n: number of sites on each dimension
     * @param trials: number of trials the simulation should run
     */
    public PercolationStats(int n, int trials) {

        results = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                perc.open(row, col);
            }
            results[i] = perc.numberOfOpenSites() / (n * n + 0.0);
        }
    }


    /**
     * calculate the mean for all trials
     * @return (double), mean value of all trial results
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * calculate the standard deviation for all trials
     * @return (double), standard deviation for all trials
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * calculate the confidence interval on the lower side
     * @return (double), confidence interval lower
     */
    public double confidenceLo() {
        return mean() - ciRatio * stddev() / Math.sqrt(results.length);
    }


    /**
     * calculate the confidence interval on the upper side
     * @return (double), confidence interval upper
     */
    public double confidenceHi() {
        return mean() + ciRatio * stddev() / Math.sqrt(results.length);
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
