/*
 * Test der verscheidenen Dictionary-Implementierungen
 *
 * O. Bittel
 * 28.05.2020
 */
package dictionary;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static test methods for different Dictionary implementations.
 * @author oliverbittel
 */
public class DictionaryTest {

	/**
	 * @param args not used.
	 */
	public static void main(String[] args)  {
		
		testSortedArrayDictionary();
		//testHashDictionary();
		//testBinaryTreeDictionary();
	}

	private static void testSortedArrayDictionary() {
		Dictionary<String, String> dict = new SortedArrayDictionary<>();
		testDict(dict);
	}
/*
	private static void testHashDictionary() {
		Dictionary<String, String> dict = new HashDictionary<>(3);
		testDict(dict);
	}

	
	private static void testBinaryTreeDictionary() {
		Dictionary<String, String> dict = new BinaryTreeDictionary<>();
		testDict(dict);
        
        // Test für BinaryTreeDictionary mit prettyPrint 
        // (siehe Aufgabe 10; Programmiertechnik 2).
        // Pruefen Sie die Ausgabe von prettyPrint auf Papier nach.
        BinaryTreeDictionary<Integer, Integer> btd = new BinaryTreeDictionary<>();
        btd.insert(10, 0);
        btd.insert(20, 0);
        btd.insert(50, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(40, 0);
        btd.insert(30, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(21, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        btd.insert(35, 0);
        btd.insert(45, 0);
        System.out.println("insert:");
        btd.prettyPrint();

        System.out.println("For Each Loop:");
        for (Dictionary.Entry<Integer, Integer> e : btd) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }

        btd.remove(30);
        System.out.println("remove:");
        btd.prettyPrint();

        btd.remove(35);
        btd.remove(40);
        btd.remove(45);
        System.out.println("remove:");
        btd.prettyPrint();
		
		btd.remove(50);
        System.out.println("remove:");
        btd.prettyPrint();
    }
*/
	private static void testDict(Dictionary<String, String> dict) {
		System.out.println("===== New Test Case ========================");
		System.out.println("test " + dict.getClass());
		System.out.println(dict.insert("gehen", "go") == null);		// true
		String s = new String("gehen");
		System.out.println(dict.search(s) != null);					// true
		System.out.println(dict.search(s).equals("go"));			// true
		System.out.println(dict.insert(s, "walk").equals("go"));	// true
		System.out.println(dict.search("gehen").equals("walk"));	// true
		System.out.println(dict.remove("gehen").equals("walk"));	// true
		System.out.println(dict.remove("gehen") == null); // true
		dict.insert("starten", "start");
		dict.insert("gehen", "go");
		dict.insert("schreiben", "write");
		dict.insert("reden", "say");
		dict.insert("arbeiten", "work");
		dict.insert("lesen", "read");
		dict.insert("singen", "sing");
		dict.insert("schwimmen", "swim");
		dict.insert("rennen", "run");
		dict.insert("beten", "pray");
		dict.insert("tanzen", "dance");
		dict.insert("schreien", "cry");
		dict.insert("tauchen", "dive");
		dict.insert("fahren", "drive");
		dict.insert("spielen", "play");
		dict.insert("planen", "plan");
		dict.insert("diskutieren", "discuss");
		System.out.println(dict.size());
		for (Dictionary.Entry<String, String> e : dict) {
			System.out.println(e.getKey() + ": " + e.getValue() + " search: " + dict.search(e.getKey()));
		}
	}
	private static void terminalTest() throws IOException {
		Scanner scanner = new Scanner(System.in);
		Dictionary<String, String> dict = new SortedArrayDictionary<>();

		while (scanner.hasNext()) {
			String input = scanner.nextLine().toUpperCase();

///Erstellung des dict

			if (input.equals("CREATE SORTEDARRAYDICTIONARY")) {
				dict = new SortedArrayDictionary<>();
				System.out.println("new SortedArrayDictionary created");
			} else if (input.equals("CREATE BINARYTREEDICTIONARY")) {
				dict = new BinaryTreeDictionary<>();
				System.out.println("new BinaryTreeDictionary created");
			} else if(input.equals("CREATE HASHDICTIONARY")) {
				Dictionary<String, String> dict = new HashDictionary<>();
				System.out.println("new HashDictionary created");

			}else if(input.equals("exit")){
				System.out.println("program was exited");
				break;
			}


			else if (Pattern.matches("READ (\\d+) ([A-Z.\\d_:]+$)", input)){ ///mit number
				Pattern patternMax = Pattern.compile("(\\d+) ([A-Z.\\d_:]+$)");
				Matcher matcherMax = patternMax.matcher(input);

				LineNumberReader in;
				String line;
				if(matcherMax.find()){
					in = new LineNumberReader(new FileReader(matcherMax.group(2)));
				}
		}
	}
	
}
