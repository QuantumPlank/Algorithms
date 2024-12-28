import java.util.Arrays;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] board;
    private int boardSize;

    public Board(int[][] tiles) {
        board = tiles;
        boardSize = tiles.length;
    }

    public String toString() {
        StringBuilder representation = new StringBuilder();
        representation.append(String.valueOf(boardSize) + "\n");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                representation.append(String.valueOf(board[i][j]) + " ");
            }
            representation.append("\n");
        }
        return representation.toString();
    }

    public int dimension() {
        return boardSize;
    }

    public int hamming() {
        int counter = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] != 0 && board[i][j] != (i * boardSize + j + 1)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                int goalY = (board[i][j] - 1) / boardSize;
                int goalX = (board[i][j] - 1) % boardSize;
                distance += Math.abs(goalX - j) + Math.abs(goalY - i);
            }
        }
        return distance;
    }

    public boolean isGoal() {
        if (hamming() == 0) {
            return true;
        }
        return false;
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board otherBoard = (Board)y;
        if (otherBoard.dimension() != otherBoard.dimension()) {
            return false;
        }
        
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] != otherBoard.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighborsStack = new Stack<Board>();
        int zeroX = 0;
        int zeroY = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                if (twinBoard[i][j] == 0) {
                    zeroX = j;
                    zeroY = i;
                }
            }
        }
        if (isInBounds(zeroX + 1, zeroY)) {
            int[][] neighborBoard = Arrays.copyOf(board, boardSize);
            neighborBoard[zeroY][zeroX] = neighborBoard[zeroY][zeroX + 1];
            neighborBoard[zeroY][zeroX + 1] = 0;
            neighborsStack.add(new Board(neighborBoard));
        }
        if (isInBounds(zeroX - 1, zeroY)) {
            int[][] neighborBoard = Arrays.copyOf(board, boardSize);
            neighborBoard[zeroY][zeroX] = neighborBoard[zeroY][zeroX - 1];
            neighborBoard[zeroY][zeroX - 1] = 0;
            neighborsStack.add(new Board(neighborBoard));
        }
        if (isInBounds(zeroX, zeroY + 1)) {
            int[][] neighborBoard = Arrays.copyOf(board, boardSize);
            neighborBoard[zeroY][zeroX] = neighborBoard[zeroY + 1][zeroX];
            neighborBoard[zeroY + 1][zeroX] = 0;
            neighborsStack.add(new Board(neighborBoard));
        }
        if (isInBounds(zeroX, zeroY - 1)) {
            int[][] neighborBoard = Arrays.copyOf(board, boardSize);
            neighborBoard[zeroY][zeroX] = neighborBoard[zeroY - 1][zeroX];
            neighborBoard[zeroY - 1][zeroX] = 0;
            neighborsStack.add(new Board(neighborBoard));
        }
        return  neighborsStack;
    }

    private boolean isInBounds(int x, int y) {
        if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
            return true;
        }
        return false;
    }

    public Board twin() {
        int[][] twinBoard = Arrays.copyOf(board, boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                if (twinBoard[i][j] != 0 && twinBoard[i][j+1] != 0) {
                    return new Board(twinBoard);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        In in;
        int n;
        
        in = new In(args[0]);
        n = in.readInt();
        int[][] tiles1 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles1[i][j] = in.readInt();
            }
        }
        in = new In(args[1]);
        n = in.readInt();
        int[][] tiles2 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles2[i][j] = in.readInt();
            }
        }

        Board board1 = new Board(tiles1);
        Board board2 = new Board(tiles2);
        StdOut.println(board1.toString());
        StdOut.println(board2.toString());

        StdOut.println("Are boards equal? " + String.valueOf(board1.equals(board2)));
        

    }
}