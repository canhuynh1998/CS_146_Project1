import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    private int[][] grid;
    private int size;
    private int open_site;
    private WeightedQuickUnionUF uf;

    //Constructor
    //Initially every grid is blocked
    // 1 is opened, 0 is blocked
    public Percolation(int N) {
        //Check for invalid input
        if (N <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.size = N;
            this.open_site = 0;
            //add 2 position at the beginng and at the end for 2 virutal nodes.
            int union_find_size = this.size * this.size + 2;
            this.uf = new WeightedQuickUnionUF(union_find_size);
            for (int i = 1; i < (N + 1); i++) {
                for (int j = 1; j < (N + 1); j++) {
                    this.grid[i][j] = 0;
                }
            }
        }
    }

    // Open site (row, col) if it is not open already.
    // Connect to all the open neighbor sites ( up, down, left, right)
    // So have to call at most 4 call of uf.union(int, int)
    public void open(int row, int col) {
        if (row <= 0 || row > this.size || col > this.size || col <= 0) {
            throw new IllegalArgumentException();
        }
        boolean opened = isOpen(row, col);
        if (!opened) {
            this.grid[row][col] = 1;
            this.open_site++;
        }
        if (this.isOpen(row - 1, col)) {
            // Connect the top site if it's open
            this.uf.union(this.encode(row - 1, col), this.encode(row, col));
            //  Connect to the top virtual site
            if (row == 1) {
                this.uf.union(0, this.encode(row, col));
            } else if (row == this.size) {
                this.uf.union(this.size + 1, this.encode(row, col));    // connect to the bottom virtual site
            }
        } else if (this.isOpen(row, col + 1)) {
            this.uf.union(this.encode(row, col), this.encode(row, col + 1));
        } else if

    }

    // Is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > this.size || col > this.size || col <= 0) {
            throw new IllegalArgumentException();
        } else {
            return this.grid[row][col] == 1;
        }
    }

    // Is site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.uf.find(this.encode(row, col)) == this.uf.find(this.encode(row - this.size, col));
    }

    // Number of open sites.
    public int numberOfOpenSites() {
        return this.open_site;
    }

    // Does the system percolate?
    public boolean percolates() {
        return this.uf.find(0) == this.uf.find(this.size + 1);
    }

    // An integer ID (1...N) for site (row, col).
    private int encode(int row, int col) {
        return (row - 1) * this.size + col;
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
