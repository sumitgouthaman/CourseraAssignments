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
public class PercolationStats {
    private int N;
    private double[] percolationThreshold;
    
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    
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
    
    public double mean() {
        return mean;
    }
    
    public double stddev() {
        return stddev;
    }
    
    public double confidenceLo() {
        return confidenceLo;
    }
    
    public double confidenceHi() {
        return confidenceHi;
    }
    
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