package project;

import java.util.NoSuchElementException;

/**
 * Represents all the vertices of the graph using a Heap structure with
 * an Array implementation. 
 *  
 * @author Philip Lung
 *
 */

public class Heap {
	/* An array of edges which represent a heap structure */
	private Edge[] edges;
	/* Constant for the maximum number of edges in the project */
	private static final int MAX_EDGES = 5000;
	/* Number of edges in the array */
	private int size;
	
	/**
	 * Null constructor.
	 */
	public Heap() {
		size = 0;
		edges = new Edge[MAX_EDGES];
	}
	
	/**
	 * Inserts an EdgeRecord into the heap
	 * @param v
	 */
	public void insert(Edge v) {
		if (isFull()) { //throw exception if array is full.
			throw new NoSuchElementException("Array is full.");
		} 
		
		insert(v, edges);
	}
	
	/**
	 * Inserts a new entry into the array; a new 
	 * leaf into the heap.
	 * 
	 * @param v second element in the key-value pair.
	 * @param arr the array to insert into.
	 */
	private void insert(Edge v, Edge[] arr) {
		arr[size++] = v;
		upHeap(arr, size - 1);
	}
	
	/**
	 * Deletes entry with the smallest key.
	 * @return item at position 0 in the array.
	 */
	public Edge deleteMin() {
		Edge er = edges[0];
		delete(edges);
		return er;
	}
	
	/**
	 * Deletes the entry with the smallest key, decrements size
	 * and calls a downHeap operation.
	 * 
	 * @return entry that was deleted.
	 */
	private Edge delete(Edge[] arr) {
		Edge x = arr[0];
		size--;
		swap(size, 0);
		downHeap(arr, 0);
		return x;
	}
	
	/**
	 * Recursive method for checking and swapping the
	 * parent and child nodes in the heap.
	 * 
	 * @param arr the array to traverse.
	 * @param m integer that represents the parent node.
	 */
	private void downHeap(Edge[] arr, int m) {
		//int i is m's smallest child, if one exists.
		int i = 0;
		
		if (((2 * m) + 2) < size) { //both children exist.
			if (arr[(2 * m) + 2].getWeight() <= arr[(2 * m) + 1].getWeight()) {
				i = (2 * m) + 2;
			} else {
				i = (2 * m) + 1;
			}
		} else if ((2 * m) + 1 < size) { //only left child exists.
			i = (2 * m) + 1;
		}
		
		//at this stage, if i = 0, then the node has no children.
		if (i > 0 && arr[m].getWeight() > arr[i].getWeight()) {
			swap(m, i);
			downHeap(arr, i);
		}
	}

	/**
	 * Recursive method for checking and swapping the keys
	 * of parent/child nodes in the heap.
	 * 
	 * @param er array that we are performing method on.
	 * @param position position in the heap of the new element.
	 */
	private void upHeap(Edge[] er, int position) {
		if (position > 0) { //base case; don't move up the root.
			//if the parent has a greater weight than the child,
			//then we swap them and recursively call upHeap.
			if ((er[(position - 1) / 2].getWeight()) > (er[position].getWeight())) {
				swap((position - 1) / 2, position);
				upHeap(er, (position - 1) / 2);
			}
		}
	}
	
	/**
	 * Swaps the elements of two nodes in the heap.
	 * 
	 * @param parent parent node
	 * @param child child node
	 */
	private void swap(int parent, int child) {
		//temporary pointer for child node.
		Edge temp = edges[child];
		
		//swap parent node with child node.
		edges[child] = edges[parent];
		edges[parent] = temp;
	}

	/**
	 * Returns the entry with the smallest key.
	 */
	public double findMin() {
		if (isEmpty()) {
			throw new NoSuchElementException("Heap is empty.");
		}
		return edges[0].getWeight();
	}
	
	/**
	 * Returns true if the heap array is full; false otherwise. 
	 */
	public boolean isFull() {
		return (this.size == this.edges.length);
	}
	
	/**
	 * Returns true if array is empty; false if not.
	 */
	public boolean isEmpty() {
		return (size() <= 0);
	}
	
	/**
	 * Returns the number of entries in the heap, or size of the array.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * @return String of vertices that represent all the edges in the heap.
	 */
	public String printArr() {
		String s = "";
		for (int i = 0; i < size; i++) {
			if (edges[i].getVertex2() > edges[i].getVertex1()) {
				s += String.format("%4d %4d\n", edges[i].getVertex1(), edges[i].getVertex2()); 
			} else {
				s += String.format("%4d %4d\n", edges[i].getVertex2(), edges[i].getVertex1());
			}
		}
		return s;
	}
	
	public void printArr2() {
		
		for (int i = 0; i < size; i++) {
			if (edges[i].getVertex2() > edges[i].getVertex1()) {
				System.out.printf("%4d %4d\n", edges[i].getVertex1(), edges[i].getVertex2()); 
			} else {
				System.out.printf("%4d %4d\n", edges[i].getVertex2(), edges[i].getVertex1());
			}
		}
	}
}
