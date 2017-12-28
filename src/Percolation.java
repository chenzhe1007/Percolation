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

    private WeightedQuickUnionUF uf = null; // main components repo
    private boolean[] isVisited = null; // to see if a site has been visited before or is already being opened
    private int totalOpen; // total open sites
    private final int dummyTop, dummyBottom, n; // the dummy site in the top, the dummy site in the bottom, dimension size
    private static final int []dx = {1, -1, 0, 0}; // to get the neighbor in x axle
    private static final int []dy = {0, 0, 1, -1}; // to get the neighbor in y axle

    /**
     * Constructor to create a n-by-n matrix with all its sites blocked
     * @param n: number of sites in each dimension
     */
    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.isVisited = new boolean[n * n];
        this.totalOpen = 0;
        this.n = n;
        this.dummyTop = n * n;
        this.dummyBottom = n * n + 1;
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

        isVisited[siteID] = true;
        totalOpen++;
        if (row == 1) {
            uf.union(siteID, dummyTop);
        }
        if (row == n) {
            uf.union(siteID, dummyBottom);
        }

        for (int i = 0; i < dx.length; i++) {
            int newX = row + dx[i];
            int newY = col + dy[i];

            if (newX < 1 || newX > n || newY < 1 || newY > n) {
                continue;
            }

            if (isOpen(newX, newY)) {
                uf.union(siteID, getSiteID(newX, newY));
            }
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
        return isVisited[getSiteID(row, col)];
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
        return uf.connected(dummyTop, site) || uf.connected(dummyBottom, col);
    }

    /**
     * check if the n-by-n matrix percolates
     * @return: (boolean) true means percolates, false means otherwise
     */
    public boolean  percolates() {
        return uf.connected(dummyTop, dummyBottom);
    }

}