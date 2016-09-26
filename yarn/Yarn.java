package yarn;
/*
 *Completed by John Hardy
 */

public class Yarn implements YarnInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Entry[] items;
    private int size;
    private int uniqueSize;
    private final int MAX_SIZE = 100;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    Yarn () {
        items = new Entry[MAX_SIZE];
        size = 0;
        uniqueSize = 0;
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
        return size == 0;
    }
    
    public int getSize () {
        return size;
    }
    
    public int getUniqueSize () {
        return uniqueSize;
    }
    
    public boolean insert (String toAdd) {
    	int toAddTo = findFirstString (toAdd);
    	if (toAddTo > -1) {
    		items[toAddTo].count++;
    		size++;
    		return true;
    	} else if (uniqueSize < 100) {
	        items[uniqueSize] = new Entry(toAdd, 1);
	        size++;
	        uniqueSize++;
	        return true;
    	}
    	return false;
    }
    
    public int remove (String toRemove) {
    	int toRemoveFrom = findFirstString(toRemove);
    	if (toRemoveFrom > -1) {
    		items[toRemoveFrom].count--;
    		size--;
    		if (items[toRemoveFrom].count == 0) {
    			destroyObject (toRemoveFrom);
    			return 0;
    		}
    		return items[toRemoveFrom].count;
    	}
    	return 0;
    }
    
    public void removeAll (String toNuke) {
    	int toRemoveAllFrom = findFirstString (toNuke);
    	if (toRemoveAllFrom > -1) {
    		size -= items[toRemoveAllFrom].count;
    		destroyObject(toRemoveAllFrom);
    	}
    }
    
    public int count (String toCount) {
    	int toCountFrom = findFirstString(toCount);
    	if (toCountFrom > -1) {
    		return items[toCountFrom].count;
    	}
    	return 0;
    }
    
    public boolean contains (String toCheck) {
    	return (findFirstString(toCheck) > -1);
    }
    
    public String getNth (int n) {
        int iteration = -1;
        for (int i = 0; i < uniqueSize; i++) {
        	for (int j = 0; j < items[i].count; j++) {
        		iteration++;
        		if (iteration == n) {
        			return items[i].text;
        		}
        	}
        }
        return "";
    }
    
    public String getMostCommon () {
        String mostCommon = null;
        int mostCommonCount = 0;
        for (int i = 0; i < uniqueSize; i++) {
        	if (items[i].count > mostCommonCount) {
        		mostCommon = items[i].text;
        		mostCommonCount = items[i].count;
        	}
        }
        return mostCommon;
    }
    
    public Yarn clone () {
        Yarn newYarn = new Yarn();
        newYarn.size = size;
        newYarn.uniqueSize = uniqueSize;
        for (int i = 0; i < uniqueSize; i++) {
        	newYarn.items[i] = new Entry(items[i].text, items[i].count);
        }
        return newYarn;
    }
    
    public void swap (Yarn other) {
        Entry[] entryRef = other.items;
        int sizeRef = other.getSize();
        int uniqueSizeRef = other.getUniqueSize();
        other.items = items;
        other.size = size;
        other.uniqueSize = uniqueSize;
        items = entryRef;
        size = sizeRef;
        uniqueSize = uniqueSizeRef;
    }
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static Yarn knit (Yarn y1, Yarn y2) {
        Yarn yarny = y1.clone();
        for (int i = 0; i < y2.getSize(); i++) {
        	yarny.insert(y2.getNth(i));
        }
        return yarny;
    }
    
    public static Yarn tear (Yarn y1, Yarn y2) {
        Yarn yarny = y1.clone();
        for (int i = 0; i < y2.getSize(); i++) {
        	if (yarny.contains(y2.getNth(i))) {
        		yarny.remove(y2.getNth(i));
        	}
        }
        return yarny;
    }
    
    public static boolean sameYarn (Yarn y1, Yarn y2) {
        if (y1.getSize() != y2.getSize() || y1.getUniqueSize() != y2.getUniqueSize()) {
        	return false;
        }
        for (int i = 0; i < y1.getUniqueSize(); i++) {
    		if (!y2.contains(y1.items[i].text) || y2.count(y1.items[i].text) != y1.items[i].count) {
    			return false;
    		}
    	}
        return true;
    }
    
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    
    /*
     * Destroys the entry at given location in items[] by overwriting it with the last item 
     * and setting the last item to null.
     */
    private void destroyObject (int objToDestroy) {
		items[objToDestroy] = items[uniqueSize - 1];
		items[uniqueSize - 1] = null;
		uniqueSize--;
    }
    
    /*
     * Returns location of first string found in items[] equal to string given.
     * Returns -1 if no string is found.
     */
    private int findFirstString (String toFind) {;
    	for (int i = 0; i < uniqueSize; i++) {
    		if (items[i].text.equals(toFind)) {
    			return i;
    		}
    	}
    	return -1;
    } 
}

class Entry {
    String text;
    int count;
    
    Entry (String s, int c) {
        text = s;
        count = c;
    }
}
