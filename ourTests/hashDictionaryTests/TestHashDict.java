package dictionary.ourTests.hashDictionaryTests;

import dictionary.Dictionary;
import dictionary.HashDictionary;

import java.util.concurrent.TimeUnit;

public class TestHashDict {

    public TestHashDict() {
   }

    public void runTests() {
        System.out.println("Running own tests for file HashDictionary.java");
        runAFirstTest();
    }

    private void runAFirstTest() {

        /* now run all tests */
        runInsertTest();

    }

    private void runInsertTest() {
        /* construct test objects first */

        Dictionary<String, String> dict = new HashDictionary<>(3);
        String stringKey = "testStringForKey";
        String stringValue = "testStringForValue";

        int nrOfReps = 10;

        for (int i = 0; i < nrOfReps; i++) {
            String string1 = stringKey + i;
            String string2 = stringValue + i;
            String result = dict.insert(string1, string2);
            System.out.println(result);
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            System.out.println("goht nooed");
        }

        for (int i = 0; i < nrOfReps; i++) {

            String string1 = new String(stringKey + i);
            String string2 = stringValue + i;
            String insertResult = dict.insert(string1, string2);
            System.out.println("insertResult: " + insertResult);
            String searchResult = dict.search(stringKey);
            System.out.println("searchResult: " + searchResult);
        }


    }

}
