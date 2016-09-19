package yarn;
/*
 *Written by John Hardy and Eddy
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
        
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
        throw new UnsupportedOperationException();
    }
    
    public int getSize () {
        throw new UnsupportedOperationException();
    }
    
    public int getUniqueSize () {
        throw new UnsupportedOperationException();
    }
    
    public boolean insert (String toAdd) {
        throw new UnsupportedOperationException();
    }
    
    public int remove (String toRemove) {
        throw new UnsupportedOperationException();
    }
    
    public void removeAll (String toNuke) {
        throw new UnsupportedOperationException();
    }
    
    public int count (String toCount) {
        throw new UnsupportedOperationException();
    }
    
    public boolean contains (String toCheck) {
        throw new UnsupportedOperationException();
    }
    
    public String getNth (int n) {
        throw new UnsupportedOperationException();
    }
    
    public String getMostCommon () {
        throw new UnsupportedOperationException();
    }
    
    public Yarn clone () {
        throw new UnsupportedOperationException();
    }
    
    public void swap (Yarn other) {
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static Yarn knit (Yarn y1, Yarn y2) {
        throw new UnsupportedOperationException();
    }
    
    public static Yarn tear (Yarn y1, Yarn y2) {
        throw new UnsupportedOperationException();
    }
    
    public static boolean sameYarn (Yarn y1, Yarn y2) {
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    // Add your own here!
    
}

class Entry {
    String text;
    int count;
    
    Entry (String s, int c) {
        text = s;
        count = c;
    }
}
