package hw4.puzzle;

public class Board {

	private int size; // dimension of the board
	private int[][] tiles;
	private Board goal;

	public Board(int[][] tiles) {
		this.tiles = tiles;
		size = tiles.length;
		this.goalConstructor();
	}

	private void goalConstructor() {
		int[][] goalTiles = new int[size][size];
		int currentElement = 1;

		for (int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				if (currentElement == size * size) {
					break;
				}
				goalTiles[i][j] = currentElement;
				currentElement += 1;
			}
		}
		goal = new Board(goalTiles);
	}

	private boolean tileInTheRightPos(int row, int col) {
		if (tileAt(row, col) != row + size * col + 1) {
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
				if (!tileInTheRightPos(i, j)) {
					numOfWrongPos += 1;
				}
			}
		}

		return numOfWrongPos;
	}

	// We will account for previously made moves later in the solver.
	public int manhattan() {

	}

	public boolean isGoal() {
		return (this.equals(goal));
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
