import java.util.Arrays;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] board;
    private int[][] twinBoard;
    private int boardSize;
    private int hammingDistance;
    private int manhattanDistance;

    public Board(int[][] tiles) {
        board = tiles;
        boardSize = tiles.length;
        // Obtain Distances
        hammingDistance =  0;
        manhattanDistance = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] != 0 && board[i][j] != (i * boardSize + j + 1)) {
                    hammingDistance++;
                }
                if (board[i][j] != 0) {
                    int goalY = (board[i][j] - 1) / boardSize;
                    int goalX = (board[i][j] - 1) % boardSize;
                    manhattanDistance += Math.abs(goalX - j) + Math.abs(goalY - i);
                }
            }
        }
        // Create the twin board
        twinBoard = copyBoard(board);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                if (twinBoard[i][j] != 0 && twinBoard[i][j+1] != 0) {
                    int temp = twinBoard[i][j+1];
                    twinBoard[i][j+1] = twinBoard[i][j];
                    twinBoard[i][j] = temp;
                }
            }
        }
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
        return hammingDistance;
    }

    public int manhattan() {
        return manhattanDistance;
    }

    public boolean isGoal() {
        return hamming() == 0;
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
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    zeroX = j;
                    zeroY = i;
                }
            }
        }
        if (isInBounds(zeroX + 1, zeroY)) {
            int[][] neighborBoard = copyBoard(board);
            exchange(neighborBoard, zeroX, zeroY, zeroX+1, zeroY);
            neighborsStack.add(new Board(neighborBoard));
        }
        if (isInBounds(zeroX - 1, zeroY)) {
            int[][] neighborBoard = copyBoard(board);
            exchange(neighborBoard, zeroX, zeroY, zeroX-1, zeroY);
            neighborsStack.add(new Board(neighborBoard));
        }
        if (isInBounds(zeroX, zeroY + 1)) {
            int[][] neighborBoard = copyBoard(board);
            exchange(neighborBoard, zeroX, zeroY, zeroX, zeroY+1);
            neighborsStack.add(new Board(neighborBoard));
        }
        if (isInBounds(zeroX, zeroY - 1)) {
            int[][] neighborBoard = copyBoard(board);
            exchange(neighborBoard, zeroX, zeroY, zeroX, zeroY-1);
            neighborsStack.add(new Board(neighborBoard));
        }
        return  neighborsStack;
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    private boolean isInBounds(int x, int y) {
        return (x >= 0 && x < boardSize && y >= 0 && y < boardSize);
    }

    private void exchange(int[][] board, int x1, int y1, int x2, int y2) {
        if (isInBounds(x1, y1) && isInBounds(x2, y2)) {
            int temp = board[y1][x1];
            board[y1][x1] = board[y2][x2];
            board[y2][x2] = temp;
        }
    }

    public Board twin() {
        return new Board(twinBoard);
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

        StdOut.println("Are boards equal? " + String.valueOf(board1.equals(board2)));
        
        StdOut.println("Neighbors");
        for (Board nextBoard: board1.neighbors()) {
            StdOut.println(nextBoard.toString());
        }

    }
}