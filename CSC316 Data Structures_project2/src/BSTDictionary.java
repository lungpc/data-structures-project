


import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Implementation of a dictionary using an unsorted linked list. It recursively 
 * builds a binary search tree from the input .txt files using and outputs the tree
 * in inorder traversal. It also calculates how long it takes to find a specific key
 * given different distributions(Zipf, geometric, and uniform) of input. 
 * 
 * @author Philip Lung
 *
 */

public class BSTDictionary {
	/* Root node of tree */
	private Node root;
	/* Counter to keep track of the number of elements in the tree */
	private int counter;
	/* Array for storing all key input values */
	private int[] arrK;
	/* Array for storing all string input values */
	private String[] arrS;
	/* Array for storing key sequence distributions */
	private int[] arrSequence;
	/* Size of the separate array of keys */
	private int index = 0;
	/* Number of total comparisons */
	private int comparisons = 0;
	/* Number of individual comparisons */
	private int numComparisons = 0;
	/* Minimum number of comparisons for a lookUp operation */
	private int minComparisons = Integer.MAX_VALUE;
	/* Maximum number of comparisons for a lookUp operation */
	private int maxComparisons = Integer.MIN_VALUE;
	/* Average number of comparisons for a lookUp operation */
	private int averageComparisons = 0;
	/* String of the inorder traversal of the BST */
	private String report = "";
	/* Keeps track of the number of keys in sequence distribution */ 
	private int keyCounter = 0;
	private final static int KEY_CAPACITY = 100000; 
	
	/**
	 * Nodes contain data and reference to the next object. The data, in this case, 
	 * is a string and the Node next will reference the next element in the list.
	 * 
	 * @author Philip Lung
	 */
	private class Node {
		/* int declaration; the key */
		public int key;
		/* String declaration; the value */
		public String value;
		/* Node object declaration; pointer to left tree */
		public Node leftChild;
		/* Node object declaration; pointer to right tree */
		public Node rightChild;
		
		/**
		 * Constructor with parameters; node consists of a String object
		 * and a reference to the next element.
		 * @param d
		 * @param next
		 */
		public Node(int key, String value) {
			this.key = key;
			this.value = value;
			leftChild = null;
			rightChild = null;
		}
		
		/**
		 * @return true if node has a left child; false, if not.
		 */
		public boolean hasLeftChild() {
			return leftChild != null;
		}
		
		/**
		 * @return true if node has a right child; false, if not.
		 */
		public boolean hasRightChild() {
			return rightChild != null;
		}

		/**
		 * @return the key
		 */
		public int getKey() {
			return key;
		}

		/**
		 * @param key the key to set
		 */
		public void setKey(int key) {
			this.key = key;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return the leftChild
		 */
		public Node getLeftChild() {
			return leftChild;
		}

		/**
		 * @param leftChild the leftChild to set
		 */
		public void setLeftChild(Node leftChild) {
			this.leftChild = leftChild;
		}

		/**
		 * @return the rightChild
		 */
		public Node getRightChild() {
			return rightChild;
		}

		/**
		 * @param rightChild the rightChild to set
		 */
		public void setRightChild(Node rightChild) {
			this.rightChild = rightChild;
		}
	}

	/**
	 * Null constructor, creates an empty tree.
	 */
	public BSTDictionary() {
		root = null;
		counter = 0;
		arrK = new int[KEY_CAPACITY];
		arrS = new String[KEY_CAPACITY];
		
	}
	
	/**
	 * Constructor with scanner parameter
	 * @param s Scanner set to read in a text file. 
	 */
	public BSTDictionary(Scanner s) {
		root = null;
		counter = 0;
		arrK = new int[KEY_CAPACITY];
		arrS = new String[KEY_CAPACITY];
		
		if (s == null) { //no file name was given.
			return;
		}
		
		while (s.hasNextLine()) { //read in each line in file.
			String line = s.nextLine();
			Scanner lineScanner = new Scanner(line); //create scanner for line.
			if (lineScanner.hasNextInt()) {
				int key = lineScanner.nextInt();
				arrK[index] = key; //add every key, even duplicates, to an array
			}
			if (lineScanner.hasNext()) {
				String value = lineScanner.next();
				arrS[index] = value;
			}
			index++;
		}
	}
	
	/**
	 * Tells us the number of nodes in the tree
	 * 
	 * @return size of the tree
	 */
	public int size() {
		return this.counter;
	}
	
