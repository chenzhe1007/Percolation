
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.*;
import java.util.Scanner;
class Percolation {

    private WeightedQuickUnionUF uf = null;
    private int[] isVisited = null;
    int totalOpen, n, dummyTop, dummyBottom;
    int []dx = {1, -1, 0, 0};
    int []dy = {0, 0, 1, -1};
    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.isVisited = new int[n * n];
        this.totalOpen = 0;
        this.n = n;
        this.dummyTop = n * n;
        this.dummyBottom = n * n + 1;
    }

    public void open(int row, int col) {
        int siteID = getSiteID(row, col);

        if (isOpen(row, col)) {
            return;
        }

        isVisited[siteID] = 1;
        if (row == 1) {
            uf.union(siteID, dummyTop);
        }

        if (row == n) {
            uf.union(siteID, dummyBottom);
        }

        for (int i = 0; i < 4; i++) {
            int new_x = row + dx[i];
            int new_y = col + dy[i];

            if (new_x < 1 || new_x > n || new_y < 1 || new_y > n) {
                continue;
            }

            if (isOpen(new_x, new_y)) {
                uf.union(siteID, getSiteID(new_x, new_y));
            }
        }
    }

    public int getSiteID(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        return (row - 1) * n + (col - 1);
    }


    public boolean isOpen(int row, int col) {
        return isVisited[getSiteID(row, col)] == 1;
    }


    public int numberOfOpenSites() {
        return uf.count() - 2;
    }

    public boolean isFull(int row, int col) {
        return this.numberOfOpenSites() - totalOpen == 0;
    }

    public boolean  percolates() {
        return uf.connected(dummyTop, dummyBottom);
    }




    public static void main(String[] args) {

        FileInputStream in = null;
        try {
            in = new FileInputStream("/Users/Luke/Documents/Coursera/AlgorithmsPrinceton/Percolation/percolation/input8.txt");
            InputStream is = (InputStream) in;
            Scanner sc = new Scanner(is);

            String line = sc.nextLine();
            int n = Integer.parseInt(line.trim());
            Percolation a = new Percolation(n);
            System.out.println(a.percolates());
            while (sc.hasNext()){
                line = sc.nextLine();
                line = line.trim();
                String[] curLine = line.split("\\s+");
                //System.out.println(curLine[0]+ " : " + curLine[1]);
                int row = Integer.parseInt(curLine[0].trim());
                int col = Integer.parseInt(curLine[1].trim());
                System.out.println(row + " : " + col);
                System.out.println(a.getSiteID(row, col));
                a.open(row, col);
                System.out.println(a.percolates());
                //System.out.println(a.isOpen(row, col));
            }


            in.close();
        } catch(FileNotFoundException e) {
            System.out.println(e.fillInStackTrace());
        } catch(IOException e) {
            System.out.println(e.fillInStackTrace());
        }

    }
}