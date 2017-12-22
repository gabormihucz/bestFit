package binPacking;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class PackingStrategy {

	public PackingStrategy() {

	}

	/**
	 * Static method that takes care of the packing up the list of weights according
	 * to the capacity constraint
	 */

	public static List<Bin> packBestFit(List<Integer> weights, int capacity) {

		/**
		 * Sort list of weights in descending order
		 */

		Collections.sort(weights, Collections.reverseOrder());

		/**
		 * To start the packing: Create returnBinList; a newBin with the standard
		 * capacity parameter, and add it to returnBinList
		 */

		List<Bin> returnBinList = new ArrayList<Bin>();
		Bin newBin = new Bin(capacity);
		returnBinList.add(newBin);

		for (int iterWeights = 0; iterWeights < weights.size(); iterWeights++) {

			/**
			 * Initialise minimum space left and the index of best bin
			 */

			int min = capacity + 1;
			int rightIndex = 0;

			/**
			 * For each bin, check how much space would be left after adding the weight. If
			 * more than 0, but less than the so far lowest, update it to be the latest
			 * minimum, and store its index
			 */

			for (int iterBins = 0; iterBins < returnBinList.size(); iterBins++) {

				if ((returnBinList.get(iterBins).getSpace() - weights.get(iterWeights) >= 0)
						&& (returnBinList.get(iterBins).getSpace() - weights.get(iterWeights) <= min)) {

					min = returnBinList.get(iterBins).getSpace() - weights.get(iterWeights);
					rightIndex = iterBins;

				}

				/**
				 * If there are no more bins to check:
				 */

				if (iterBins == returnBinList.size() - 1) {

					/**
					 * If the lowest number is greater than the capacity, the item did not fit in
					 * any bin, hence create a new one, add the item into it, add the new bin to the
					 * binList and move on to checking the next weight. If min is different, put the
					 * weight into the bin with index min.
					 */

					if (min == capacity + 1) {

						Bin tempBin = new Bin(capacity);
						tempBin.store(weights.get(iterWeights));
						returnBinList.add(tempBin);
						break;

					} else {

						returnBinList.get(rightIndex).store(weights.get(iterWeights));

					}
				}

			}
		}

		return returnBinList;

	}

	public static List<Bin> packBestFitParallel(List<Integer> weights, int capacity, int numThreads) {

		/**
		 * Split the list of weights into numThreads-many equal-sized sublists. And
		 * store them in a list of lists <type Integer>
		 */

		int sizeOfSubList = weights.size() / numThreads;
		List<List<Integer>> listsOfWeights = new ArrayList<List<Integer>>();
		for (int i = 0; i < numThreads; i++) {
			List<Integer> tempWeights = weights.subList(i * sizeOfSubList, i * sizeOfSubList + sizeOfSubList);
			listsOfWeights.add(tempWeights);
		}

		/**
		 * Start a new Thread with a new BinPackingProblem instance for each
		 * weight-sublist And store these threads in a list.
		 */

		List<Thread> threadList = new ArrayList<Thread>();
		for (int i = 0; i < listsOfWeights.size(); i++) {
			Thread t = new Thread(new BinPackingProblem(listsOfWeights.get(i), capacity));
			t.start();
			threadList.add(t);
		}
		/**
		 * Then go through each thread in the list of threads and join them. "Catching"
		 * any InterruptedException, because this is how it has to be done.
		 */
		try {
			for (int i = 0; i < threadList.size(); i++) {
				threadList.get(i).join();
			}
		} catch (InterruptedException ex) {

		}

		/**
		 * Turn the list of list (type Bin) that was created by the threads and stored
		 * in the class BinPackingProblem into a single list (type Bin)
		 */

		List<Bin> listBinFlat = new ArrayList<Bin>();
		for (int i = 0; i < BinPackingProblem.getBins().size(); i++) {
			for (int g = 0; g < BinPackingProblem.getBins().get(i).size(); g++) {
				listBinFlat.add(BinPackingProblem.getBins().get(i).get(g));
			}
		}

		return listBinFlat;

	}

}