	/**
	 * Tells us whether a node is an internal one or not.
	 * @param n Node that is being checked.
	 * @return true if internal; false, if not.
	 */
	public boolean isInternal(Node n) {
		return (n.hasLeftChild() || n.hasRightChild());
	}
	
	/**
	 * Tells us if a node is a leaf or not.
	 * @param n Node being tested.
	 * @return true if leaf; false, it not.
	 */
	public boolean isLeaf(Node n) {
		return !(n.hasLeftChild() && n.hasRightChild());
	}
	
	/**
	 * Traverses through the tree, looking for an entry with a specific key. If a match is found, 
	 * that entry gets returned. 
	 * 
	 * @param key integer that we are looking for
	 * @return a Node object with matching key.
	 * 		   or null if key is not in list or list is empty.
	 */
	public Node lookUp(int key) {
		return lookUp(root, key);
	}
	
	/**
	 * Helper method for lookUp(). Recursively looks for a node with a specific key. 
	 * @param n
	 * @param key
	 * @return node with matching key; null, if not found
	 */
	private Node lookUp(Node n, int key) {
		if (n == null) { //base case; if list is empty or key not found in list.
			return null;
		} else if (key == n.getKey()) { //base case; found the key.
			comparisons += numComparisons + 1;
			return n;
		} else if (key < n.getKey()) { //recursive search in left subtree.
			numComparisons++;
			return lookUp(n.leftChild, key);
		} else { //recursive search in right subtree. 
			numComparisons++;
			return lookUp(n.rightChild, key);
		}
	}
	
	
	/**
	 * Adds a new entry to the tree.
	 * @param key integer data of node
	 * @param value String data of node
	 */
	public void insert(int key, String value) {
		if (lookUp(key) != null) { //if key is already in tree, we do nothing.
			return;
		}
		insert(root, key, value);
		counter++; //increment size of list
	}
	
	/**
	 * Helper method for insert(). Recursively adds a new node to tree. 
	 * @param n root node
	 * @param key integer data
	 * @param value string data
	 * @return newly added node
	 */
	private Node insert(Node n, int key, String value) {
		if (n == null) {
			n = new Node(key, value);
		} else if (key < n.getKey()) {
			n.leftChild = insert(n.leftChild, key, value);
		} else if (key > n.getKey()) {
			n.rightChild = insert(n.rightChild, key, value);
		}
		return n;
	}
	
	/**
	 * Deletes a node from the tree.
	 * @param key entry we want to delete from the tree.
	 */
	public void remove(int key) {
		if (isEmpty()) {
			System.out.println("Tree is empty.");
		} else if (lookUp(key) == null) { //key not in tree.
			return;
		} else {
			remove(root, key); 
			counter--; //decrement size of the tree.
		}
	}
	
	/**
	 * Helper method for remove(). Recursively removes node
	 * @param n Node that is being examined. 
	 * @param key entry we are looking for.
	 */
	private Node remove(Node n, int key) {
		Node current, reference;
		if (n != null && n.getKey() == key) { //found it.
			if (n.leftChild == null || n.rightChild == null) { //at most one child.
				if (n.leftChild == null) {
					reference = n.rightChild;
					return reference;
				} else {
					reference = n.leftChild;
					return reference;
				}
			} else { //has two children -- replace it by its inorder successor 
				int k;
				reference = n.rightChild; //start at the node's right subtree.
				while (reference.hasLeftChild()) { //head down to its leftmost, smallest, node
					reference = reference.leftChild;
				}
				
				k = reference.getKey();
				
				n.setValue(reference.getValue()); //copy the value into its new place in the tree.
				n.setKey(reference.getKey()); //copy the key into its new place in the tree.
				
				remove(n.rightChild, reference.getKey()); //remove the leftmost node on the right subtree.
				
				if (n.rightChild.getKey() == k) {
					n.setRightChild(null);
				} else if (n.leftChild.getKey() == k) {
					n.setLeftChild(null);
				}
			}
		} else if (n != null && n.getKey() > key) { //not the correct key, move down the left subtree.
			current = remove(n.leftChild, key);
			n.setLeftChild(current);
		} else if (n != null) { //not the correct key, move down the right subtree. 
			current = remove(n.rightChild, key);
			n.setRightChild(current);
		}
		return n;
	}
	
	/**
	 * Determines whether the tree is empty or not.
	 * 
	 * @return true if tree is empty, false if not.
	 */
	public boolean isEmpty() {
		return (root == null); 
	}

	/**
	 * Prints a string with values for the total number of key comparisons
	 * every 1000th time lookUp() is called.
	 * @return String of the number of comparisons
	 */
	public String printReport() {
		return this.report;
	}

