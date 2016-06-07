

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;



/**
 * Prompts user for input and output files to create and 
 * print out the initial binary search tree. 
 * 
 * @author Philip Lung
 *
 */
public class BST {
	
	/* Dictionary that holds each key-value pair */
	private static BSTDictionary bst;

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
	 * Main method.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		//get name of input file.
		Scanner input = getInput(console);
		
		//create dictionary with input file.
		//And run buildTree, insert, and remove operations.
		bst = new BSTDictionary(input);
		bst.performOperations();
		
		//create output file.
		File output = getOutput(console); 
		//print results to it.
		PrintStream outputFile = new PrintStream(output);
		printToOutput(bst.inorder(), outputFile); 
		
		//prompt for second input file of key sequence distributions.
		Scanner secondInput = getInput(console);
		//create an array from the keys in the second input file.
		//perform lookUp action on each key in array.
		bst.createArrayDist(secondInput);
		bst.lookUpSequence();
		
		//create second output file and print results of the 
		//cost of lookUp operation on key distribution to it. 
		File secondOutput = getOutput(console);
		PrintStream secondOutputFile = new PrintStream(secondOutput);
		printToOutput(bst.lookUpSequence(), secondOutputFile);
	}
}
