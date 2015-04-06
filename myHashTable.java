import java.util.Iterator;
import java.util.LinkedList;

public class myHashTable {

	
	// The array that stores the hashed key
	private LinkedList<Entry>[] table;
	private int tableLength;

	
	@SuppressWarnings("unchecked")
	public myHashTable(int length) {
		tableLength = length;//default list size
		table = new LinkedList[tableLength];
	}

	
	//hash function which uses Java's built in hashing method
	//to assign each string a number and then mods it
	//to fit in the table
	private int hash(String word) {
		int hashVal = word.hashCode();
		hashVal %= tableLength; //makes the hash value an index within the table
		
		if (hashVal < 0) { //making sure the position index is in bounds
			hashVal += tableLength;
		}
		return hashVal;
	}

	
	//Determines that a rehash is needed when the length of any one
	//LinkedList is longer than the hash table's length
	//This is because the load factor exceeds one-to-one
	private boolean needsRehashed(){
		
		for(int list=0;list<tableLength;list++){
			
			if(table[list]!=null){				
				if(table[list].size()>tableLength)
					return true;
			}		
		}
		
		return false;
		
	}
	

	//reinserts all of the entries back into a bigger table
	//to keep the load factor under 1:1
	private void rehash(){
		
		myHashTable replacement = new myHashTable(tableLength*2);

		for(int i = 0; i<tableLength; i++){ //for every Linked List...
			
			if(table[i]!=null){ //won't try to access empty lists
				Iterator<Entry> iter = table[i].iterator();
				
				//iterates through the i-th linked list, reinserting every
				//entry in the list
				while(iter.hasNext()){
					Entry entry = iter.next();
					replacement.insert(entry.key, entry.frequency);
				}
			}
		}
		
		//changing out the temporary, new table with the old one
		//so that the changes can take effect
		table = replacement.table;
		tableLength = replacement.tableLength;
	}
	
	
	//checks to see if the key is present in the
	//table's LinkedList provided as a parameter (slot-th list)
	//If it is present, return the index in the list
	//else return -1
	private int indexOfEntry(String key,int slot) {
		
		if(table[slot]==null){ //if the Linked List is uninitialized...
			table[slot] = new LinkedList<Entry>();//initialize it
			return -1; //indicates that the key wasn't here
		}
		
		//loops through the list to check for the key
		for(int i=0;i<table[slot].size();i++){
			if(table[slot].get(i).key.equals(key))
				return i;//if found, return index
		}
		return -1; //no key found
	}
	
	
	//either increments frequency or inserts a new Entry depending
	//on if it has already been added to the list
	public void insert(String key, int frequency) {
		
		int slotIndex = hash(key); //gets which list the entry should be in
		int position = indexOfEntry(key,slotIndex);//gets the index of the word in the List
		
		//if the word has already been added into the list
		if(position!=-1){
			//increments its frequency
			table[slotIndex].get(position).frequency++;
		}
		
		else{
			//if the word hasn't been added, add it
			table[slotIndex].add(new Entry(key,frequency));
		}
		
		//checks for whether the hash table needs to increase in size
		//and rehash
		if(needsRehashed())
			rehash();
	}
	
	
	//returns the printed statistics: total words, size of table,
	//and avg collisions
	public String getStatistics(){
		
		int words=0;
		
		//adds up total number of entries and stores it in "words"
		for(int i=0; i<tableLength;i++){
			
			if(table[i]!=null){
				words+=table[i].size();
			}
		}
		
		//does calculations and returns info on avg collisions, size of table, and words
		return "Total Words: "+words+
				",\tHash table size: "+tableLength+
				",\tAvg length of collision lists: "+words/tableLength;
	}
	
	
	//used for creating the output file, outputs a string with
	//each entry and its frequency
	public String toString(){
		StringBuilder str = new StringBuilder();
		
		for(int i=0;i<tableLength;i++){
			
			//iterates through each entry and appends its info to the output string
			if(table[i]!=null){
				Iterator<Entry> iter = table[i].iterator();
				
				while(iter.hasNext()){
					str.append(iter.next());
				}
				str.append("\n");
			}
			
		}
		
		return str.toString();
	}
	
	
	// An Entry contains the unique words and their frequencies
	private class Entry {

		private String key; // the unique word
		private int frequency;

		private Entry(String key, int frequency) {
			this.key = key;
			this.frequency = frequency;
		}
		
		public String toString(){
			return "("+key+": "+frequency+")";
		}

	}

}