	/**
	 * Recursively builds a binary search tree from an array of preorder traversal key values. 
	 * 
	 * @param size number of nodes to create.
	 * @param start place in the array of preroder traversal values where this subtree begins.
	 * @return root node of current subtree.
	 */
	public Node buildTree(int size, int start) {
		int count = 0;
		int next = start + 1;
		//create a new node from values in the two arrays.
		Node current = new Node(arrK[start], arrS[start]);
		if (size == 1) { //base case; last node to create for subtree.
			current.setLeftChild(null);
			current.setRightChild(null);
		} else if (size <= 0) { //base case; no nodes to create
			current = null;
		} else { //there is more than one node to create.
			size--;
			//count the number of keys less than the current key 
			//to build the left subtree.
			while (arrK[next] < arrK[start]) {
				count++;
				next++;
				if (next >= this.counter)
					break;
			}
			//handles the last element in array.
			int rightCount;
			if (next >= this.counter) {
				rightCount = count + start;
			} else {
				rightCount = count + start + 1;
			}
			//build left subtree from current node.
			current.setLeftChild(buildTree(count, start + 1));
			//build right subtree from current node.
			current.setRightChild(buildTree((size - count), rightCount));
		}
		return current;
	}
	
	/**
	 * Returns a string of the inorder traversal of the BST dictionary.
	 */
	public String inorder() {
		return inorder(root);
	}
	
	/**
	 * Helper method for inorder(). Recursively performs an inorder traversal on BST.
	 * @param n current Node.
	 * @return String of elements from smallest to largest.
	 */
	private String inorder(Node n) {
		if (n != null) {
			inorder(n.getLeftChild());
			report += (n.getKey() + "    " + n.getValue() + "\n");
			inorder(n.getRightChild());
		}
		return report;
	}
	
	/**
	 * Executes buildTree(), insert(), and remove() operations from input in the arrays.
	 * Builds tree from first sequence of keys.
	 * Adds new keys, except duplicates, from the second sequence of keys.
	 * Removes keys in the tree from the third sequence of keys. 
	 */
	public void performOperations() {
		int numNodes = 0;
		
		//traverse the key array to find the length of keys to create tree with.
		for (int k = 0; k < arrK.length; k++) {
			if (arrK[k] != -1) {
				counter++;
				numNodes++;
			} else {
				break;
			}
		}
		//build the actual tree from the first sequence in the key array.
		root = buildTree(counter, 0);
		
		
		//perform insert() from the keys in the
		//second sequence in the array.
		numNodes += 4; //get past the first end sequence value
		while (arrK[numNodes] != -1) {
			insert(arrK[numNodes], arrS[numNodes]);
			numNodes++;
		}
		
		//perform the remove() method on the tree from the keys
		//in the third sequence of the array.
		numNodes += 4; //get past the next end sequence value.
		while (arrK[numNodes] != -1) {
			remove(arrK[numNodes]);
			numNodes++;
		}

	}
	
	/**
	 * Creates an array of key sequences that will be used to test the find function.
	 */
	public void createArrayDist(Scanner s) {
		int index = 0;
		arrSequence = new int[KEY_CAPACITY];
		while (s.hasNextLine()) { //scan in each line.
			keyCounter++; //increment size of array.
			String line = s.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			while (lineScanner.hasNextInt()) { //line scanner.
				arrSequence[index++] = lineScanner.nextInt();
				//discard the string value
				lineScanner.next();
			}
		}
	}
	
	/**
	 * Executes a series of lookUp actions on the entries in the BST
	 * dictionary based on the sequence of keys in the array we have built. 
	 */
	public String lookUpSequence() {
		String keySequence = "";
		for (int i = 0; i < keyCounter; i++) {
			//reset the individual comparisons in case the key is not found.
			this.numComparisons = 0;
			lookUp(this.arrSequence[i]);
			
			//check number of comparisons to see if it 
			//is the most or the least.
			if (this.numComparisons > this.maxComparisons) {
				this.maxComparisons = this.numComparisons;
			}
			if (this.numComparisons < this.minComparisons) {
				this.minComparisons = this.numComparisons;
			}
			//calculate average number of comparisons.
			averageComparisons = comparisons / (i + 1);
			
			//record results of total comparisons for every 1000th lookUp. 
			if ((i + 1) % 1000 == 0) {
				keySequence += this.maxComparisons + "\n";
			}
		}
		return keySequence;
	}
}
