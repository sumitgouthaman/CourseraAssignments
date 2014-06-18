/*------------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          15 June 2014
 * Last updated:     15 June 2014
 * 
 * Compilation:      javac-algs4 Percolation.java
 * Execution:        <No main method. Cannot be executed directly>
 * 
 * Class that stores current status of a Percolation simulation.
 * 
 * ---------------------------------------------------------------------------*/

/**
 * The <tt>Percolation</tt> class provides a set of methods to keep track of the
 * current status of open and closed sites in a Percolation simulation.
 * 
 * @author Sumit Gouthaman
 */
public class Percolation {
    
    private boolean[][] grid;               // To track of open & closed sites
    private WeightedQuickUnionUF unionFind; // Keeps track of partitions
    private WeightedQuickUnionUF fromTop;   // To avoid backwash
    private int TOP = 0;                    // node representing the top
    private int BOTTOM;                     // node representing the bottom
    private int N;                          // Size of grid
    
    /**
     * Constructor that initializes the necessary data structures
     * 
     * @param N - Size of grid (i.e. N x N square)
     */
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException("N = " + N);
        this.N = N;
        grid = new boolean[N][N];
        unionFind = new WeightedQuickUnionUF(N * N + 2);
        fromTop = new WeightedQuickUnionUF(N * N + 1);
        BOTTOM = N * N + 1;
    }
    
    /**
     * Opens a new site.
     * Also connects it to all its open neighbours in the union find data
     * structure keeping track of connected partitions.
     * 
     * @param i - x co-ordinate of the site
     * @param j - y co-ordinate of the site
     * @throws IndexOutOfBoundsException if (i, j) is not a valid site
     */
    public void open(int i, int j) {
        if (!isIndexValid(i, j)) {
            throw new IndexOutOfBoundsException("Grid coordinates invalid i = "
                                                    + i + ", j = " + j);
        }
        grid[i - 1][j - 1] = true;
        connectToOpenNeighbours(i, j);
    }
    
    /**
     * Checks whether a site is already open.
     * 
     * @param i - x co-ordinate of the site
     * @param j - y co-ordinate of the site
     * @throws IndexOutOfBoundsException if (i, j) is not a valid site
     */
    public boolean isOpen(int i, int j) {
        if (!isIndexValid(i, j)) {
            throw new IndexOutOfBoundsException("Grid coordinates invalid i = "
                                                    + i + ", j = " + j);
        }
        return grid[i - 1][j - 1];
    }
    
    /**
     * Checks whether a site is filled.
     * 
     * @param i - x co-ordinate of the site
     * @param j - y co-ordinate of the site
     * @throws IndexOutOfBoundsException if (i, j) is not a valid site
     */
    public boolean isFull(int i, int j) {
        if (!isIndexValid(i, j)) {
            throw new IndexOutOfBoundsException("Grid coordinates invalid i = "
                                                    + i + ", j = " + j);
        }
        return fromTop.connected(TOP, xyTo1D(i, j));
    }
    
    /**
     * Checks whether the grid has percolated.
     * 
     * @return boolean representing whether percolation has happened
     */
    public boolean percolates() {
        return unionFind.connected(TOP, BOTTOM);
    }
    /**-------------------------------------------------------------------------
     * Private helper methods
     * -----------------------------------------------------------------------*/
    
    /**
     * Converts from 2D space to a 1D space for use with the union find data
     * structure.
     * 
     * @param x - the x co-ordinate
     * @param y - the y co-ordinate
     * @returns the corresponding 1D index for the site
     */
    private int xyTo1D(int x, int y) {
        return (x - 1) * N + y;
    }
    
    /**
     * Checks whether a index is valid
     * 
     * @param x - the x co-ordinate
     * @param y - the y co-ordinate
     * @returns boolean representing validity of the site
     */
    private boolean isIndexValid(int x, int y) {
        if ((x >= 1) && (x <= N) && (y >= 1) && (y <= N)) {
            return true;
        } 
        return false;
    }
    
    /**
     * Connect a site to all ints open neighbours in the union find data
     * structure.
     * 
     * @param x - the x co-ordiante of the site
     * @param y - the y co-ordinate of the site
     */
    private void connectToOpenNeighbours(int x, int y) {
        if (x == 1) {
            unionFind.union(TOP, y);
            fromTop.union(TOP, y);
        }
        if (x == N) unionFind.union(xyTo1D(x, y), BOTTOM);
        
        int[][] neighbours = {
            {x - 1, y},
            {x + 1, y},
            {x, y - 1},
            {x, y + 1} 
        };
        
        for (int[] neighbour: neighbours) {
            if (isIndexValid(neighbour[0], neighbour[1])) {
                if (isOpen(neighbour[0], neighbour[1])) {
                    unionFind.union(xyTo1D(x, y), 
                                    xyTo1D(neighbour[0], neighbour[1]));
                    fromTop.union(xyTo1D(x, y), 
                                    xyTo1D(neighbour[0], neighbour[1]));
                }
            }
        }
    }
}