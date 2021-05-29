package Treap;
//Robert Brandl Recitation: RI
//I pledge my honor that I have abided by the Stevens Honor System.
import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
	
	private static class Node<E>{
		//data fields of the Node class
		public E data; // key for the search
		public int priority; // random heap priority
		public Node <E> left;
		public Node <E> right;
		
		/**
		 * CONSTRUCTOR: Create a new node, throws exceptions for null data
		 * @param data - stores the data for the new node
		 * @param priority - value for the new node's priority
		 */
		public Node (E data, int priority) {
			if (data == null || priority < 0) {//checks for invalid data or negative priority
				throw new IllegalArgumentException("Node Constructor: null data or negative priority not allowed");
			}
			this.data = data;
			this.priority = priority;
			left = null;
			right = null;
		}
		
		//methods for Node
		/**
		 * Performs a right rotation. Updates the data and priority attributes as well as the left and right pointers 
		 * of the involved nodes accordingly.
		 * @return the node at the root of the rotation
		 */
		private Node<E> rotateRight() {
			Node<E> curr = this;
			curr = curr.left;
			this.left = curr.right;
			curr.right = this;
			return curr;
		}
		/**
		 * Performs a left rotation. Updates the data and priority attributes as well as the left and right pointers 
		 * of the involved nodes accordingly.
		 * @return the node at the root of the rotation
		 */
		private Node<E> rotateLeft() {
			Node<E> curr = this;
			curr = curr.right;
			this.right = curr.left;
			curr.left = this;
			return curr;
		}
		
		public String toString() {//returns a representation of the Node
			return "[key=" + data + ", priority=" + priority + "]";
		}


	}
	
	//data fields
	private Random priorityGenerator;
	private Node <E> root;

	//Constructors
	/**
	 * Create a new Treap, with an empty root and a random number generator
	 */
	public Treap () {
		priorityGenerator = new Random();//generates a random priority
		root = null;//starts root at null
	}
	
	/**
	 * Create a new Treap, with an empty root and a random number generator (uses seed)
	 * @param seed - provides the value for the priorityGenerator random number generator
	 */
	public Treap (long seed) {
		if (seed < 0) {//checks for invalid seed values
			throw new IllegalArgumentException("Treap Constructor: Invalid input for seed value");
		}
		priorityGenerator = new Random(seed);//generates a random priority based on seed
		root = null;//starts root at null
	}
	
	//METHODS
	/**
	 * Calls iterative add method to add the new Node to the treap, sends a random priority for the Node.
	 * @param key - the data of the Node to be added to the treap
	 * @return true if successfully added, false otherwise
	 */
	public boolean add (E key) {
		if (key == null) {//checks for invalid key value (null)
			throw new IllegalArgumentException("add: cannot have a null key");
		}
		return add(key, priorityGenerator.nextInt());//calls other add function
	}
	
	/**
	 * Adds a new node to the treap with the given key and priority, assuming priority is valid. Uses the helper function
	 * reheap to keep the treap properties
	 * @param key - the data of the Node to be added to the heap
	 * @param priority - the priority of the Node to be added to the heap
	 * @return true if successfully added, false otherwise
	 */
	public boolean add (E key, int priority) {
		if (priority < 0) {//checks for invalid priority (less than 0)
			throw new IllegalArgumentException("add: Invalid input for priority (can't be negative)");
		}
		if (key == null) {//checks for invalid key value (null)
			throw new IllegalArgumentException("add: cannot have a null key");
		}
		if (root == null) {//sets the new node as the root
			root = new Node<E>(key,priority);
			return true;
		}
		Node<E> add = new Node<E>(key, priority);//creates the node to be added
		Stack<Node<E>> temp = new Stack<Node<E>>();//sets up a stack of Nodes
		Node<E> current = root;//since the root exists, create a temp variable to store it
		while (current != null) {//iterative process to determine where to add the new node
			int i = current.data.compareTo(key);
			if (i== 0) {//if key already exists, do not add new node and return false;
				return false;
			}
			else {
				if (i < 0) {//enters right subtree
					temp.push(current);//adds current to stack
					if (current.right == null) {
						current.right = add;
						reheap(add,temp);//restores heap properties
						return true;
					}
					else {
						current = current.right;
					}
				}
				else {//enters left subtree
					temp.push(current);
					if (current.left == null) {
						current.left = add;
						reheap(add, temp);//restores heap properties
						return true;
					}
					else {
						current = current.left;
					}
				}
			}
		}
		return false;//if all else fails, node is not added
	}
	
	/**
	 * Helper function which maintains the properties of treap (in terms of priority and keys)
	 * @param current - keeps track of the node being checked
	 * @param stack - sends in the stack from add
	 */
	private void reheap(Node<E> current, Stack<Node<E>> stack) {
		while (stack.isEmpty() == false) {//iterative process to restore heap properties using stack from add
			Node<E> above = stack.pop();//generates parent
			if (above.priority < current.priority) {//compares priorities
				int i = above.data.compareTo(current.data);//determines the necessary rotations
				if (i > 0) {
					current = above.rotateRight();
				}
				else {
					current = above.rotateLeft();
				}
				if (stack.isEmpty() == false) {
					if (stack.peek().left == above) {
						stack.peek().left = current;
					}
					else {
						stack.peek().right = current;
					}
				}
				else {
					this.root = current;
				}
			}
			else {
				break;
			}
		}
	}
	
	/**
	 * Deletes the Node with the corresponding key from the treap, calls helper function below to find and then delete the node
	 * @param key - the key value to be deleted
	 * @return true if successfully deleted, false otherwise
	 */
	public boolean delete (E key) {
		if (find(key) == false) {//checks if node is not in list
			return false;
		}
		if (root.left == null && root.right == null) {//deleting the root if only the root exists
			root = null;
			return true;
		}
		if (root.data.compareTo(key) == 0) {//deleting the root if children exist
			if (root.left != null && root.right != null) {//both children exist, rotate based on priority
				if (root.left.priority > root.right.priority) {
					root.rotateRight();
				}
				else {
					root.rotateLeft();
				}
			}
			if (root.left != null && root.right == null) {
				root = root.rotateRight();
			}
			if (root.right != null && root.left == null) {
				root = root.rotateLeft();
			}
		}
		Stack<Node<E>> stack = new Stack<Node<E>>();//create an empty stack to send to helper function
		return delete(key, root, stack);//calls helper function
	}
	
	/**
	 * Helper function for delete
	 * @param key - the item to be deleted
	 * @param current - the node being checked
	 * @param stack - keeps track of the nodes that have been visited
	 * @return true if the node of key is deleted, false otherwise
	 */
	private boolean delete(E key, Node<E> current, Stack<Node<E>> stack) {
		boolean check = false;//keeps track of whether a node is deleted or not
		int i = key.compareTo(current.data);
		if (i< 0) { //checking through the left child
			stack.push(current);
			return delete(key, current.left, stack);//recursive call using left
		}
		else if (i > 0){//checking through the right child
			stack.push(current);
			return delete(key, current.right, stack);//recursive call using right
		}
		else {//if key is found, delete that node
			while(true) {//create a loop that continues until the node is deleted and properties are restored!
				if (current.left == null && current.right == null) {//node being checked has no children
					break;
				}
				if (current.left != null && current.right != null) {//both children of current exist
					Node<E> ancestor = stack.pop();
					if (current.left.priority > current.right.priority) {//checks priorities
						if (ancestor.right.equals(current)) {
							ancestor.right = current.rotateRight();
							stack.push(ancestor.right);
						}
						else {
							ancestor.left = current.rotateRight();
							stack.push(ancestor.left);
						}
					}
					else {
						if (ancestor.right.equals(current)) {
							ancestor.right = current.rotateLeft();
							stack.push(ancestor.right);
						}
						else {
							ancestor.left = current.rotateLeft();
							stack.push(ancestor.left);
						}
					}
				}
				if (current.left != null && current.right == null) {//only left child exists (right rotations only)
					Node<E> ancestor = stack.pop();
					if (ancestor.right != null && ancestor.right.equals(current)) {
						ancestor.right = current.rotateRight();
						stack.push(ancestor.right);
					}
					else {
						ancestor.left = current.rotateRight();
						stack.push(ancestor.left);
					}
				}
				if (current.right != null && current.left == null) {//only right child exists (left rotations only)
					Node<E> ancestor = stack.pop();
					if (ancestor.right != null && ancestor.right.equals(current)) {
						ancestor.right = current.rotateLeft();
						stack.push(ancestor.right);
					}
					else {
						ancestor.left = current.rotateLeft();
						stack.push(ancestor.left);
					}
				}
			}
			Node<E> parent = stack.peek();
			if (parent.left != null && parent.left.equals(current)) {
				parent.left = null;
				check = true;
				return check;
			}
			else {
				parent.right = null;
				check = true;
				return check;
			}
		}
	}
	
	/**
	 * Finds a node with the given key in the treap rooted at root and returns true if it finds it and false otherwise.
	 * @param root - node to start at (root of treap)
	 * @param key - key to search for
	 * @return true if key is found in a node, false otherwise
	 */
	private boolean find (Node <E> root , E key) {
		if (root == null) {//checks if root is null
			return false;
		}
		int i = root.data.compareTo(key);
		if (i == 0) {//if 0, then the key has been found
			return true;
		}
		return find(root.left, key) || find(root.right, key);//check the left and right roots
	}
	
	/**
	 * Finds a node with the given key in the treap by calling the other find function, starts at root of entire treap
	 * @param key - the key to look for
	 * @return true if key is found in a node, false otherwise
	 */
	public boolean find (E key) {
		return find (root, key);//calls recursive find function
	}
	
	/**
	 * Returns the string version of the Treap by calling a recursive private method toString(current,depth)
	 * @return the string representation of the Treap
	 */
	public String toString () {
		return toString(root, 0);//calls recursive string function
	}
	
	/** Converts a subtree to a string.
	* Performs a preorder traversal.
	* @param current - The local root
	* @param depth - The depth
	*/
	private String toString(Node<E> current, int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<depth;i++) {
			sb.append("-");
		}
		if (current==null) {
			sb.append("null\n");
		} else {
			sb.append(current.toString()+"\n");
			sb.append(toString(current.left, depth+1));
			sb.append(toString(current.right,depth+1));
		}
		return sb.toString();
	}

	
	public static void main(String[] args) {
		/*Treap<Integer> testTree = new Treap <Integer>();
		System.out.println(testTree);
		testTree . add (4 ,19);
		System.out.println(testTree);
		testTree . add (2 ,31);
		testTree . add (6 ,70);
		testTree . add (1 ,84);
		testTree . add (3 ,12);
		testTree . add (5 ,83);
		testTree . add (7 ,26);
		System.out.println(testTree);
		System.out.println(testTree.delete(7));
		System.out.println(testTree);*/

	}
}
