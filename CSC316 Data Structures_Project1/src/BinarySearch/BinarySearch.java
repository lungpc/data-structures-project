package BinarySearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This program scans input from a .txt file that has an integer key with a String value. It
 * creates an array list of all the unique key-value pairings, and another separate array of every 
 * (including duplicates) key in the file. It then goes through a binary search operation where it 
 * uses the keys in the array to look for the key-value object in the array list. 
 * @author Philip Lung
 *
 */

public class BinarySearch {

	/* Dictionary that holds each key-value pair */
	private static DictionaryArray dArr;
	
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
		
		//create dictionary with input file.
		//And run find() sequences.
		dArr = new DictionaryArray(input);
		dArr.findSequence();
		
		//create output file.
		File output = getOutput(console);
		//print results to it.
		PrintStream outputFile = new PrintStream(output);
		printToOutput(dArr.toString(), outputFile);
	}
}
