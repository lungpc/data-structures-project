package transpose;

import java.util.Scanner;


/**
 * Implementation of an unsorted linked list with a transpose node strategy. 
 * It models a Dictionary ADT.
 * 
 * @author Philip Lung
 *
 */
public class TransposeList {

	/* Node object declaration, head pointer of linked list */
	private Node head;
	/* Node object declaration, tail pointer */
	private Node tail;
	/* Counter to keep track of the number of elements in the list */
	private int counter;
	/* Array for storing all key values */
	private int[] arrKey;
	/* Size constant for array length */
	private final static int CAPACITY = 100000;
	/* Variable for keeping track of position in linked list */
	private int position;
	/* Size of the separate array of key sequence */
	private int index = 0;
	/* Number of comparisons */
	private int comparisons;
	/* String of number key comparisons every 1000th key */
	private String report = "";
	
	/**
	 * Nodes contain data a reference to the next object. The data, in this case, is a string
	 * and the Node next will reference the next element in the list.
	 * 
	 * @author Philip Lung
	 */
	private class Node {
		/* object declaration; the data */
		public KeyValue entry;
		/* Node object declaration; the link */
		public Node next;
		
		/**
		 * Constructor with parameters; node consists of a String object
		 * and a reference to the next element.
		 * @param d
		 * @param next
		 */
		public Node(KeyValue entry, Node next) {
			this.entry = entry;
			this.next = next;
		}
	}

	/**
	 * Null constructor, creates an empty list with an empty dummy node and a counter.
	 */
	public TransposeList() {
		Node dummy = new Node(null, null);
		head = dummy;
		tail = dummy.next;
		counter = 0;
	}
	
	/**
	 * Constructor with scanner parameter
	 * @param s Scanner set to read in a text file. 
	 */
	public TransposeList(Scanner s) {
		Node dummy = new Node(null, null);
		head = dummy;
		tail = dummy;
		counter = 0;
		arrKey = new int[CAPACITY];
		
		if (s == null) { // no file name was given
			return;
		}
		
		KeyValue kv;
		
		while (s.hasNextLine()) {
			String line = s.nextLine();
			Scanner lineScanner = new Scanner(line);
			int key = lineScanner.nextInt();
			arrKey[index++] = key; //add every key, even duplicates, to an array
			String value = lineScanner.next();
			if (this.lookUp(key) == null) { //traverse list and add only unique KeyValue pairs
				kv = new KeyValue(key, value);
				this.addToEnd(kv);
			} else {
				continue;
			}
		}
	}
	
	/**
	 * Tells us the number of nodes in the list
	 * 
	 * @return size of the list
	 */
	public int size() {
		return this.counter;
	}
	
	/**
	 * Traverses through the list, looking for an entry with a specific key. If a match is found, 
	 * that entry gets returned. 
	 * 
	 * @param head The start, or first entry, in the list
	 * @param key integer that we are looking for
	 * @return a KeyValue object with matching key.
	 * 		   or null if key is not in list or list is empty.
	 */
	public Node lookUp(int key) {
		Node reference = head.next;
		//If the list is not empty and the key doesn't match what we want, 
		//move on to the next entry.
		while (reference != null && reference.entry.getKey() != key) {
			reference = reference.next;
			comparisons++; //keeps count of the number of comparisons before match is found.
		}
		comparisons++; //this one is for when the matching key is found.
		return reference;
	}
	
	
	/**
	 * Adds a new entry to the end of the list.
	 * @param kv new KeyValue object that will be added
	 */
	public void addToEnd(KeyValue kv) {
		//creates a new node at end of list and moves tail to the new node.
		Node reference = tail;
		reference.next = new Node(kv, null);
		tail = reference.next;
		counter++; //increment size of list
	}
	
	/**
	 * Adds a new entry to a certain point in the list.
	 * @param n Node that is being inserted into list.
	 * @param point in the list to insert at.
	 */
	public void addBefore(Node n, int point) {
		Node reference = head;
		int insertion = point - 1; //point of new node insertion.
		
		//traverse list until we get to insertion point.
		while (reference.next != null && insertion > 0) {
			reference = reference.next;
			insertion--;
		}
		n.next = reference.next;
		reference.next = n;
		counter++; //increment list size
	}
	
	/**
	 * Method for removing node from a given position in the list.
	 * @param reference Node that is being removed.
	 */
	public Node remove(Node n, int key) {
		Node reference = null;
		this.position = 0;
		
		//If the list is not empty and the key doesn't match what we want, 
		//move on to the next entry.
		while (n.next != null && n.next.entry.getKey() != key) {
			n = n.next;
			this.position++;
		}
		//Remove the node with the specified key
		reference = n.next;
		n.next = n.next.next;         
		return reference;
	}
	
	/**
	 * Determines whether the list is empty or not.
	 * 
	 * @return true if list is empty, false if not.
	 */
	public boolean isEmpty() {
		return (head.next == null); 
	}
	
	/**
	 * String representation of the dictionary.
	 */
	public String toString() {
		String list = "";
		Node reference = head.next;
		while (reference != null) {
			list = list + reference.entry.toString() + "\n";
			reference = reference.next;
		}
		return list;
	}
	
	/**
	 * Finds an entry with a particular key and performs 
	 * a transpose operation on that node.
	 * @param key Key integer to search for 
	 */
	public void find(int key) {
		//Traverse list and find the key, if it exists.
		Node current = this.lookUp(key);
		if (current == null) { //list is empty or key does not exist in the list.
			System.out.println("Item could not be found.");
			return;
		} else { //key is removed and moved up one spot in the list.
			current = this.remove(head, key);
			this.addBefore(current, this.position);
		}
	}
	
	/**
	 * Executes a series of transpose actions on the entries in the linked 
	 * list dictionary based on the sequence of keys in the array we have built. 
	 */
	public void findSequence() {
		for (int i = 0; i < index; i++) {
			find(this.arrKey[i]);
			if ((i + 1) % 1000 == 0) { //add every 1000th check to the report
				this.report += this.comparisons + "\n";
			}
		}
	}
	
	/**
	 * Prints a string with values for the total number of key comparisons
	 * every 1000th time lookUp() is called.
	 * @return String of the number of comparisons
	 */
	public String printReport() {
		return this.report;
	}
}
