package binPacking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabor Mihucz
 * 22399495M
 */

/**
 * This class is for running Threads, hence it implements the Runnable interface
 */

public class BinPackingProblem implements Runnable {

	/**
	 * Creation of the necessary fields and the corresponding constructor.
	 */

	private List<Integer> weights = new ArrayList<Integer>();
	private int capacity;
	private static List<List<Bin>> listOfReturnBinLists = new ArrayList<List<Bin>>();

	public BinPackingProblem(List<Integer> weights, int capacity) {
		this.weights = weights;
		this.capacity = capacity;
	}

	/**
	 * Method to run the threads. The lists (type Bin) that each thread returns are
	 * stored in listOfReturnBinLists
	 */

	public void run() {
		List<Bin> binToReturn = new ArrayList<Bin>();
		binToReturn = PackingStrategy.packBestFit(weights, capacity);
		listOfReturnBinLists.add(binToReturn);
	}

	/**
	 * Getter for the listOfReturnBinLists (Since it is private, a getter is needed
	 * to access it from another class.)
	 */

	public static List<List<Bin>> getBins() {
		return listOfReturnBinLists;
	}

}
