/**
 * The purpose of the class is to create a API that can
 * track the dynamic connectivity between two sites in a
 * n-by-n matrix
 * @author  Zhe CHen
 * @version 1.0
 * @since   2017-12-28
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int OPEN = 4; // 100
    private static final int PERCOLATE = 7; // 111
    private static final int CHECK_TO_TOP = 5; // 101
    private static final int CHECK_TO_BOTTOM = 6; // 110
    private final int n; // the dummy site in the top, the dummy site in the bottom, dimension size
    private static final int []dx = {1, -1, 0, 0}; // to get the neighbor in x axle
    private static final int []dy = {0, 0, 1, -1}; // to get the neighbor in y axle
    private WeightedQuickUnionUF uf = null;
    private char[] isVisited = null; // to see if a site has been visited before or is already being opened
    private int totalOpen; // total open sites
    private boolean isPercolate;
    /**
     * Constructor to create a n-by-n matrix with all its sites blocked
     * @param n: number of sites in each dimension
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.uf = new WeightedQuickUnionUF(n * n);
        this.isVisited = new char[n * n];
        this.totalOpen = 0;
        this.n = n;
        this.isPercolate = false;
    }

    /**
     * Open a blocked site, if a site is already open, then simply return
     * @param row: the coordinate on the vertical
     * @param col: the coordinate on the horizontal
     */
    public void open(int row, int col) {
        int siteID = getSiteID(row, col);

        if (isOpen(row, col)) {
            return;
        }

        if (row == 1 && row == n) {
            isVisited[siteID] = Character.forDigit(PERCOLATE,10);
        } else if (row == 1) {
            isVisited[siteID] = Character.forDigit(CHECK_TO_BOTTOM,10);
        } else if (row == n) {
            isVisited[siteID] = Character.forDigit(CHECK_TO_TOP,10);
        } else {
            isVisited[siteID] = Character.forDigit(OPEN,10);
        }
        totalOpen++;
        int status = isVisited[siteID] - '0';
        for (int i = 0; i < dx.length; i++) {
            int newX = row + dx[i];
            int newY = col + dy[i];
            if (newX < 1 || newX > n || newY < 1 || newY > n) {
                continue;
            }
            if (isOpen(newX, newY)) {
                int neighbor = getSiteID(newX, newY);
                int root = uf.find(neighbor);
                status = status | (isVisited[root] - '0');
                uf.union(siteID, getSiteID(newX, newY));
            }
        }
        int new_root = uf.find(siteID);
        isVisited[new_root] = Character.forDigit(status, 10);
        if (status == PERCOLATE && !isPercolate) {
            isPercolate = true;
        }
    }


    private int getSiteID(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        return (row - 1) * n + (col - 1);
    }

    /**
     *  check to see if a site is already open
     * @param row: the coordinate on the vertical
     * @param col: the coordinate on the horizontal
     * @return: (boolean) true means is open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        return (isVisited[getSiteID(row, col)] - '0' & OPEN) == OPEN;
    }


    /**
     * total opened site
     * @return: (int) total opened site
     */
    public int numberOfOpenSites() {
        return totalOpen;
    }


    public boolean isFull(int row, int col) {
        int site = getSiteID(row, col);
        int root = uf.find(site);
        return (isVisited[root] - '0' | CHECK_TO_TOP) == PERCOLATE;
    }

    /**
     * check if the n-by-n matrix percolates
     * @return: (boolean) true means percolates, false means otherwise
     */
    public boolean  percolates() {
        return isPercolate;
    }
}