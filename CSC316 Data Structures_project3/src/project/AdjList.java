package project;

/**
 * An array of linked list structures that models the adjacency list. 
 * Represents the entire connected graph. 
 * @author Philip Lung
 *
 */
public class AdjList {

	private EdgeSet[] aList;
	
	/**
	 * Constructor with parameters.
	 * @param num number of vertices in the adjacency list.
	 */
	public AdjList(int num) {
		aList = new EdgeSet[num];
		for (int i = 0; i < num; i++) {
			aList[i] = new EdgeSet();
		}
	}

	public void insert(Edge e) {
		int v1 = e.getVertex1();
		int v2 = e.getVertex2();
		//create a duplicate edge for list
		Edge e2 = new Edge(v1, v2, e.getWeight());
		
		aList[v1].insert(e);
		aList[v2].insert(e2);
	}
	
	/**
	 * Prints out the adjacency list in order of increasing vertices
	 * and all of their adjacent vertices.
	 */
	public String printList() {
		String s = "";
		for (int i = 0; i < aList.length; i++) {
			s += (aList[i].printVertices(i) + "\n");
		}
		return s;
	}
}

