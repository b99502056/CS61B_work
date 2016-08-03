package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;

public class Solver {

	private class searchNode implements Comparable<searchNode> {
		private Board board;
		private int priority;

		public searchNode(Board b, int numOfPreviousMoves) {
			board = b;
			priority = b.manhattan() + numOfPreviousMoves;
		}

		public int priority() {
			return priority;
		}

		public int compareTo(searchNode n) {
			return priority - n.priority();
		}

		public Board board() {
			return board;
		}
	}

	private int moves;
	private LinkedList sequenceToSolution = new LinkedList();
	private MinPQ<searchNode> queue = new MinPQ<>();
	private searchNode currentNode;
	private searchNode previousNode;

	public Solver(Board initial) {
		moves = 0;
		currentNode = new searchNode(initial, moves);
		previousNode = new searchNode(initial, moves);


		while (!currentNode.board().isGoal()) {
			queue = new MinPQ<>();
			for (Board n : BoardUtils.neighbors(currentNode.board())) {
				if (!n.equals(previousNode.board())) {
					queue.insert(new searchNode(n, moves));
				}
			}

			sequenceToSolution.addLast(currentNode.board());
			previousNode = currentNode;
			currentNode = queue.delMin();
			moves += 1;
		}
		// Add solution to the liked list.
		sequenceToSolution.addLast(currentNode.board());
	}

	public int moves() {
		return moves;
	}

	public Iterable<Board> solution() {
		return sequenceToSolution;
	}


    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
       }
    }

}
