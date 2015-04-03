package HashTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WordCounter {

	static myHashTable table = new myHashTable();
	
	
	public WordCounter(){
		
	}
	
	
	public static String wordCount(String input_file, String output_file){
		
		File inFile = new File(input_file);
		File outFile = new File(output_file);
		String status="";
		
		status=buildHashTable(inFile);
		if(!status.equals("success")) return status;
		
		status=createOutputFile(outFile);
		if(!status.equals("success")) return status;
		
		return "Success";
		
	}
	
	
	private static String buildHashTable(File inputFile){
		String line;
		
		try {
			
			Scanner fileScanner = new Scanner(inputFile);
			while(fileScanner.hasNext()){
				
				line = fileScanner.nextLine();
				for(String word:line.split("[^a-zA-Z]")){
					
					table.insert(word);
					
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "Input File error";
		} 
		
		return "Success";
		
	}
	
	
	private static String createOutputFile(File outputFile){
		
		try {
			
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
		
		
		} catch (IOException e) {
			e.printStackTrace();
			return "Output File error";
		}
		return "Success";
	}
	
}