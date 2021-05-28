import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.Test;

import sorting.Sort;
//Robert Brandl Recitation: RI
//I pledge my honor that I have abided by the Stevens Honor System.

class SortTest {

	@Test
	void test1() {
		Integer[] test = {3,2,1};
		Sort.sort(test);
		assertTrue(test[0] == 1);
		assertTrue(test[1] == 2);
		assertTrue(test[2] == 3);
	}
	
	@Test
	void test2() {
		String[] x = {"bat","zebra", "apple"};
		Sort.sort(x);
		assertTrue(x[0].equals("apple"));
		assertTrue(x[1].equals("bat"));
		assertTrue(x[2].equals("zebra"));
		
	}
	@Test
	void test3() {
		Integer[] test = {-1};
		Sort.sort(test);
		assertTrue(test[0] == -1);
	}
	@Test
	void test4() {
		Integer[] test = {};
		assertThrows(IllegalArgumentException.class, () -> {
			Sort.sort(test);
		});
	}
	@Test
	void test5() {
		Double[] test = {1.0, 19.9, 19.8, 3.7, 4.2};
		Sort.sort(test);
		assertTrue(test[0] == 1.0);
		assertTrue(test[1] == 3.7);
		assertTrue(test[2] == 4.2);
		assertTrue(test[3] == 19.8);
		assertTrue(test[4] == 19.9);
	}
	

}
