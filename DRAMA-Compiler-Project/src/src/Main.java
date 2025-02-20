package src;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import exceptions.ErrorHandler;
import exceptions.ErrorType;
import model.Program;
import model.URL;
import parser.CParser;

public class Main {
	
	private static CParser parser = new CParser();
	private static ErrorHandler errorHandler = new ErrorHandler();
	

	public static void main(String[] args) {
		try {
			handleArgs(args[0], args[1]);
		}
		catch (IndexOutOfBoundsException exc) {
			handleNoArgs();
		}
	}
	
	
	public static void handleArgs(String input, String output) {
		System.out.println("Handling Command Line Args");
	}
	
	
	public static void handleNoArgs() {
		System.out.println("Handling No Command Line Args");
		URL input = null, output = null;
		boolean inputSet = false, outputSet = false;
		Scanner scanner = new Scanner(System.in);
		
		while (!inputSet) {
			System.out.format("Enter target input file: ");
			input = new URL(scanner.nextLine());
			inputSet = true;
			try {
				FileReader testReader = new FileReader(input.getTarget());
				testReader.close();
				System.out.format("Reading: %s ...%n", input.getTarget());
			} catch (IOException e) {
				inputSet = false;
				errorHandler.handleIOError(ErrorType.INPUTIO);
			}
		}
		
		while (!outputSet) {
			System.out.format("Enter target output file: ");
			output = new URL(scanner.nextLine());
			outputSet = true;
			try {
			    PrintWriter writer = new PrintWriter(output.getTarget(), "UTF-8");
			    writer.println("| File generated by DRAMA-Compiler");
			    writer.close();
			    System.out.format("Writing: %s ...%n", output.getTarget());
			} catch (IOException e) {
				outputSet = false;
				errorHandler.handleIOError(ErrorType.OUTPUTIO);
			}
		}
		
		scanner.close();
		Program program;
		try {
			program = parser.parse(input);
			try {
				program.compile(output);
			} catch(RuntimeException e){
				System.out.println("Not Implemented.");
			}
		} catch (IOException e) {
			errorHandler.handleIOError(ErrorType.UNKOWNIO);
		} catch (RuntimeException e) {
			System.out.println("Not Implemented.");
		}		
	}
	
}
