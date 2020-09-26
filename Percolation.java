import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    private int[][] grid;
    private final int size;
    private int open_site;
    private final int uf_size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF check_backwash;

    //Constructor
    //Initially every grid is blocked
    // 1 is opened, 0 is blocked
    public Percolation(int N) {
        //Check for invalid input
        if (N <= 0) {
            throw new IllegalArgumentException("Invalid");
        }
        this.size = N;
        this.grid = new int[this.size][this.size];  // NxN grids
        this.open_site = 0;
        this.uf_size = this.size * this.size + 2;    //add 2 position at the beginng and at the end for 2 virtual sites.

        this.uf = new WeightedQuickUnionUF(this.uf_size);
        this.check_backwash = new WeightedQuickUnionUF(this.uf_size);

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.grid[i][j] = 0;
            }
        }
    }

    // Open site (row, col) if it is not open already.
    // Connect to all the open neighbor sites ( up, down, left, right)
    // Check individually with if statements
    public void open(int row, int col) {
        // Accept row and col == 0
        if (row < 0 || row >= this.size || col >= this.size || col < 0) {
            throw new IndexOutOfBoundsException("Row or column is out of bound.");
        }

        boolean opened = isOpen(row, col);
        if (!opened) {
            this.grid[row][col] = 1;
            this.open_site++;
        }

        // Connect with the upper site
        // Check if the site is at the top row and union check_backwash
        // Connect to the top virtual site if it is
        if (row == 0) {
            this.uf.union(this.encode(row, col), 0);
            this.check_backwash.union(this.encode(row, col), 0);
        } else if (this.isOpen(row - 1, col)) {
            this.uf.union(this.encode(row, col), this.encode(row - 1, col));
            this.check_backwash.union(this.encode(row, col), this.encode(row - 1, col));
        }


        // Connect the bottom site
        // Check if the site is at the bottom row. This won't union the check_backwash to avoid backwash case.
        // Connect to the bottom virtual site if it is
        if (row == this.size - 1) {
            this.uf.union(this.encode(row, col), this.uf_size - 1);
        } else if (this.isOpen(row + 1, col)) {
            this.uf.union(this.encode(row, col), this.encode(row + 1, col));
            this.check_backwash.union(this.encode(row, col), this.encode(row + 1, col));
        }


        // Connect the left site
        // col - 1 is the condition to check if out of bound. This will accept col - 1 == 0
        if (col - 1 >= 0 && this.isOpen(row, col - 1)) {
            this.uf.union(this.encode(row, col), this.encode(row, col - 1));
            this.check_backwash.union(this.encode(row, col), this.encode(row, col - 1));
        }

        // Connect the right site
        if (col + 1 < this.size && this.isOpen(row, col + 1)) {
            this.uf.union(this.encode(row, col), this.encode(row, col + 1));
            this.check_backwash.union(this.encode(row, col), this.encode(row, col + 1));
        }

    }

    // Is site (row, col) open?
    // If the value of grid[row][col] == 1, return true
    // Otherwise, return false
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= this.size || col >= this.size || col < 0) {
            throw new IndexOutOfBoundsException("Row or column is out of bound.");
        }
        return this.grid[row][col] == 1;

    }

    // Is site (row, col) full?
    // Use check_backwash instead of uf.
    // This will avoid the back_wash case and give an accurate GUI
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= this.size || col >= this.size || col < 0) {
            throw new IndexOutOfBoundsException("Row or column is out of bound.");
        }
        int current_index = this.encode(row, col);
        return this.check_backwash.find(current_index) == this.check_backwash.find(0);

    }

    // Number of open sites.
    public int numberOfOpenSites() {
        return this.open_site;
    }

    // Does the system percolate?
    // Use uf instead of check_backwash, check_backwash won't check the connectivity with the bottom virtual site.
    // If the top virtual site and the bottom virtual site is connected, return true
    // Otherwise, return false
    public boolean percolates() {
        return this.uf.find(0) == this.uf.find(this.uf_size - 1);
    }

    // An integer ID (1...N) for site (row, col).
    private int encode(int row, int col) {
        return row * this.size + col + 1;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        } else {
            StdOut.println("does not percolate");
        }

        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
