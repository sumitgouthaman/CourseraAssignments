/**-----------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          8 June 2014
 * Last updated:     9 July 2014
 * 
 * Compilation:      javac-algs4 Board.java
 * 
 * Stores a state of the board in 8 Puzzle solution tree
 * 
 *--------------------------------------------------------------------------- */
import java.util.ArrayList;

/**
 * Class to represent and store a certain state of the Board during the state
 * space search of 8 Puzzle.
 * 
 * @author Sumit Gouthaman
 */
public class Board {
    
    /**
     * NOTE:
     * Internally we use a short[] intead of int[][] to store the board as a way
     * to reduce space requirement and because the maximum dimension of board in
     * the assignment is 128. And a short is enough to store 128 x 128.
     */
    
    private final int N;          // Dimension of the board
    private final short[] board;  // The actual board in 1D representation
    private final int manhattanD; // Manhattan distance
    private final int hammingD;   // Hamming distance
    private int blank;      // Position of blank in 1D representation
    private final boolean isGoal; // Whether this board is the goal
    
    /**
     * Public Contructor
     * 
     * Takes a 2 dimentional board, makes the necessary converstion to represent
     * it as a 1D array and performs calculations for manhattan and hamming
     * distance
     * 
     * @param blocks 2D int array representing the board
     */
    public Board(int[][] blocks) {
        this(get1DArray(blocks), blocks.length);
    }
    
    /**
     * Private constructor that can directly initialize the Board from a 1D
     * array of shorts.
     * 
     * @param blocks 1D array representing a 2D board
     * @param N Dimension of the board
     */
    private Board(short[] blocks, int N) {
        this.N = N;
        this.board = new short[N * N];
        int pos = 0;
        int hamD = 0;
        int manD = 0;
        boolean goal = true;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.board[pos] = blocks[to1Dim(i, j)];
                if (board[pos] == 0) {
                    blank = pos;
                }
                // Keep track of manhattan and hamming distances
                if (board[pos] != (pos + 1) && board[pos] != 0) {
                    hamD++;
                }
                if (board[pos] != 0) {
                    int targetX = to2Dx(board[pos] - 1);
                    int targetY = to2Dy(board[pos] - 1);
                    manD += Math.abs(targetX - i) + Math.abs(targetY - j);
                }
                if (board[pos] != (pos + 1) && pos != (N*N - 1)) {
                    goal = false;
                }
                pos++;
            }
        }
        hammingD = hamD;
        manhattanD = manD;
        isGoal = goal;
    }
    
    /**
     * Return dimension of the board
     * 
     * @returns The dimentsion of the board
     */
    public int dimension() {
        return N;
    }
    
    /**
     * Returns hamming distance of the board from the goal board
     * 
     * @returns The hamming distance of the board
     */
    public int hamming() {
        return hammingD;
    }
    
    /**
     * Returns manhattan distance of the board from the goal board
     * 
     * @returns The hamming distance of the board
     */
    public int manhattan() {
        return manhattanD;
    }
    
    /**
     * Returns whether the board is the goal board
     * 
     * @returns boolean representing if the board is the goal board
     */
    public boolean isGoal() {
        return isGoal;
    }
    
    /**
     * Returns a twin for the board.
     * A twin is a similar board with two tiles in any non blank containing
     * row exchanged.
     * 
     * @returns The twin of this board
     */
    public Board twin() {
        int blankX = to2Dx(blank);
        int swapRow;
        if (blankX == 0) {
            swapRow = 1;
        } else {
            swapRow = blankX - 1;
        }
        short[] copy = new short[N * N];
        System.arraycopy(this.board, 0, copy, 0, N * N);
        exch(copy, to1Dim(swapRow, 0), to1Dim(swapRow, 1));
        return new Board(copy, N);
    }
    
    /**
     * @returns if the two board are equal
     */
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board yBoard = (Board) y;
        if (this.N != yBoard.N) return false;
        for (int i = 0; i < (N * N); i++) {
            if (this.board[i] != yBoard.board[i]) return false;
        }
        return true;
    }
    
    /**
     * @returns A string representation of the board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[to1Dim(i, j)]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    /**
     * @returns A iterable set of all neighbors of the current board
     */
    public Iterable<Board> neighbors() {
        int[] possibilities = getNeighbourPos(blank);
        ArrayList<Board> neighbours = new ArrayList<Board>();
        short[] temp = new short[N * N];
        System.arraycopy(board, 0, temp, 0, N * N);
        for (int possibility: possibilities) {
            exch(temp, blank, possibility);
            neighbours.add(new Board(temp, N));
            exch(temp, blank, possibility);
        }
        return neighbours;
    }
    
    /**
     * Convert from 2D coordinates to a 1D position.
     * In cases where the 2D coordinate is out of bounds, return extreme
     * integer values.
     * 
     * @param x Coordinate representing row
     * @param y Coordinate representing column
     * @returns The 1D coordinate
     */
    private int to1Dim(int x, int y) {
        if (x >= N || y >= N) return Integer.MAX_VALUE;
        if (x < 0 || y < 0) return Integer.MIN_VALUE;
        return (x * N) + y;
    }
     
    /**
     * @returns the 2D x coordinate for a 1D coordinate
     */
    private int to2Dx(int p) {
        return (p / N);
    }
    
    /**
     * @returns the 2D y coordinate for a 1D coordinate
     */
    private int to2Dy(int p) {
        return (p % N);
    }
    
    private static void exch(short[] arr, int pos1, int pos2) {
        short temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }
    
    /**
     * Convert from a 2D matrix of ints to a 1D array of shorts
     * 
     * @param blocks The 2D matrix of ints
     * @returns a 1D array of shorts
     */
    private static short[] get1DArray(int[][] blocks) {
        int n = blocks.length;
        short[] temp = new short[n * n];
        int pos = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[pos++] = (short) blocks[i][j];
            }
        }
        return temp;
    }
    
    /**
     * Get all valid neighbor positions for the given position
     * 
     * @param pos The current position
     * @returns The set of valid neighbors
     */
    private int[] getNeighbourPos(int pos) {
        ArrayList<Integer> nPos = new ArrayList<Integer>();
        int x = to2Dx(pos);
        int y = to2Dy(pos);
        int[][] possibilities = {
            {x - 1, y},
            {x + 1, y},
            {x, y - 1},
            {x, y + 1}
        };
        for (int[] coords: possibilities) {
            int coord = to1Dim(coords[0], coords[1]);
            if (coord >= 0 && coord < (N * N)) {
                nPos.add(coord);
            }
        }
        int[] positions = new int[nPos.size()];
        for (int i = 0; i < nPos.size(); i++) {
            positions[i] = nPos.get(i);
        }
        return positions;
    }
}
