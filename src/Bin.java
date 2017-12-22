package binPacking;

import java.util.ArrayList;
import java.util.List;

/**
 * Gabor Mihucz 2239495M
 * 
 */

public class Bin {

	/**
	 * Creation of fields: capacity and (list of ) weights. And the corresponding
	 * constructor for the class Bin
	 */

	private int capacity;
	private List<Integer> weights;

	public Bin(int capacity) {
		this.capacity = capacity;
		this.weights = new ArrayList<Integer>();
	}

	/**
	 * In order to get the available free space: Add all of the elements in weights
	 * and subtract the result from the capacity.
	 */

	public int getSpace() {
		int sum = 0;
		for (Integer weight : this.weights) {
			sum = sum + weight;
		}

		return this.capacity - sum;
	}

	/**
	 * 
	 * @param weight
	 *            : If smaller than the result of getSpace(), add it to the list of
	 *            weights
	 * @throws IllegalArgumentException
	 *             (otherwise)
	 */

	public void store(int weight) throws IllegalArgumentException {
		if (this.getSpace() >= weight) {
			this.weights.add(weight);
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Auto-generated hashCode() and equals(Object obj) methods
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((weights == null) ? 0 : weights.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bin other = (Bin) obj;
		if (capacity != other.capacity)
			return false;
		if (weights == null) {
			if (other.weights != null)
				return false;
		} else if (!weights.equals(other.weights))
			return false;
		return true;
	}

}
