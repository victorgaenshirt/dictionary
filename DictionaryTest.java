/*
 * Test der verscheidenen Dictionary-Implementierungen
 *
 * O. Bittel
 * 28.05.2020
 */
package dictionary;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static test methods for different Dictionary implementations.
 * @author oliverbittel
 */
public class DictionaryTest {

	private static Dictionary<String, String> dict = new SortedArrayDictionary<>();
//	private static String path = "/home/victor/Studium/Algorithmen_und_Datenstrukturen/aufgabe1/dictionary";
	private static String path = "./dtengl.txt";

	/**
	 * @param args not used.
	 */
	public static void main(String[] args) throws IOException {
		
//		testSortedArrayDictionary();
//		testHashDictionary();
//		testBinaryTreeDictionary();

		terminalTest();
	}

	private static void testSortedArrayDictionary() {
		Dictionary<String, String> dict = new SortedArrayDictionary<>();
		testDict(dict);
	}

	private static void testHashDictionary() {
		Dictionary<String, String> dict = new HashDictionary<>(3);
		testDict(dict);
	}

	
/*
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
		System.out.println("dict.size(): " + dict.size());
		for (Dictionary.Entry<String, String> e : dict) {
			System.out.println(e.getKey() + ": " + e.getValue() + " search: " + dict.search(e.getKey()));
		}
	}

	private static void terminalTest() throws IOException {
		Scanner scanner = new Scanner(System.in);
		Dictionary<String, String> dict = new SortedArrayDictionary<>();

		while (scanner.hasNext()) {
			String input = scanner.nextLine();

///Erstellung des dict

			if (input.equalsIgnoreCase("CREATE SORTEDARRAYDICTIONARY")) {
				dict = new SortedArrayDictionary<>();
				System.out.println("new SortedArrayDictionary created");
//			} else if (input.equalsIgnoreCase("CREATE BINARYTREEDICTIONARY")) {
//				dict = new BinaryTreeDictionary<>();
//				System.out.println("new BinaryTreeDictionary created");
			} else if (input.equalsIgnoreCase("CREATE HASHDICTIONARY")) {
				dict = new HashDictionary<>(3);
				System.out.println("new HashDictionary created");

			} else if (input.equals("exit")) {
				System.out.println("program was exited");
				break;


			} else if (Pattern.matches("(read|Read|READ) (\\d+) ([A-Za-z.\\d_:]+$)", input)) {
				/////mit number
				Pattern patternMax = Pattern.compile("(\\d+) ([A-Za-z.\\\\\\d_:]+$)");
				Matcher matcherMax = patternMax.matcher(input);

				LineNumberReader in;
				String line;
				if (matcherMax.find()) {
					in = new LineNumberReader(new FileReader(matcherMax.group(2)));

					int max = Integer.parseInt(matcherMax.group(1)); /// argument an 2. stelle(number) in Int
					int counter = 0;

					while ((line = in.readLine()) != null && counter < max) {
						String[] wordArr = line.split(" ");
						dict.insert(wordArr[0], wordArr[1]); //insert ins dict
						++counter;
					}
				}
				System.out.println("n lines were read into the dictionary");




			} else if (Pattern.matches("(read|Read|READ) ([A-Za-z.\\\\\\d_:]+$)", input)){
				///ohne Number
				Pattern pattern = Pattern.compile("([A-Za-z.\\\\\\d_:]+$)");
				Matcher matcher = pattern.matcher(input);

				LineNumberReader in;
				String line;

				if (matcher.find()) {
					//System.out.println("group1: " + matcher.group(1));

					in = new LineNumberReader(new FileReader(matcher.group(1)));

					while ((line = in.readLine()) != null) {
						String[] wf = line.split(" ");

						dict.insert(wf[0], wf[1]); //insert ins dict					}
					}
				}
				System.out.println(" whole file were read into the dictionary");


			} else if(input.equals("p")){
				//print
				System.out.println(dict.size());
				for (Dictionary.Entry<String, String> e : dict) {
					System.out.println(e.getKey() + ": " + e.getValue());
				}

			} else if(Pattern.matches("s [A-Za-z]*", input)) {
				//search german word -> print english word
				Pattern pattern = Pattern.compile("([A-Za-z]+$)");
				Matcher matcher = pattern.matcher(input);

				if(matcher.find()){
					String GermanWord = matcher.group(1);
					System.out.println(dict.search(GermanWord));
				}

			} else if((Pattern.matches("i [A-Za-z]* [A-Za-z]*", input))) {
				//insert pair of words
				Pattern pattern = Pattern.compile("([A-Za-z]+) ([A-Za-z]+$)");
				Matcher matcher = pattern.matcher(input);

				if(matcher.find()){
					String GermanWord = matcher.group(1);
					String EnglishWord = matcher.group(2);
					dict.insert(GermanWord, EnglishWord);
				}
				System.out.println("entry was added");

			} else if(Pattern.matches("r [A-Za-z]*", input)) {
				//remove one entry
				Pattern pattern = Pattern.compile("([A-Za-z]+$)");
				Matcher matcher = pattern.matcher(input);

				if (matcher.find()) {
					String GermanWord = matcher.group(1);
					dict.remove(GermanWord);
				}
				System.out.println("word removed");

			} else if(input.equals("test")) {
				System.out.println("SortedArrayDictionary");
				performanceCreation(8000, 10, "Aufbau 8000: ", Type.sorted);
				performanceCreation(16000, 10, "Aufbau 16000: ", Type.sorted);
				performanceSearchSuccess(8000, 100, "Search Success 8000: ", Type.sorted, true);
				performanceSearchSuccess(16000, 100, "Search Success 16000: ", Type.sorted, true);
				performanceSearchSuccess(8000, 100, "Search Failure 8000: ", Type.sorted, false);
				performanceSearchSuccess(16000, 100, "Search Failure 16000: ", Type.sorted, false);
				System.out.println("");
				System.out.println("HashDictionary");
				performanceCreation(8000, 500, "Aufbau 8000: ", Type.hash);
				performanceCreation(16000, 500, "Aufbau 16000: ", Type.hash);
				performanceSearchSuccess(8000, 100, "Search Success 8000: ", Type.hash, true);
				performanceSearchSuccess(16000, 100, "Search Success 16000: ", Type.hash, true);
				performanceSearchSuccess(8000, 100, "Search Failure 8000: ", Type.hash, false);
				performanceSearchSuccess(16000, 100, "Search Failure 16000: ", Type.hash, false);
				System.out.println("");
				System.out.println("BinaryTreeDictionary");
				performanceCreation(8000, 500, "Aufbau 8000: ", Type.binary);
				performanceCreation(16000, 500, "Aufbau 16000: ", Type.binary);
				performanceSearchSuccess(8000, 100, "Search Success 8000: ", Type.binary, true);
				performanceSearchSuccess(16000, 100, "Search Success 16000: ", Type.binary, true);
				performanceSearchSuccess(8000, 100, "Search Failure 8000: ", Type.binary, false);
				performanceSearchSuccess(16000, 100, "Search Failure 16000: ", Type.binary, false);
				System.out.println("");
			}else{
				System.out.println("unknown command");
			}
		}
		scanner.close();

	}

	private enum Type {
		sorted, hash, binary;
	}

	private static void initDict(Type type) {
		switch (type) {
			case sorted:
				Dictionary<String, String> dict = new SortedArrayDictionary<>();
				break;
			case hash:
				dict = new HashDictionary<>(3);
				break;
//			case binary:
//				dict = new BinaryTreeDictionary<>();
//				break;
		}
	}

	private static void createDict(int n) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			int i = 0;
			while (line != null && i < n) {
				String[] words = line.split(" ");
				String german = words[0];
				String english = words[1];
				dict.insert(german, english);
				line = reader.readLine();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void performanceCreation(int n, int loops, String msg, Type type) {
		double sumElapsedTime = 0;

		for (int i = 0; i < loops; i++) {
			BufferedReader reader;
			try {
				initDict(type);
				reader = new BufferedReader(new FileReader(path));
				String line = reader.readLine();
				int j = 0;

				long start = System.nanoTime();

				while (line != null && j < n) {
					String[] words = line.split(" ");
					String german = words[0];
					String english = words[1];
					dict.insert(german, english);
					line = reader.readLine();
					j++;
				}

				long end = System.nanoTime();
				double elapsedTime = (double)(end-start)/1.0e06;
				sumElapsedTime += elapsedTime;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		double average = sumElapsedTime / loops;
		System.out.println(msg + "" + average);
	}

	private static void performanceSearchSuccess(int n, int loops, String msg, Type type, boolean success) {
		initDict(type);
		createDict(n);
		LinkedList<String> list = wordsInList(n, success);
		double sumElapsedTime = 0;

		for (int i = 0; i < loops; i++) {
			long start = System.nanoTime();
			for (String word : list) {
				dict.search(word);
			}
			long end = System.nanoTime();
			double elapsedTime = (double) (end - start) / 1.0e06;
			sumElapsedTime += elapsedTime;
		}

		double average = sumElapsedTime / loops;
		System.out.println(msg + "" + average);
	}

	private static LinkedList<String> wordsInList(int n, boolean success) {
		BufferedReader reader;
		LinkedList<String> list = new LinkedList<>();
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			int i = 0;
			while (line != null && i < n) {
				String[] words = line.split(" ");
				String german = words[0];
				String english = words[1];

				if (success) {
					list.add(german);
				} else {
					list.add(english);
				}

				line = reader.readLine();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}