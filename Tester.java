package HashTable;

public class Tester {

	public static void main(String[] args) {
		
		WordCounter w = new WordCounter();
		w.wordCount("in","out"); //call statically later
		System.out.println(w);
		
		
		/*myHashTable table = new myHashTable();
		table.insert("dog");
		table.insert("cat");
		table.insert("cat");
		System.out.println(table);*/
	}

}
