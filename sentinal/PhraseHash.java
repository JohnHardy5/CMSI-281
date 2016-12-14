package sentinal;

import java.util.LinkedList;

public class PhraseHash implements PhraseHashInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    
    private final static int BUCKET_COUNT = 1000;
    private int size, longest;
    private LinkedList<String>[] buckets;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    
    @SuppressWarnings("unchecked") // Don't worry about this >_>
    PhraseHash () {
        size = 0;
        longest = 0;
        buckets = new LinkedList[BUCKET_COUNT];
    }
    
    
    // -----------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------
    
    public int size () {
        return size;
    }
    
    public boolean isEmpty () {
        return size == 0;
    }
    
    public void put (String s) {
    	int position = forneyHash(s);
		if (buckets[position] == null) {
			buckets[position] = new LinkedList<String>();
		}
		if (buckets[position].contains(s)) {
			return;
		} else {
			buckets[position].add(s);
			size++;
			longest = longest < getWordCount(s) ? getWordCount(s) : longest;
		}
    }
    
    public String get (String s) {
    	int position = forneyHash(s);
		if (buckets[position] == null || !buckets[position].contains(s)) {
	    	return null;
		} else {
			return s;
		}
    }
    
    public int longestLength () {
        return longest;
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    private int forneyHash (String s) {
    	int largePrime = 129;//seems good enough
    	int asciiVal = 0;
    	for (int i = 0; i < s.length(); i++) {
    		asciiVal += s.charAt(i);
    	}
    	return (asciiVal * largePrime) % BUCKET_COUNT;
    }
    
    private int getWordCount (String s) {
    	return s.split(" ").length;
    }
    
}
