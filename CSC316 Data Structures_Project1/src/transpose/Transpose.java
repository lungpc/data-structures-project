package transpose;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Program scans input from a .txt file that has an integer key with a String value. It
 * creates a linked list of all the unique key-value pairings, and a separate array of every 
 * (including duplicates) key in the file. It then uses that array of keys to perform a 
 * sequence of lookUp() actions on the list of key-value pairings. If found, that node is 
 * moved up one spot in the list.
 * @author Philip Lung
 *
 */
public class Transpose {

	/* Dictionary that holds each key-value pair */
	private static TransposeList tList;
	
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
	 * Main method.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		//get name of input file.
		Scanner input = getInput(console);
		
		//creates dictionary with input file.
		//And run find() sequences.
		tList = new TransposeList(input);
		tList.findSequence();
		
		//creates output file.
		File output = getOutput(console);
		//print results to it.
		PrintStream outputFile = new PrintStream(output);
		printToOutput(tList.toString(), outputFile);
		
	}
}