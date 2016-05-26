package project;

import java.util.Arrays;

/**
 * Array implementation of an Up-Tree. Keeps track of the vertices in
 * our minimum spanning tree and how they are connected to each other.
 * 
 * @author Philip Lung
 *
 */
public class UpTree {
	private int[] vertex;
	private static final int MAX_VERTICES = 1000;

	/**
	 * Null constructor.
	 */
	public UpTree() {
		vertex = new int[MAX_VERTICES];
	}
	
	/**
	 * Makes a new set consisting of a single element.
	 * 
	 * @param x element in the set.
	 * @return new set consisting of just x.
	 */
	public int makeSet(int x) {
		vertex[x] = -1;
		return x;
	}
	
	/**
	 * Finds the set that x is an element of. 
	 * @param x element that we are searching for
	 * @return root node, or set, that x is a part of.
	 */
	public int find(int x) {
		int index = x;
		while (vertex[index] >= 0) {
			index = vertex[index];
		}
		return index;
	}
	
	/**
	 * Combines 2 disjoint sets into 1 connected set.
	 * 
	 * @param x root of one set.
	 * @param y root of another set.
	 * @return a new set that is the union of x and y.
	 */
	public int union(int x, int y) {
		if (vertex[x] < vertex[y]) { //take the set with higher count as root of new set. 
			vertex[x] = vertex[x] + vertex[y]; //update count
			vertex[y] = x; //join sets by setting pointer to the bigger set
			return vertex[x];
		} else { 
			vertex[y] = vertex[x] + vertex[y];
			vertex[x] = y;
			return vertex[y];
		}
	}
	
	/**
	 * prints out the vertices in the array.
	 */
	public void printArr() {
		for (int i = 0; i < MAX_VERTICES; i++) {
			System.out.print(vertex[i] + " ");
		}
	}

}
