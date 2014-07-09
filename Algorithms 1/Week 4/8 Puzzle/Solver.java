public class Solver {
    private int noOfMoves;
    private Iterable<Board> solutionList;
    
    public Solver(Board initial) {
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
        
        minPQ.insert(new SearchNode(initial, 0, null));
        minPQTwin.insert(new SearchNode(initial.twin(), 0, null));
        
        while (!minPQ.isEmpty()) {
            SearchNode currentNode = minPQ.delMin();
            SearchNode currentNodeTwin = minPQTwin.delMin();
            
            if (currentNode.getBoard().isGoal()) {
                solutionList = getAllMoves(currentNode);
                noOfMoves = currentNode.getNoOfMoves();
                return;
            } else if (currentNodeTwin.getBoard().isGoal()) {
                solutionList = null;
                noOfMoves = -1;
                return;
            }
            
            Iterable<Board> neighbors = currentNode.getBoard().neighbors();
            for (Board neighbor: neighbors) {
                if (!currentNode.getBoard().equals(neighbor)) {
                    minPQ.insert(new SearchNode(neighbor, 
                                 currentNode.getNoOfMoves() + 1, currentNode));
                }
            }
            
            Iterable<Board> neighborsTwin = currentNodeTwin.getBoard()
                                                           .neighbors();
            for (Board neighbor: neighborsTwin) {
                if (!currentNodeTwin.getBoard().equals(neighbor)) {
                    minPQTwin.insert(new SearchNode(neighbor, 
                        currentNodeTwin.getNoOfMoves() + 1, currentNodeTwin
                    ));
                }
            }
        }
        solutionList = null;
        noOfMoves = -1;
    }
    public boolean isSolvable() {
        return solutionList != null;
    }
    
    public int moves() {
        return noOfMoves;
    }
    
    public Iterable<Board> solution() {
        return solutionList;
    }    
    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final int priority;
        private final SearchNode previousNode;
        
        public SearchNode(Board board, int moves, SearchNode previousNode) {
            this.board = board;
            this.moves = moves;
            this.priority = this.board.manhattan() + moves;
            this.previousNode = previousNode;
        }
        
        public Board getBoard() {
            return board;
        }
        
        public int getPriority() {
            return priority;
        }
        
        public int getNoOfMoves() {
            return moves;
        }
        
        public int compareTo(SearchNode that) {
            return (this.priority - that.priority);
        }
        
        public SearchNode getPrevious() {
            return previousNode;
        }
    }
    
    private Iterable<Board> getAllMoves(SearchNode searchNode) {
        if (searchNode == null) return null; 
        Stack<Board> stack = new Stack<Board>();
        SearchNode currentNode = searchNode;
        while (currentNode != null) {
            stack.push(currentNode.getBoard());
            currentNode = currentNode.getPrevious();
        }
        return stack;
    }
}