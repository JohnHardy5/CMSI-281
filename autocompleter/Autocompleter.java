package autocompleter;

import java.util.ArrayList;

public class Autocompleter implements AutocompleterInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    TTNode root;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    Autocompleter () {
        root = null;
    }
    

    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    public boolean isEmpty () {
        return root == null;
    }
    
    public void addTerm (String toAdd) {
        char[] charArr = normalizeTerm(toAdd).toCharArray();
        insertNext(charArr, 0, root);
    }
    
    public boolean hasTerm (String query) {
        throw new UnsupportedOperationException();
    }
    
    public String getSuggestedTerm (String query) {
        throw new UnsupportedOperationException();
    }
    
    public ArrayList<String> getSortedTerms () {
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    private String normalizeTerm (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    /*
     * Returns:
     *   int less than 0 if c1 is alphabetically less than c2
     *   0 if c1 is equal to c2
     *   int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    // [!] Add your own helper methods here!
    
    private void insertNext (char[] charArr, int p, TTNode current) {
		boolean isLast = (p == charArr.length - 1);
    	if (current == null) {//add to empty spot
    		current = new TTNode(charArr[p], isLast);
    		if (isLast) {//stop at last letter
    			return;
    		}
    		insertNext (charArr, p + 1, current.mid);
		} else {
			int comparison = compareChars(charArr[p], current.letter);
			if (comparison < 0) {//char is less than current
				insertNext(charArr, p, current.left);
			} else if(comparison == 0) {//char is equal to current
				if (isLast) {//ensure that the word is not already in the tree
					current.wordEnd = true;
					return;
				}
				insertNext(charArr, p + 1, current.mid);
			} else {//otherwise char is greater than current
				insertNext(charArr, p, current.right);
			}
		}
    }
    
    // -----------------------------------------------------------
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /*
     * Internal storage of autocompleter search terms
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {
        
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
            left    = null;
            mid     = null;
            right   = null;
        }
        
    }
    
}
