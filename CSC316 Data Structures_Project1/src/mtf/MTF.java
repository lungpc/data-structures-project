package mtf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;



/**
 * This program scans a .txt file and creates nodes for each unique key-value pair. 
 * The file data consists of an integer key and a String value. Nodes are stored in a dictionary 
 * object MTFList, which is implemented with a linked list. 
 * Program then performs a find() operation, which looks for specific keys in the 
 * dictionary and, if found, executes a move-to-front operation on that node. 
 * The move-to-front strategy moves that node to the front of the list. After all find()
 * operations are completed, the final MTFList is printed to a new .txt output file. 
 * 
 * @author Philip Lung
 *
 */
public class MTF {
	
	/* Dictionary that holds each key-value pair */
	private static MTFList dList;

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
		while (!f.exists()) { //only accepts files that exist.
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
		System.out.println("Output file name?");
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
	 * Executes program. Prompts user for input and output file names. 
	 * And performs all the find operations of the MTFList. 
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		//get name of input file.
		Scanner input = getInput(console);
		
		//create dictionary with input file.
		//And run find() sequences.
		dList = new MTFList(input);
		dList.findSequence();
		
		//create output file.
		File output = getOutput(console); 
		//print results to it.
		PrintStream outputFile = new PrintStream(output);
		printToOutput(dList.toString(), outputFile); 
	}
}
