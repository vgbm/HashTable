package HashTable;

public class myHashTable {

	
	// The array that stores the hashed key
	private Entry[] table;
	private int tableLength;

	public myHashTable() {
		tableLength = getPrime(5);//default list size
		table = new Entry[tableLength];
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

	
	//primary hash function which uses Java's built in hashing method
	private int h1(String word) {
		int hashVal = word.hashCode();
		hashVal %= tableLength;
		if (hashVal < 0) {
			hashVal += tableLength;
		}
		return hashVal;
	}

	
	//hash function for determining stride length
	//uses word length to determine stride
	private int h2(String word) {
		return word.length();
	}

	
	//TODO
	private void rehash(){
		
	}
	
	
	//tries to find the first open slot
	private int probe(String key) {
		int i = h1(key)%tableLength; // first hash function
		int j = h2(key); // second hash function
		int iterations = 0;
		// keep probing while the current position is occupied (non-empty and
		// non-removed)
		//i%=tableLength;
		while (table[i] != null) {
			i = (i + j) % tableLength;
			iterations++;
			if (iterations > tableLength) {
				return -1;
			}
		}
		return i;
	}

	
	// Make private after testing
	public int findKey(String key) {
		int i = h1(key); // first hash function
		int j = h2(key); // second hash function
		int iterations = 0;
		
		i%=tableLength;
		while (table[i] != null) {
			// return if key is found, otherwise continue
			if (table[i].key.equals(key)) {
				return i;
			}
			i = (i + j) % tableLength;
			iterations++;
			if (iterations > tableLength)
				return -1;
		}
		return -1;
	}
	

	public void insert(String key) {

		int slotIndex = probe(key);
		int keyIndex = findKey(key);

		if (slotIndex == -1) {
			//TODO: increase length, reinsert everything
		} 
		else{
			if(keyIndex==-1){
				table[slotIndex] = new Entry(key);
			} else {
				table[keyIndex].incFreq();
			}
		}
	}
	
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(Entry entry:table){
			if(entry!=null)str.append(entry);
		}
		
		return str.toString();
	}
	
	// An Entry contains the unique words and their frequencies
	private class Entry {

		private String key; // the unique word
		private int frequency;

		private Entry(String key) {
			this.key = key;
			this.frequency = 1;
		}
		
		public void incFreq(){
			frequency++;
		}
		
		public String toString(){
			return "("+key+": "+frequency+")";
		}

	}

}
