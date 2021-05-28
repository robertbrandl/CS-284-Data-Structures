package sorting;
import java.util.HashSet;
import java.util.Set;

//Robert Brandl Recitation: RI
//I pledge my honor that I have abided by the Stevens Honor System.
public class Sort<T> {
	
	private static class Interval {
		private int lower;
		private int upper;
		/**
		 * Initializes the Interval with lower and upper
		 * @param lower - the lower bound for the interval
		 * @param upper - the upper bound for the interval
		 */
		public Interval (int lower, int upper) {
			if (lower < 0 || upper < 0 || upper < lower) {
				throw new IllegalArgumentException("Interval Constructor: invalid lower or upper bounds");
			}
			this.lower = lower;
			this.upper = upper;
		}
		/**
		 * Returns the lower bound of the interval
		 * @return this.lower
		 */
		public int getLower () {
			return this.lower;
		}
		/**
		 * Returns the upper bound of the interval
		 * @return this.upper
		 */
		public int getUpper () {
			return this.upper;
		}
		/**
		 * Checks whether the two intervals have the same upper and lower bounds
		 * @return true if the bounds are equivalent, false otherwise
		 */
		public boolean equals (Object o) {
			if (o == null) {
				throw new IllegalArgumentException("equals: cannot compare to a null object");
			}
			return (this.lower == ((Sort.Interval) o).getLower() && this.upper == ((Sort.Interval) o).getUpper());
		}
		/**
		 * Uses (lower*lower)+upper to compute the interval's hashcode
		 * @return the int hashcode of the interval
		 */
		public int hashCode () {
			return (lower*lower) + upper;
		}
	}
	
	//self-testing!
	public static void main (String[] args) {
		Integer[] a = {1,2};
		Sort.sort(a);
	}
	
	/**
	 * Wrapper function which calls the function for quick sort with the array to be sorted
	 * @param <T> represents the generic type
	 * @param array takes in an array of generic T (can be Integer, String, etc.)
	 */
	public static <T extends Comparable<T>> void sort (T[] array) {
		if (array == null || array.length <= 0) {
			throw new IllegalArgumentException("sort: cannot sort an empty or null array");
		}
		quickSort(array, 0, array.length - 1);
	}
	
	/**
	 * Performs the quicksort of the given array table, making calls to partition
	 * Uses a hashset to store the intervals being checked, runs iteratively rather than recursively
	 * @param <T> represents the generic type
	 * @param table - the array to be sorted
	 * @param first - the first index of the array
	 * @param last - the final index of the array (array.length - 1)
	 */
	private static <T extends Comparable<T>> void quickSort(T[] table, int first, int last) {
		if (first < last) {//ensures that the array contains more than one element to start, otherwise nothing happens
			Set<Interval> set = new HashSet<Interval>();//starts with an empty set
			set.add(new Interval(first,last));//adds the first Interval (the whole array)
			while (set.isEmpty() == false) {//iterative loop, checks whether there are no intervals in the set
				Interval x = set.iterator().next();//works with the first set present in set
				if (x.getUpper() - x.getLower() == 0) {//if only 1 element, leave it alone and remove that interval
					set.remove(x);
				}
				if (x.getUpper() - x.getLower() == 1) {//if 2 elements, sort them and then remove the interval x
					int i = table[x.getUpper()].compareTo(table[x.getLower()]);
					if (i < 0) {
						T temp = table[x.getLower()];
						table[x.getLower()] = table[x.getUpper()];
						table[x.getUpper()] = temp;
					}
					set.remove(x);
				}
				else {//otherwise, partition the interval if it is too large, and remove interval x
					int p = partition(table, x.getLower(), x.getUpper());//calls the partition function
					if (p > x.getLower() && p < x.getUpper()) {
						set.add(new Interval(x.getLower(),p-1));
						set.add(new Interval(p+1, x.getUpper()));
					}
					else if (p == x.getLower()) {
						set.add(new Interval(p+1,x.getUpper()));
					}
					else if (p == x.getUpper()) {
						set.add(new Interval(x.getLower(), p-1));
					}
					set.remove(x);
				}
			}
		}
		
	}
	/**
	 * Creates a partition of the current interval, and returns the integer value at which to split the interval
	 * @param <T> generic type
	 * @param table - the array to be sorted
	 * @param first - the lower bound of the interval to be partitioned
	 * @param last - the upper bound of the interval to be partitioned
	 * @return the integer at which to split the interval
	 */
	private static<T extends Comparable <T>> int partition(T[] table, int first, int last) {
		T fEle = table[first];//performs the median-of-three method to determine the pivot value
		int middle = (table.length - 1)/2;
		T mEle = table[middle];
		T lEle = table[last];
		if (fEle.compareTo(mEle) > 0) {
			T holder = table[first];
			table[first] = table[middle];
			table[middle] = holder;
		}
		if (mEle.compareTo(lEle) > 0) {
			T holder = table[middle];
			table[middle] = table[last];
			table[last] = holder;
		}
		if (fEle.compareTo(lEle) > 0) {
			T holder = table[first];
			table[first] = table[last];
			table[last] = holder;
		}
		T holder = table[first];
		table[first] = table[middle];
		table[middle] = holder;
		T pivot = table[first];//the pivot represents the median of the first, middle, and last table values
		int up = first;
		int down = last;
		do{
			while((up < last) && (pivot.compareTo(table[up])>=0)) {
				up++; 
			}
			while(pivot.compareTo(table[down]) < 0) {
				down--;
			}
			if(up < down) { // if up is to the left of down.
				T temp = table[up];
				table[up] = table[down];
				table[down] = temp;
			}
		} while(up < down); // Repeat while up is left of down.
		T temp = table[down];
		table[down] = table[first];
		table[first] = temp;
		return down;//value to split the interval!
	}
}
