package HashTable;

import java.util.Iterator;
import java.util.LinkedList;

public class myHashTable {

	
	// The array that stores the hashed key
	private LinkedList<Entry>[] table;
	private int tableLength;

	
	@SuppressWarnings("unchecked")
	public myHashTable(int length) {
		tableLength = getPrime(length);//default list size
		table = new LinkedList[tableLength];
	}

	
	// Returns the closest prime number below inputNum
	//used as tableLength should always be prime so that all empty slots can be filled
	public int getPrime(int inputNum) {
		
		for(int i = inputNum - 1; i >= 1; i--) {
	
			int fact = 0;
			for (int j = 2; j <= (int) Math.sqrt(i); j++) {
				if (i % j == 0)
					fact++;
			}
			if (fact == 0) {
				return i;
			}
		}
		/* Return a prime number */
		return 3;
		
	}

	
	//hash function which uses Java's built in hashing method
	private int hash(String word) {
		int hashVal = word.hashCode();
		hashVal %= tableLength;
		if (hashVal < 0) {
			hashVal += tableLength;
		}
		return hashVal;
	}

	
	//Determines that a rehash is needed when the length of any one
	//bucket is longer than the hash table's length
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
	

	private void rehash(){
		
		myHashTable replacement = new myHashTable(getPrime((int)(tableLength*2)));

		for(int i = 0; i<tableLength; i++){
			
			if(table[i]!=null){
				Iterator<Entry> iter = table[i].iterator();
				
				while(iter.hasNext()){
					Entry entry = iter.next();
					replacement.insert(entry.key, entry.frequency);
				}
			}
		}
		
		table = replacement.table;
		tableLength = replacement.tableLength;
	}
	
	
	private int indexOfEntry(String key,int slot) {
		
		if(table[slot]==null){
			table[slot] = new LinkedList<Entry>();
			return -1;
		}
		
		for(int i=0;i<table[slot].size();i++){
			if(table[slot].get(i).key.equals(key))
				return i;
		}
		return -1;
	}
	

	public void insert(String key, int frequency) {
		
		int slotIndex = hash(key);
		int position = indexOfEntry(key,slotIndex);
		
		if(position!=-1){
			table[slotIndex].get(position).frequency++;
		}
		
		else{
			table[slotIndex].add(new Entry(key,frequency));
		}
		
		if(needsRehashed())
			rehash();
	}
	
	
	public String getStatistics(){
		
		int words=0;
		
		for(int i=0; i<tableLength;i++){
			
			if(table[i]!=null){
				words+=table[i].size();
			}
		}
		
		StringBuilder str = new StringBuilder("Total Words: "+words+
				"\tHash table size: "+tableLength+
				"\tAvg length of collision lists: "+words/tableLength);
		return str.toString();
	}
	
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		
		for(int i=0;i<tableLength;i++){
			
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
