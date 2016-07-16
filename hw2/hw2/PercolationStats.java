package hw2;                       
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

	private int[] testResults;
	private int testNum;

	public PercolationStats(int N, int T) {

		Percolation percTest;
		int[] randomIndex = new int[N * N];

		testResults = new int[T];
		testNum = T;

		for (int i = 0; i < N * N; i++) {
			randomIndex[i] = i;
		}

		for (int i = 0; i < T; i++) {
			percTest = new Percolation(N);
			StdRandom.shuffle(randomIndex);

			for (int j = 0; j < N * N; j++) {
				if (!percTest.percolates()) {
					percTest.open(randomIndex[j] / N, randomIndex[j] % N);
				} else {
					testResults[i] = percTest.numberOfOpenSites();
					break;
				}
			}
		}
	}

	public double mean() {
		return StdStats.mean(testResults);
	}

	public double stddev() {
		return StdStats.stddev(testResults);
	}

	public double confidenceLow() {
		return mean() - 1.96 * stddev() / Math.sqrt(testNum);
	}

	public double confidenceHigh() {
		return mean() + 1.96 * stddev() / Math.sqrt(testNum);
	}

	public static void main(String[] args) {

		PercolationStats test = new PercolationStats(50, 2000);

		double mean = test.mean();
		double stddev = test.stddev();

		System.out.println(mean);
		System.out.println(stddev);
	}
}                       
