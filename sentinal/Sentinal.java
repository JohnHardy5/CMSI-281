package sentinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Sentinal implements SentinalInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    
    private PhraseHash posHash, negHash;

    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    
    Sentinal (String posFile, String negFile) throws FileNotFoundException {
    	posHash = new PhraseHash();
    	negHash = new PhraseHash();
    	loadSentimentFile (posFile, true);
    	loadSentimentFile (negFile, false);
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    public void loadSentiment (String phrase, boolean positive) {
       	if (positive) {
       		posHash.put(phrase);
       	} else {
       		negHash.put(phrase);
       	}
    }
    
    public void loadSentimentFile (String filename, boolean positive) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new File(filename));
        while (fileReader.hasNext()) {
        	String current = fileReader.nextLine();
        	loadSentiment(current, positive);
        }
        fileReader.close();
    }
    
    public String sentinalyze (String filename) throws FileNotFoundException {
    	Scanner fileReader = new Scanner(new File(filename));
    	int largest = posHash.longestLength() > negHash.longestLength() ? posHash.longestLength() : negHash.longestLength();
    	System.out.println("Largest: " + largest);
    	int posCount = 0, negCount = 0;
    	while (fileReader.hasNext()) {
    		String currentLine = fileReader.nextLine();
    		System.out.println("Current line: " + currentLine);
    		String[] words = currentLine.split(" ");
    		System.out.println("Number of words: " + words.length);
    		for (int i = largest; i > 0; i--) {
    			System.out.println("Value of i: " + i);
    			for (int j = 0; j < words.length - i; j++) {
    				System.out.println("Value of j: " + j);
    				String selection = makeSelection(words, i, j);
    				if (posHash.get(selection) != null && posHash.get(selection).equals(selection)) {
    					System.out.println("Adding one to positive count.");
    					posCount++;
    				}
    				if (negHash.get(selection) != null && negHash.get(selection).equals(selection)) {
    					System.out.println("Adding one to negative count.");
    					negCount++;
    				}
    				
    			}
    		}
    	}
    	fileReader.close();
    	if (posCount > negCount) {
    		return "positive";
    	} else if (negCount > posCount) {
    		return "negative";
    	}
    	return "neutral";
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    private int analyzeLine (String[] words ) {
    	return 0;
    } 
    
    private String makeSelection (String[] words, int selectionSize, int startPos) {
    	String selection = words[startPos];
		for (int i = 1; i < selectionSize; i++) {
			selection = selection + " " + words[startPos + i];
		}
		//System.out.println("Selection: " + selection);
		return selection;
    }
    
}

