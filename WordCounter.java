import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WordCounter {

	
	static myHashTable table = new myHashTable(40); //default length of 10
	
	
	//main method for WordCounter
	//handles/directs everything
	//gives instructions on building the table, printing it, and giving the output
	//the only callable method
	public static String wordCount(String input_file, String output_file){
		
		File inFile = new File(input_file);
		File outFile = new File(output_file);
		String status="";
		
		status=buildHashTable(inFile); //creates table
		if(!status.equals("Success")) return status;//returns a failure
		
		status=createOutputFile(outFile);//outputs the table to a file
		if(!status.equals("Success")) return status;//returns a failure
		
		return "Okay; "+table.getStatistics(); 
		
	}
	
	
	//creates the table based on the input file
	//runs insert on each word delimited by non-alphabetical characters
	private static String buildHashTable(File inputFile){
		String line;
		
		try {
			
			Scanner fileScanner = new Scanner(inputFile);
			while(fileScanner.hasNext()){ //while the file has text
				
				line = fileScanner.nextLine(); //gets the line
				
				// runs insert on each word
				//word being defined as characters separated
				//by non alphabetical characters
				for(String word:line.split("[^a-zA-Z]")){
					
					if(!word.equals(""))//if the word is not empty
						table.insert(word.toLowerCase(),1);
					
				}
			}
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "Input File error";
		} 
		
		return "Success";
		
	}
	
	
	//creates the output file based on hash table toString
	private static String createOutputFile(File outputFile){
		
		try {
			
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
			
			fileWriter.write(table.toString());
			
			fileWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			return "Output File error";
		}
		
		return "Success";
	}
	
}
