import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// Models an N-by-N percolation system.
public class Percolation {
    private int[][] grid;
    private int size;
    private int topNode = 1;
    private int bottomNode = 1;

    //Constructor
    //Initially every grid is blocked
    // 1 is opened, 0 is blocked
    public Percolation(int N) {
        //Check for invalid input
        if (N <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.size = N;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    this.grid[i][j] = 0;
                }
            }
        }
    }

    // Open site (row, col) if it is not open already.
    public void open(int row, int col) {
        if (row > this.size || col > this.size) {
            throw new IllegalArgumentException();
        } else {
            boolean opened = isOpen(row, col);
            if (!opened) {
                this.grid[row][col] = 1;
            }
        }
    }

    // Is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > this.size || col > this.size) {
            throw new IllegalArgumentException();
        } else {
            if (this.grid[row][col] == 1) {
                return true;
            }
            return false;
        }
    }

    // Is site (row, col) full?
    public boolean isFull(int row, int col) {

        return false;
    }

    // Number of open sites.
    public int numberOfOpenSites() {
        //...
        return 0;
    }

    // Does the system percolate?
    public boolean percolates() {
        //...
        return false;
    }

    // An integer ID (1...N) for site (row, col).
    private int encode(int row, int col) {
        //...
        return 0;
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
