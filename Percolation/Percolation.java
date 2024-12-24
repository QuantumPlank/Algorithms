import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int number;
    private WeightedQuickUnionUF topUF;
    private WeightedQuickUnionUF bottomUF;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N <= 0 ");
        }
        number = n;
        grid = new boolean[number][number];
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                grid[i][j] = false;
            }
        }
        // Two UF one for top another for bottom
        // Element 0 is the extra node
        topUF = new WeightedQuickUnionUF(number*number + 1);
        bottomUF = new WeightedQuickUnionUF(number*number + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isInBounds(row, col)) {
            grid[row - 1][col - 1] = true;
            // Check 4 adjacent squares
            if (isInGrid(row-1, col) && isOpen(row-1, col)) {
                topUF.union(getFlattenGridIndex(row, col), getFlattenGridIndex(row-1, col));
                bottomUF.union(getFlattenGridIndex(row, col), getFlattenGridIndex(row-1, col));
            }
            if (isInGrid(row, col-1) && isOpen(row, col-1)) {
                topUF.union(getFlattenGridIndex(row, col), getFlattenGridIndex(row, col-1));
                bottomUF.union(getFlattenGridIndex(row, col), getFlattenGridIndex(row, col-1));
            }
            if (isInGrid(row+1, col) && isOpen(row+1, col)) {
                topUF.union(getFlattenGridIndex(row, col), getFlattenGridIndex(row+1, col));
                bottomUF.union(getFlattenGridIndex(row, col), getFlattenGridIndex(row+1, col));
            }
            if (isInGrid(row, col+1) && isOpen(row, col+1)) {
                topUF.union(getFlattenGridIndex(row, col), getFlattenGridIndex(row, col+1));
                bottomUF.union(getFlattenGridIndex(row, col), getFlattenGridIndex(row, col+1));
            }
            // Connect to hidden nodes if it is top or bottom row
            if (row == 1) {
                topUF.union(getFlattenGridIndex(row, col), 0);
            }
            else if (row == number) {
                bottomUF.union(getFlattenGridIndex(row, col), 0);
            }
        }
        else {
            throw new IllegalArgumentException("Outside bounds");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isInBounds(row, col)) {
            return grid[row - 1][col - 1];
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isInBounds(row, col) && isOpen(row, col)) {
            return (topUF.find(getFlattenGridIndex(row, col)) == topUF.find(0));
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i <= number; i++) {
            for (int j = 1; j <= number; j++) {
                if (isOpen(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= number; i++) {
            for (int j = 1; j <= number; j++) {
                if (isOpen(i, j)) {
                    if (topUF.find(0) == topUF.find(getFlattenGridIndex(i, j)) && bottomUF.find(0) == bottomUF.find(getFlattenGridIndex(i, j))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Check if site is in bounds of the grid
    private boolean isInBounds(int row, int col) {
        if (row >= 1 && row <= number && col >= 1 && col <= number) {
            return true;
        }
        else{
            throw new IllegalArgumentException("Out of bounds");
        }
    }

    private boolean isInGrid(int row, int col) {
        if (row >= 1 && row <= number && col >= 1 && col <= number) {
            return true;
        }
        return false;
    }

    // Get the index of the square if grid was flattened
    private int getFlattenGridIndex(int row, int col) {
        return (row - 1) * number + (col - 1) + 1;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation pc = new Percolation(Integer.parseInt(args[0]));
        StdOut.println("Is full?: " + String.valueOf(pc.isFull(1, 1)));
        StdOut.println("Percolates?: " + String.valueOf(pc.percolates()));
    }
}
