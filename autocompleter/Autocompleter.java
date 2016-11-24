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
        if (isEmpty()) {
        	root = new TTNode(charArr[0], isLast(charArr, 0));
        }
        insertNext(charArr, 0, root);
    }
    
    public boolean hasTerm (String query) {
        char[] charArr = normalizeTerm(query).toCharArray();
        return findNext(charArr, root, 0);
    }
    
    public String getSuggestedTerm (String query) {
    	char[] charArr = normalizeTerm(query).toCharArray();
    	TTNode current = advanceTo(charArr);
    	if (current == null) {return null;}//ensure that the prefix exists
        return new String(suggestNext(charArr, current));
    }
    
    public ArrayList<String> getSortedTerms () {
    	if (root == null) {return new ArrayList<String>();}
        return sortNext(addLetter(new char[0], root.letter), root, new ArrayList<String>());
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
		int comparison = compareChars(charArr[p], current.letter);
		if (comparison == 0) {
			if (isLast(charArr, p)) {
				current.wordEnd = true;//possible word inside a word
				return;
			}
			if (current.mid == null) {
				current.mid = new TTNode (charArr[p + 1], isLast(charArr, p));
			}
			insertNext(charArr, p + 1, current.mid);
		} else if (comparison < 0) {
			if (current.left == null) {
				current.left = new TTNode (charArr[p], isLast(charArr, p));
				if (isLast(charArr, p)) {return;}
			}
			insertNext(charArr, p, current.left);
		} else {
			if (current.right == null) {
				current.right = new TTNode (charArr[p], isLast(charArr, p));
				if (isLast(charArr, p)) {return;}
			}
			insertNext(charArr, p, current.right);
		}
	}
    
    private boolean findNext (char[] charArr,TTNode current, int p) {
    	if (current == null) {return false;}
    	int comparison = compareChars(charArr[p], current.letter);
    	if (comparison == 0) {
    		if (isLast(charArr, p)) {return current.wordEnd;}
    		return findNext (charArr, current.mid, p + 1);
    	}
    	if (comparison < 0) {
    		return findNext (charArr, current.left, p);
    	}
    	return findNext (charArr, current.right, p);
    }
    
    private char[] suggestNext (char[] charArr, TTNode current) {
    	if (current.wordEnd) {return charArr;}
    	char[] mid = new char[0], left = new char[0], right = new char[0];
    	if (current.mid != null) {
    		mid = suggestNext(addLetter(charArr, current.mid.letter), current.mid);
    	}
    	if (current.left != null) {
    		left = suggestNext(setLastLetter(charArr, current.left.letter), current.left);
    	}
    	if (current.right != null) {
    		right = suggestNext(setLastLetter(charArr, current.right.letter), current.right);
    	}
    	return getSmallest(mid, left, right, charArr);
    }
    
    /*
     * Returns the smallest of the three char arrays, ignoring empty ones.
     * Returns the original if all are empty.
     */
    private char[] getSmallest(char[] mid, char[] left, char[] right, char[] original) {
    	char[] winner = original;
    	if ((mid.length <= left.length && mid.length != 0) || left.length == 0) {
    		winner = mid;
    	} else if (left.length != 0) {
    		winner = left;
    	}
    	if ((right.length <= winner.length || winner == original) && right.length != 0) {
    		winner = right;
    	}
    	return winner;
    }
    
    private ArrayList<String> sortNext (char[] charArr, TTNode current, ArrayList<String> result) {
    	if (current.left != null) {
    		sortNext(setLastLetter(charArr, current.left.letter), current.left, result);
    	}
    	if (current.wordEnd) {
    		result.add(new String(charArr));
    	}
    	if (current.mid != null) {
    		sortNext(addLetter(charArr, current.mid.letter), current.mid, result);
    	}
    	if (current.right != null) {
    		sortNext(setLastLetter(charArr, current.right.letter), current.right, result);
    	}
    	return result;
    }
    
    /*
     * Returns a copy of given charArr with last letter set to given char.
     */
    private char[] setLastLetter (char[] charArr, char newLastLetter) {
		char[] clone = charArr.clone();
		clone[clone.length -1] = newLastLetter;
    	return clone;
    }
    
    /*
     * Returns a copy of given charArr with given letter added to the end.
     */
    private char[] addLetter (char[] charArr, char letter) {
    	char[] result = new char[charArr.length + 1];
    	for (int i = 0; i < charArr.length; i++) {
    		result[i] = charArr[i];
    	}
    	result[result.length - 1] = letter;
    	return result;
    }
    
    private boolean isLast (char[] charArr, int index) {
    	return index == charArr.length - 1;
    }
    
    /*
     * Advances a node from root to last item in given charArr.
     * Returns null if unable to do so.
     */
    private TTNode advanceTo (char[] charArr) {
    	if (root == null) {return null;}
    	TTNode current = root;
    	int position = 0;
    	while (position < charArr.length) {
    		int comparison = compareChars(charArr[position], current.letter);
    		if (comparison == 0) {
				position++;
    			if (position < charArr.length) {
    				if (current.mid == null) {return null;}
    				current = current.mid;
    			}
    		} else if (comparison < 0) {
    			if (current.left == null) {return null;}
    			current = current.left;
    		} else {
    			if (current.right == null) {return null;}
    			current = current.right;
    		}
    	}
    	return current;
    }
    
    // -----------------------------------------------------------
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /*
     * Internal storage of auto-completer search terms
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