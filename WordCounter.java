package HashTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WordCounter {

	static myHashTable table = new myHashTable(50); //default length of 10
	
	
	public WordCounter(){
		
	}
	
	
	public static String wordCount(String input_file, String output_file){
		
		File inFile = new File(input_file);
		File outFile = new File(output_file);
		String status="";
		
		status=buildHashTable(inFile);
		if(!status.equals("Success")) return status;
		
		System.out.println(table.getStatistics());
		
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
					
					if(!word.equals(""))
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
	
	
	private static String createOutputFile(File outputFile){
		
		try {
			
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
			
			fileWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			return "Output File error";
		}
		
		return "Okay;";//add the shit he wants here
	}
	
	public String toString(){
		return table.toString();
	}
	
}