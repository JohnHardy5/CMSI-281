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
    	int sentiment = 0;
    	while (fileReader.hasNextLine()) {
    		String currentLine = fileReader.nextLine();
    		String[] words = currentLine.split(" ");
    		for (int window = largest; window > 0; window--) {
    			for (int currPos = 0; currPos < words.length - window + 1; currPos++) {//do not iterate outside of 'window'
    				sentiment += analyzeSelection(words, window, currPos);
    			}
    		}
    	}
    	fileReader.close();
    	if (sentiment > 0) {
    		return "positive";
    	} else if (sentiment < 0) {
    		return "negative";
    	}
    	return "neutral";
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
 
    private int analyzeSelection (String[] words, int currWindow, int startPos) {
		String selection = makeSelection(words, currWindow, startPos);
		int sentiment = 0;
		if (posHash.get(selection) != null && posHash.get(selection).equals(selection)) {
			deleteSelection(words, currWindow, startPos);
			sentiment++;
		} else if (negHash.get(selection) != null && negHash.get(selection).equals(selection)) {
			deleteSelection(words, currWindow, startPos);
			sentiment--;
		}
    	return sentiment;
    } 
    
    private String makeSelection (String[] words, int selectionSize, int startPos) {
    	String selection = words[startPos];
		for (int i = 1; i < selectionSize; i++) {
			selection = selection + " " + words[startPos + i];
		}
		return selection;
    }
    
    private void deleteSelection (String[] words, int currWindow, int startPos) {
    	for (int i = startPos; i < startPos + currWindow; i++) {
    		words[i] = "";
    	}
    }
    
}
