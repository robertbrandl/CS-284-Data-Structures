
public class hw2 {
	//Robert Brandl Recitation: RI
	//I pledge my honor that I have abided by the Stevens Honor System.

	/**
	 * Represents a method with a time complexity of O(n), with an accumulator that updates for each operation
	 * ---Provided in the hw document
	 * @param n
	 */
	public static void method0 ( int n) {
		int counter=1;
		for (int i=0; i<n; i++) {
			System.out.println(" Operation "+ counter);
			counter++;
		}
	}
	
	/**
	 * Represents a method with a time complexity of O(n^2), with an accumulator that updates for each operation
	 * Adding a second for loop will achieve n^2 time complexity
	 * @param n
	 */
	public static void method1(int n) {
		int counter=1;
		for (int i=0; i<n; i++) {// first loop: n
			for (int j=0; j<n; j++) { //second loop: n
				System.out.println(" Operation "+ counter);
				counter++;
			}
		}
	}
	
	/**
	 * Represents a method with a time complexity of O(n^3), with an accumulator that updates for each operation
	 * Adding a third for loop will achieve n^3 time complexity
	 * @param n
	 */
	public static void method2(int n) {
		int counter=1;
		for (int i=0; i <n; i++) {// n
			for (int j=0; j <n; j++) { //n
				for (int k=0; k <n; k++) { //n
					System.out.println(" Operation "+ counter );
					counter ++;
				}
			}
		}
	}
	
	/**
	 * Represents a method with a time complexity of O(log n), with an accumulator that updates for each operation
	 * Can be achieved through updating i by multiplying itself by 2 (base 2 log)
	 * @param n
	 */
	public static void method3 (int n) {
		int counter = 1;
		for (int i=1; i < n; i=i*2) {
			System.out.println(" Operation "+ counter );
			counter++;
		}
	}
	
	/**
	 * Represents a method with a time complexity of O(n log n), with an accumulator that updates for each operation
	 * Use the n loop outside of the log n loop (essentially combine them)
	 * @param n
	 */
	public static void method4 (int n) {
		int counter = 1;
		for (int i=0; i <n; i++) { //runs n times
			for (int j =1; j < n; j=j*2) {//runs log n times
				System.out.println(" Operation "+ counter );
				counter++;
			}
		}
	}
	
	/**
	 * Represents a method with a time complexity of O(log log n), with an accumulator that updates for each operation
	 * Methodology: trial and error, plugging in values to determine patterns where if n = 4, 1 operation is completed;
	 * when n = 16, 2 operations completed; when n = 256, 3 operations completed; etc.
	 * Used the relations to determine that 1 operation should be completed every time i is multiplied by itself, beginning with 4
	 * @param n
	 */
	public static void method5 (int n) {
		int counter = 1;
		for (long i = 4; i <= n; i*=i) { //runs log log n times, long allows for greater values
			System.out.println(" Operation "+ counter );
			counter++;
		}
	}
	
	/**
	 * Represents a method with a time complexity of O(2^n), with an accumulator that updates for each operation
	 * Uses recursion (I used fibonacci!) where two recursive calls are made within the else statement
	 * @param n
	 */
	public static int count = 1;//counter for method6 to track the operations since function is recursive
	public static int method6 (int n) {
		if (n == 0) {
			System.out.println(" Operation "+ count );
			count++;
			return 1;
		}
		else {
			return method6(n-1) + method6(n-1);
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//method5(65536);
	}

}
