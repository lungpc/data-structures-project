package BinarySearch;

import java.util.Scanner;

/**
 * A dictionary implemented with an array-list using a binary search algorithm
 * to look up and to add new elements. 
 * @author Philip Lung
 *
 */
public class DictionaryArray {

	/* Array of KeyValue objects */
	private KeyValue[] dArray;
	/* Array to store keys of all input */
	private int[] arrKey;
	/* Constant for the maximum number of distinct KeyValues */
	private static final int CAPACITY = 2000;
	/* Constant for the maximum number of keys in input sequence */
	private static final int KEY_CAPACITY = 100000;
	/* Size of the array list */
	private int size = 0;
	/* position in the array */
	private int position = 0;
	/* Number of comparisons */
	private int comparisons;
	/* String of number key comparisons every 1000th key */
	private String report = "";
	/* Total number of keys in input */
	private int index = 0;
	
	/**
	 * Null constructor
	 */
	public DictionaryArray() {
		dArray = new KeyValue[CAPACITY];
		arrKey = new int[KEY_CAPACITY];
		int index = 0;
	}
	
	/**
	 * Constructor with parameter
	 * @param input Reads input from a file
	 */
	public DictionaryArray(Scanner input) {
		//initialize arrays.
		dArray = new KeyValue[CAPACITY];
		arrKey = new int[KEY_CAPACITY];
		
		if (input == null) { // no file name was given.
			return;
		}
		
		KeyValue kv;
		
		while (input.hasNextLine()) {
			String line = input.nextLine();
			Scanner lineScanner = new Scanner(line);
			int key = lineScanner.nextInt();
			arrKey[index++] = key; //add every key, even duplicates, to a separate array.
			String value = lineScanner.next();
			if (this.lookUp(key, this.dArray, 0, size - 1) == null) { //traverse list and add only unique KeyValue objects.
				kv = new KeyValue(key, value);
				this.enqueue(kv);
			} else {
				continue;
			}
		}
	}
	
	/**
	 * Finds element in the array that has the same key using binary search.
	 *  
	 * @param key integer key that we want to find.
	 * @param kv Array that will be searched through.
	 * @param low starting index in search algorithm.
	 * @param last index in search algorithm.
	 * @return KeyValue object that has a matching key; null if no match was found
	 */
	public KeyValue lookUp(int key, KeyValue[] kv, int low, int high) {
		if (low > high) { //no match was found
			position = low;
			return null;
		} else {
			int mid = (low + high)/2; //starting point at the midpoint of the array
			if (kv[mid] == null) {
				return null;
			} else if (kv[mid].getKey() == key) { //matching key is found
				comparisons++;// for report
				return kv[mid];
			} else if (kv[mid].getKey() < key) { //key is greater than middle element
				comparisons++; //for report.
				return lookUp(key, kv, mid + 1, high); //so, search upper half of array
			} else {
				comparisons++;
				return lookUp(key, kv, low, mid - 1); //otherwise, search lower half 
			}
		}
	}
	
	/**
	 * Determines if array is empty or not
	 * @return true is empty, false if not.
	 */
	public boolean isEmpty() {
		return (dArray[0] == null);
	}
	
	/**
	 * Returns the size of the array
	 * @return number of elements in array
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds new KeyValue object to the correct position in array. 
	 * 
	 * @param kv KeyValue object to be added to the array.
	 */
	public void enqueue(KeyValue kv) {
		int high = CAPACITY - 1;
		if (size == high) { //throw exception if attempting to add to a full array.
			throw new IllegalStateException();
		}
		
		//Perform lookUp to locate item.
		KeyValue current = this.lookUp(kv.getKey(), this.dArray, 0, size - 1); 
		
		if (current != null) { //item already exists, take no action.
			return;
		} else { //At point of insertion, shift array elements down one.
			for (int i = size; i > position; i--) {
				dArray[i] = dArray[i - 1];
			}
			//insert new element
			dArray[position] = kv;
			size++;
		}
	}
	
	/**
	 * Sequence of find operations.
	 */
	public void findSequence() {
		for (int i = 0; i < index; i++) {
			lookUp(this.arrKey[i], this.dArray, 0, size - 1);
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
	
	/**
	 * String representation of the dictionary entries.
	 */
	public String toString() {
		String list = "";
		for (int i = 0; i < (size); i++) {
			list += dArray[i].toString() + "\n";
		}
		return list;
	}
}
