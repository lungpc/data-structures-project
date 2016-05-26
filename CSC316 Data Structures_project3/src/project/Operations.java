package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Program creates a minimum spanning tree using Kruskal's algorithm. It takes input from
 * a .txt file to create Edge objects from. Then it creates a .txt output file with the
 * tree structure. 
 * @author Philip Lung
 *
 */

public class Operations {
	/* Kruskal's algorithm for a minimum spanning tree */
	private static KruskalMST mst;
	private static EdgeSet es;
	private static AdjList al;

	/**
	 * Takes user input from console.  
	 * 
	 * @param console Scanner that reads in user input.
	 * @return a Scanner that reads input from an existing file.
	 * @throws FileNotFoundException
	 */
	public static Scanner getInput(Scanner console) throws FileNotFoundException {
		System.out.println("input file name?");
		File f = new File(console.nextLine());
		while (!f.exists()) {
			System.out.println("File not found. Try again.");
			System.out.print("input file name? ");
			f = new File(console.nextLine());
		}
		return new Scanner(f);
	}
	
	/**
	 * Takes input from user to create output file. 
	 * 
	 * @param console Scanner that takes user input
	 * @return a new File object
	 * @throws FileNotFoundException 
	 */
	public static File getOutput(Scanner console) throws FileNotFoundException {
		System.out.println("output file name?");
		File f = new File(console.nextLine());
		return f;
	}
	
	/**
	 * Prints text to an output file.
	 * 
	 * @param text a String that will be printed to a file
	 * @param output file that is the output
	 */
	public static void printToOutput(String text, PrintStream output) {
		Scanner data = new Scanner(text);
		while (data.hasNextLine()) { //read in every line in the text
			output.println(data.nextLine());
		}
	}

	/**
	 * Main method. Creates the necessary structures and runs Kruskal's algorithm. 
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		//get name of input file.
		Scanner input = getInput(console);
		
		//create minimum spanning tree with input file.
		//And run mst operations using Kruskal's algorithm.
		mst = new KruskalMST(input);
		//print the Heap of edges after all insertions
		//and before performing Kruskal's algorithm.
		String heap = mst.getHeap();

		//Run Kruskal's to find a minimum spanning tree.
		es = mst.performKruskals();
		
		//get adjacency list.
		al = mst.getAdjList();

		
		//create output file.
		File output = getOutput(console); 
		//print results to it.
		PrintStream outputFile = new PrintStream(output);
		printToOutput(heap + es.printSet() + al.printList(), outputFile); 
		

	}
}