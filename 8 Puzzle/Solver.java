import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private SearchNode solutionNode;

    public Solver(Board initial) {
        // PQ for Original Board
        MinPQ<SearchNode> pqOrig = new MinPQ<SearchNode>();
        SearchNode origNode = new SearchNode(initial, 0, null);
        pqOrig.insert(origNode);
        // PQ for Twin Board
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        SearchNode twinNode = new SearchNode(initial.twin(), 0, null);
        pqTwin.insert(twinNode);
        // Loop to get Min
        while (true) {
            SearchNode origMinNode = pqOrig.delMin();
            SearchNode twinMinNode = pqTwin.delMin();
            for (Board nextBoard: origMinNode.board.neighbors()) {
                if (origMinNode.previousNode == null || !nextBoard.equals(origMinNode.previousNode.board)) {
                    pqOrig.insert(new SearchNode(nextBoard, origMinNode.numberOfMoves + 1, origMinNode));
                }
            }
            for (Board nextBoard: twinMinNode.board.neighbors()) {
                if (twinMinNode.previousNode == null || !nextBoard.equals(twinMinNode.previousNode.board)) {
                    pqTwin.insert(new SearchNode(nextBoard, twinMinNode.numberOfMoves + 1, twinMinNode));
                }
            }
            if (origMinNode.board.isGoal()) {
                solutionNode = origMinNode;
                break;
            }
            else if (twinMinNode.board.isGoal()) {
                solutionNode = null;
                break;
            }
        }

    }

    private class SearchNode implements Comparable<SearchNode>{
        private Board board;
        private SearchNode previousNode;
        private int numberOfMoves;
        private int priority;

        public SearchNode(Board board, int numberOfMoves, SearchNode previousNode) {
            this.board = board;
            this.numberOfMoves = numberOfMoves;
            this.previousNode = previousNode;
            priority = board.manhattan() + this.numberOfMoves;
        }

        public int compareTo(SearchNode that) {
            if (this.priority > that.priority) {
                return 1;
            }
            if (this.priority < that.priority) {
                return -1;
            }
            return 0;
        }
    }

    public boolean isSolvable() {
        return solutionNode != null;
    }

    public int moves() {
        if (isSolvable()) {
            return solutionNode.numberOfMoves;
        }
        return -1;
    }

    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<>();
        SearchNode sn = solutionNode;
        while (sn != null) {
            solution.push(sn.board);
            sn = sn.previousNode;
        }
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
}
