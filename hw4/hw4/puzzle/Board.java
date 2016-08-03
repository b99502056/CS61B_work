package hw4.puzzle;

import java.util.Arrays;

public class Board {

	private int size; // dimension of the board
	private int[][] tiles;

	public Board(int[][] tiles) {
		size = tiles.length;
		this.tiles = new int[size][size];

		// Need to make a copy of the passed array so the board is immutable.
		for (int i = 0; i < size; i += 1) {
			this.tiles[i] = Arrays.copyOf(tiles[i], size);
		}
	}

	private boolean tileInTheRightPos(int row, int col) {

		// The right value for the bottom right spot is 0
		if ((row == size - 1) && (col == size - 1) && (tileAt(row, col) == 0)) {
			return true;
		}
		// Else where if the tile does not have the given value, it is not in the right position.
		if (tileAt(row, col) != size * row + col + 1) {
			return false;
		}

		return true;
	}

	public int tileAt(int row, int col) {
		return tiles[row][col];
	}

	public int size() {
		return size;
	}

	// We will account for previously made moves later in the solver.
	public int hamming() {
		int numOfWrongPos = 0;
		for (int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				if ((tileAt(i, j) != 0) && !tileInTheRightPos(i, j)) {
					numOfWrongPos += 1;
				}
			}
		}

		return numOfWrongPos;
	}

	// We will account for previously made moves later in the solver.
	public int manhattan() {
		int manhattanVal = 0;
		int tile;
		int tileRow;
		int tileCol;

		for (int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				if (tileAt(i, j) != 0) {
					tile = this.tileAt(i, j);
					tileRow = (tile - 1) / size;
					tileCol = tile - tileRow * size - 1;
					manhattanVal += Math.abs(tileRow - i) + Math.abs(tileCol - j);
				}
			}
		}
		return manhattanVal;
	}

	public boolean isGoal() {
		for (int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				if (!tileInTheRightPos(i, j)) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean equals(Board y) {
		for(int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				if (y.tileAt(i, j) != this.tileAt(i, j)) {
					return false;
				}
			}
		}

		return true;
	}

    /** Returns the string representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
