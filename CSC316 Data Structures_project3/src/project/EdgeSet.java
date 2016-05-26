package project;


public class EdgeSet {

	private Node head;
	private Node dummy;
	private int size;
	
	private class Node {
		/* int declaration; the data */
		public Edge data;
		/* Node object declaration; the link */
		public Node next;
		
		/**
		 * Constructor with parameters; node consists of a String object
		 * and a reference to the next element.
		 * @param d
		 * @param next
		 */
		public Node(Edge entry, Node next) {
			data = entry;
			this.next = next;
		}
	}
	
	/**
	 * Null constructor.
	 */
	public EdgeSet() {
		dummy = new Node(null, null);
		head = dummy;
		size = 0;
	}
	
	/**
	 * Inserts an edge to the list in lexicographic order based on vertex.
	 */
	public void insert(Edge e) {
		int v1 = e.getVertex1();
		
		//assign reference pointers.
		Node current = head.next;
		Node previous = head;
		//find the place in the list to insert new node.
		while (current != null && v1 > current.data.getVertex1()) { //sort by first vertex.
			previous = current;
			current = current.next;
			}
		while (current != null && 
				e.getVertex1() == current.data.getVertex1() &&
				e.getVertex2() > current.data.getVertex2()) { //then sort by second vertex.
			previous = current;
			current = current.next;
			}

		//create new node and insert.
		previous.next = new Node(e, current);
		size++;
	}
	
	/**
	 * Prints out the edges in the set.
	 */
	public String printSet() {
		String s = "";
		Node current = head.next;
		while (current != null) {
			s += String.format(current.data.toString() + "\n");
			current = current.next;
		}
		return s;
	}
	
	/**
	 * Prints out the vertices adjacent to each vertex in lexicographic order.
	 * @param x vertex we use to determine its neighbors.
	 * @return String of the vertices.
	 */
	public String printVertices(int x) {
		String s = "";
		Node current = head.next;
		while (current != null) {
			if (current.data.getVertex1() == x) { //we don't want to print out the reference vertex
				s += String.format("%4d ", current.data.getVertex2());
			} else if (current.data.getVertex2() == x) {
				s += String.format("%4d ", current.data.getVertex1());
			}
			current = current.next;
		}
		return s;	
	}

	public boolean hasNext() {
		return head.next != null;
	}
}
