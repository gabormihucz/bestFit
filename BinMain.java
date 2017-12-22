package binPacking;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A main method to test the bin-packing code developed in JP2 lab 8.
 * 
 * @author Mary Ellen Foster
 */
public class BinMain {

	/** Constants controlling the bin-packing problem instance */
	/**
	 * How many items to include (the "_" does not affect the value but makes it
	 * more readable)
	 */
	private static final int NUM_WEIGHTS = 100_000;

	/** The maximum weight of any given item */
	private static final int MAX_WEIGHT = 10;

	/** The capacity of each bin */
	private static final int BIN_CAPACITY = 100;

	/** How many threads to use in the parallel version */
	private static final int NUM_THREADS = 10;

	public static void main(String[] args) {
		// Generate the weights randomly
		// Ensure ArrayList has sufficient capacity to avoid reallocation
		List<Integer> weights = new ArrayList<>(NUM_WEIGHTS);
		Random rand = new Random();
		for (int i = 0; i < NUM_WEIGHTS; i++) {
			weights.add(rand.nextInt(MAX_WEIGHT) + 1);
		}

		// Run the single-threaded version
		Instant start = Instant.now();
		System.out.println("Single-thread version started at " + start);
		List<Bin> bins = PackingStrategy.packBestFit(weights, BIN_CAPACITY);
		Instant end = Instant.now();
		System.out.println("Single-thread version finished at " + end);
		System.out.println("Duration " + Duration.between(start, end).toMillis() + "ms");

		// Assess the quality by checking the remaining space
		int space = bins.stream().mapToInt(b -> b.getSpace()).sum();
		System.out.println("Number of bins: " + bins.size());
		System.out.println("Unused space: " + space);

		// Blank line to break things up ...
		System.out.println();

		// Run the multi-threaded version
		start = Instant.now();
		System.out.println("Parallel version started at " + start);
		bins = PackingStrategy.packBestFitParallel(weights, BIN_CAPACITY, NUM_THREADS);
		end = Instant.now();
		System.out.println("Parallel version finished at " + end);
		System.out.println("Duration " + Duration.between(start, end).toMillis() + "ms");

		// Assess the quality by checking the remaining space
		space = bins.stream().mapToInt(b -> b.getSpace()).sum();
		System.out.println("Number of bins: " + bins.size());
		System.out.println("Unused space: " + space);

	}
}
