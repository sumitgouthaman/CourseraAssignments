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
    
    private boolean[][] grid;
    private WeightedQuickUnionUF unionFind;
    private int TOP = 0;
    private int BOTTOM;
    private int N;
    
    public Percolation(int N) {
        this.N = N;
        grid = new boolean[N][N];
        unionFind = new WeightedQuickUnionUF(N * N + 2);
        BOTTOM = N * N + 1;
    }
    
    public void open(int i, int j) {
        if(!isIndexValid(i, j)) {
            throw new IndexOutOfBoundsException("Grid coordinates invalid i = "
                                                    + i + ", j = " + j);
        }
        grid[i - 1][j - 1] = true;
        connectToOpenNeighbours(i, j);
    }
    
    public boolean isOpen(int i, int j) {
        if(!isIndexValid(i, j)) {
            throw new IndexOutOfBoundsException("Grid coordinates invalid i = "
                                                    + i + ", j = " + j);
        }
        return grid[i - 1][j - 1];
    }
    
    public boolean isFull(int i, int j) {
        if(!isIndexValid(i, j)) {
            throw new IndexOutOfBoundsException("Grid coordinates invalid i = "
                                                    + i + ", j = " + j);
        }
        return unionFind.connected(TOP, xyTo1D(i, j));
    }
    
    public boolean percolates() {
        return unionFind.connected(TOP, BOTTOM);
    }
    
    //Private helper methods
    private int xyTo1D(int x, int y) {
        return (x - 1) * N + y;
    }
    
    private boolean isIndexValid(int x, int y) {
        if((x >= 1) && (x <= N) && (y >= 1) && (y <= N)) {
            return true;
        } else {
            return false;
        }
    }
    
    private void connectToOpenNeighbours(int x, int y) {
        if(x == 1) unionFind.union(TOP, y);
        if(x == N) unionFind.union(xyTo1D(x, y), BOTTOM);
        
        int[][] neighbours = {
            {x - 1, y},
            {x + 1, y},
            {x, y - 1},
            {x, y + 1} 
        };
        
        for(int[] neighbour: neighbours) {
            if(isIndexValid(neighbour[0], neighbour[1])) {
                if(isOpen(neighbour[0], neighbour[1]))
                    unionFind.union(xyTo1D(x, y), xyTo1D(neighbour[0], neighbour[1]));
            }
        }
    }
}