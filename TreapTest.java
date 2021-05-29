import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Treap.Treap;

class TreapTest {

	@Test
	void testAdd() {
		Treap<Integer> testTree = new Treap <Integer>();
		assertTrue(testTree.add(4,19));
		assertTrue(testTree.add(2,31));
		assertTrue(testTree.add(6,70));
		assertTrue(testTree.add(1,84));
		assertTrue(testTree.add (3,12));
		assertTrue(testTree.add(5,83));
		assertTrue(testTree.add(7,26));
		assertFalse(testTree.add (3,12));
		assertTrue(testTree.add(19));
		assertThrows(IllegalArgumentException.class, () -> {
			testTree.add(1,-1);
	    });
	}
	
	@Test
	void testFind() {
		Treap<Integer> testTree = new Treap <Integer>();
		testTree . add (4 ,19);
		testTree . add (2 ,31);
		testTree . add (6 ,70);
		testTree . add (1 ,84);
		testTree . add (3 ,12);
		testTree . add (5 ,83);
		testTree . add (7 ,26);
		assertTrue(testTree.find(7));
		assertTrue(testTree.find(1));
		assertTrue(testTree.find(4));
		assertFalse(testTree.find(0));
	}
	
	@Test
	void testTreapConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			Treap<Integer> testTree = new Treap <Integer>(-1);
	    });
	}
	
	@Test
	void testDelete() {
		Treap<Integer> testTree = new Treap <Integer>();
		testTree . add (4 ,19);
		testTree . add (2 ,31);
		testTree . add (6 ,70);
		testTree . add (1 ,84);
		testTree . add (3 ,12);
		testTree . add (5 ,83);
		testTree . add (7 ,26);
		assertTrue(testTree.delete(7));
		assertFalse(testTree.delete(7));
		assertTrue(testTree.delete(3));
		assertTrue(testTree.delete(2));
	}
	
	@Test
	void TreapFULLTEST() {
		Treap<Integer> testTree = new Treap <Integer>();
		assertTrue(testTree . add (4 ,19));
		assertTrue(testTree . add (2 ,31));
		testTree . add (6 ,70);
		assertTrue(testTree.delete(4));
		assertTrue(testTree.find(6));
	}

}
