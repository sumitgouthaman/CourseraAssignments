/*------------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          15 June 2014
 * Last updated:     16 June 2014
 * 
 * Compilation:      javac-algs4 PercolationStats.java
 * Execution:        java-algs4 PercolationStats [Grid Size] [No of Tests]
 * 
 * Performs multiple Percolation simulations and prints relevant statistics.
 * 
 * ---------------------------------------------------------------------------*/

/**
 * Class that performs multiple Percolation Threshold simulations and calculates
 * the mean and other relevant statistics of the simulations.
 * 
 * @author Sumit Gouthaman
 */
public class PercolationStats {
    private int N;                         // Grid size
    private double[] percolationThreshold; // Array storing results
    private double mean;                   // mean of all simulations
    private double stddev;                 // standard deviation
    private double confidenceLo;           // lower limit of 95% confidence
    private double confidenceHi;           // upper limit of 95% confidence
    
    /**
     * Constructor
     * 
     * @param N - Grid size
     * @param T - No of independent experiments
     * @throws IllegalArgumentException if N or T is <= 0
     */
    public PercolationStats(int N, int T) {
        if (N <= 0) throw new IllegalArgumentException("N = " + N);
        if (T <= 0) throw new IllegalArgumentException("T = " + T);
        
        this.N = N;
        
        percolationThreshold = new double[T];
        
        for (int t = 0; t < T; t++) {
            percolationThreshold[t] = runSimulation();
        }
        
        mean = StdStats.mean(percolationThreshold);
        stddev = StdStats.stddev(percolationThreshold);
        confidenceLo = mean - (1.96 * stddev / Math.sqrt(T));
        confidenceHi = mean + (1.96 * stddev / Math.sqrt(T));
        
        StdOut.println("mean                    = " + mean);
        StdOut.println("stddev                  = " + stddev);
        StdOut.println("95% confidence interval = " + confidenceLo + ", "
                           + confidenceHi);
    }
    
    /**
     * Returns mean
     */
    public double mean() {
        return mean;
    }
    
    /**
     * Returns standard deviation
     */
    public double stddev() {
        return stddev;
    }
    
    /**
     * Returns lower limit of 95% confidence
     */
    public double confidenceLo() {
        return confidenceLo;
    }
    
    /**
     * Returns upper limit of 95% confidence
     */
    public double confidenceHi() {
        return confidenceHi;
    }
    
    /**
     * Main method
     * 
     * @param args[0] - Grid Size
     * @param args[1] - No of independent experiments
     * @throws NumberFormatException if N or T is not an integer
     */
    public static void main(String[] args) {
        int N;
        int T;
        try {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("Command line arguments not integers!");
        }
        
        new PercolationStats(N, T);
    }
    
    /**
     * Run one round of simulations.
     * 
     * @returns calculated value of percolation threshold
     */
    private double runSimulation() {
        Percolation percolation = new Percolation(N);
        int openSites = 0;
        while (!percolation.percolates()) {
            int x = StdRandom.uniform(1, N + 1);
            int y = StdRandom.uniform(1, N + 1);
            if (!percolation.isOpen(x, y)) {
                percolation.open(x, y);
                openSites++;
            }
        }
        return (double) openSites / (double) (N * N);
    }
}