package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	// 0th element and (N^2 + 1)th elements are imaginary, created to facilitate percolation checking.
	// If the 0th element and the last element are connected, the whole system percolates.
	private WeightedQuickUnionUF connectedSites;
	// This additional set is created to prevent the bug when determining if a site is full
	// by inspecting only connectedSites.
	private WeightedQuickUnionUF fullSites;
	private int sideSize;
	private int openSitesNum;

	// array of site status: open or not.
	private boolean[][] siteArray;

	public Percolation(int N) {
		connectedSites = new WeightedQuickUnionUF(N * N + 2);
		fullSites = new WeightedQuickUnionUF(N * N + 1);
		sideSize = N;
		openSitesNum = 0;
		siteArray = new boolean[N][N];
	}

	public void open(int row, int col) {
		if ((row > sideSize - 1) || (col > sideSize - 1)) {
			throw new IndexOutOfBoundsException("The input size of the row/column exceeds the given size.");
		}

		if ((row < 0) || (col < 0)) {
			throw new IllegalArgumentException("The number of row/column must be positive integers.");
		}

		int index = row * sideSize + col + 1;

		siteArray[row][col] = true;
		openSitesNum += 1;

		// storing the index of the first-row open site after the last non-zero item of the array.
		if (index > 0 && index <= sideSize) {
			connectedSites.union(0, index);
			fullSites.union(0, index);
		}

		// when index is between (N*N - N) and (N*N - 1), the last row is reached.
		if ((index > sideSize * sideSize - sideSize) && (index <= sideSize * sideSize)) {
			connectedSites.union(sideSize * sideSize + 1, index);
		}

		// calculate the index of sites that share borders with this site.
		if (row == 0) {
			if (siteArray[row + 1][col]) {
				connectedSites.union(index, index + sideSize);
				fullSites.union(index, index + sideSize);
			}
		} else if (row == sideSize - 1) {
			if (siteArray[row - 1][col]) {
				connectedSites.union(index, index - sideSize);
				fullSites.union(index, index - sideSize);
			}
		} else {
			if (siteArray[row + 1][col]) {
				connectedSites.union(index, index + sideSize);
				fullSites.union(index, index + sideSize);
			}
			if (siteArray[row - 1][col]) {
				connectedSites.union(index, index - sideSize);
				fullSites.union(index, index - sideSize);
			}
		}

		if (col == 0) {
			if (siteArray[row][col + 1]) {
				connectedSites.union(index, index + 1);
				fullSites.union(index, index + 1);
			}
		} else if (col == sideSize - 1) {
			if (siteArray[row][col - 1]) {
				connectedSites.union(index, index - 1);
				fullSites.union(index, index - 1);
			}
		} else {
			if (siteArray[row][col + 1]) {
				connectedSites.union(index, index + 1);
				fullSites.union(index, index + 1);
			}
			if (siteArray[row][col - 1]) {
				connectedSites.union(index, index - 1);
				fullSites.union(index, index - 1);
			}
		}
	}

	public boolean isOpen(int row, int col) {
		if ((row > sideSize - 1) || (col > sideSize - 1)) {
			throw new IndexOutOfBoundsException("The input size of the row/column exceeds the given size.");
		}

		if ((row < 0) || (col < 0)) {
			throw new IllegalArgumentException("The number of row/column must be positive integers.");
		}

		return siteArray[row][col];
	}

	public boolean isFull(int row, int col) {
		if ((row > sideSize - 1) || (col > sideSize - 1)) {
			throw new IndexOutOfBoundsException("The input size of the row/column exceeds the given size.");
		}

		if ((row < 0) || (col < 0)) {
			throw new IllegalArgumentException("The number of row/column must be positive integers.");
		}
		int index = row * sideSize + col + 1;

		return fullSites.connected(0, index);
	}

	public int numberOfOpenSites() {
		return openSitesNum;
	}

	public boolean percolates() {
		return connectedSites.connected(0, sideSize * sideSize + 1);
	}

	public static void main(String[] args) {

	}
}                       
