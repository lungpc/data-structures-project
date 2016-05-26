package project;

import java.util.Scanner;

public class KruskalMST {

	/* UpTree structure that holds the vertices */
	private UpTree vertices;
	/* Heap structure that holds our edges */
	private Heap edges;
	/* number of connected components in the minimum spanning tree */
	private int components;
	/* Adjacency list that represents the graph */
	private AdjList graph;
	/* Number of vertices in the graph */
	private int numVertices = 0;
	/* Set of edges in the MST */
	private EdgeSet edgeSet;
	
	/**
	 * Constructor that reads input and initializes the
	 * edge and vertex structures.
	 *  
	 * @param s file Scanner
	 */
	public KruskalMST(Scanner s) {
		vertices = new UpTree();
		edges = new Heap();
		int vertex1 = 0;
		int vertex2 = 0;
		double weight = 0.0;
		Edge edge;
		
		if (s == null) { //no file was given.
			return;
		}
		
		while (s.hasNextLine()) { //read in each line in file.
			String line = s.nextLine();
			Scanner lineScanner = new Scanner(line); //create scanner for line.
			while (lineScanner.hasNextInt()) {
				vertex1 = lineScanner.nextInt();
				if (vertex1 == -1) { //end of the input.
					components = this.numVertices + 1;
					//create adjacency list.
					graph = new AdjList(numVertices + 1);
					break;
				}
				vertex2 = lineScanner.nextInt();
				
				//Determine the number of vertices in the graph.
				numVertices = maxVertex(numVertices, vertex1, vertex2);
			}
			if (lineScanner.hasNextDouble()) {
				weight = lineScanner.nextDouble();
			}
			
			//create an edge record.
			if (vertex2 > vertex1) {
				edge = new Edge(vertex1, vertex2, weight);
			} else {
				edge = new Edge(vertex2, vertex1, weight);
			}
			
			//insert into heap.
			if (edge.getVertex1() != -1) 
				edges.insert(edge);
		}
	}
			
	/**
	 * Helper method that returns the maximum of 3 integers.
	 * 
	 * @param x number of current vertices
	 * @param y one endpoint in an edge
	 * @param z other endpoint in the edge
	 * @return maximum number
	 */
	private int maxVertex(int x, int y, int z) {
		int max = Math.max(x, y);
		return Math.max(max, z);
	}

	/**
	 * Creates a new vertex set and inserts it into the UpTree.
	 * 
	 * @param num number of new sets of vertices to make.
	 */
	public void makeNewVertex(int num) {
		for (int i = 0; i < num; i++) {
			vertices.makeSet(i);
		}
	}
	
	/**
	 * Getter method that returns the number of connected components
	 * in the minimum spanning tree.
	 */
	public int getComponents() {
		return this.components;
	}
	
	/**
	 * Create all the connected components and create a minimum spanning tree
	 * using a greedy algorithm to connect the edges with the least weight. 
	 * 
	 * @return set of edges in the MST. 
	 */
	public EdgeSet performKruskals() {
		edgeSet = new EdgeSet();
		Edge minEdge;
		
		//vertices are connected components in themselves.
		//create a new set for each vertex in the graph.
		makeNewVertex(this.components);
		
		while (components > 1) { //process edges in order of increasing weight.
			//find smallest edge.
			minEdge = edges.deleteMin();
			
			//place into adjacency list.
			graph.insert(minEdge);
			
			//find that edge's endpoints.
			int v1 = vertices.find(minEdge.getVertex1());
			int v2 = vertices.find(minEdge.getVertex2());
			
			if (v1 != v2) { //if not equal, then components are not connected.
				//merge the two components into one.
				vertices.union(v1, v2);
				edgeSet.insert(minEdge);
				components--; //decrement number of connected components
			}
		}
		return edgeSet;
	}
	
	/**
	 * @return AdjList that represents the adjacency list of the vertices.
	 */
	public AdjList getAdjList() {
		return graph;
	}
	
	/**
	 * @return String of vertices that represent all the edges in the heap.
	 */
	public String getHeap() {
		return edges.printArr();
	}
	
	public void printHeap() {
		edges.printArr2();
	}
	
}
