package project;

/**
 * Represents an edge in a minimum spanning tree graph. Contains information
 * about its priority(weight), the vertices it connects, and the next record.
 * @author Philip Lung
 *
 */
public class Edge {

	/* vertex at one end of the edge */
	private int vertex1;
	/* vertex at the other end of the edge */
	private int vertex2;
	/* priority of the edge itself */
	private double weight;
	/* link to the next EdgeRecord */
	private Edge next;
	
	/**
	 * Constructor with parameters.
	 * @param v1 one endpoint of the edge.
	 * @param v2 the other endpoint of the edge.
	 * @param weight weight of the edge. 
	 */
	public Edge(int v1, int v2, double weight) {
		vertex1 = v1;
		vertex2 = v2;
		this.weight = weight;
		this.next = null;
	}

	/**
	 * @return the vertex1
	 */
	public int getVertex1() {
		return vertex1;
	}

	/**
	 * @return the vertex2
	 */
	public int getVertex2() {
		return vertex2;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets pointer to another node.
	 */
	public void setNext(Edge next) {
		this.next = next;
	}
	
	/**
	 * Returns the two vertices of the edge, in increasing order.
	 */
	public String toString() {
		if (vertex2 > vertex1) {
			return String.format("%4d %4d", vertex1, vertex2); 
		} else {
			return String.format("%4d %4d", vertex2, vertex1);
		}
	}
}
