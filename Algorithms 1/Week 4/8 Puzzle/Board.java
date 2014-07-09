import java.util.ArrayList;

public class Board {
    private final int N;
    private final short[] board;
    private final int manhattanD;
    private final int hammingD;
    private int blank;
    
    public Board(int[][] blocks) {
        this(get1DArray(blocks), blocks.length);
    }
    
    private Board(short[] blocks, int N) {
        this.N = N;
        this.board = new short[N * N];
        int pos = 0;
        int hamD = 0;
        int manD = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.board[pos] = blocks[to1Dim(i, j)];
                if (board[pos] == 0) {
                    blank = pos;
                }
                if (board[pos] != (pos + 1) && board[pos] != 0) {
                    hamD++;
                }
                if (board[pos] != 0) {
                    int targetX = to2Dx(board[pos] - 1);
                    int targetY = to2Dy(board[pos] - 1);
                    manD += Math.abs(targetX - i) + Math.abs(targetY - j);
                }
                pos++;
            }
        }
        hammingD = hamD;
        manhattanD = manD;
    }
             
    public int dimension() {
        return N;
    }
    
    public int hamming() {
        return hammingD;
    }
    
    public int manhattan() {
        return manhattanD;
    }
    
    public boolean isGoal() {
        for (int i = 0; i < (N * N); i++) {
            if (board[i] == 0) continue;
            if (board[i] != (i + 1)) {
                return false;
            }
        }
        return true;
    }
    
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
        
    private int to1Dim(int x, int y) {
        if (x >= N || y >= N) return Integer.MAX_VALUE;
        if (x < 0 || y < 0) return Integer.MIN_VALUE;
        return (x * N) + y;
    }
     
    private int to2Dx(int p) {
        return (p / N);
    }
    
    private int to2Dy(int p) {
        return (p % N);
    }
    
    private static void exch(short[] arr, int pos1, int pos2) {
        short temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }
    
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
