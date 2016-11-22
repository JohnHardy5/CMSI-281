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
        	root = new TTNode(charArr[0], isLast(0, charArr));
        	if (isLast(0, charArr)) {return;}
        }
        insertNext(charArr, 0, root);
    }
    
    public boolean hasTerm (String query) {
        char[] charArr = normalizeTerm(query).toCharArray();
        return findNext(charArr, 0, root, false);
    }
    
    public String getSuggestedTerm (String query) {
    	System.out.println("Current query: " + query);
    	char[] charArr = normalizeTerm(query).toCharArray();
        //if (!findNext(charArr, 0, root, true)) {return null;}
    	TTNode current = advanceTo(charArr);
    	if (current == null) {return null;}//ensure that the prefix exists
        charArr = suggestNext(current, charArr, -1);
        return new String(charArr);
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
		int comparison = compareChars(charArr[p], current.letter);
		if (comparison == 0) {//char is equal to current
			if (isLast(p, charArr)) {
				current.wordEnd = true;//possible word inside a word
				return;
			}
			if (current.mid == null) {
				current.mid = new TTNode (charArr[p + 1], isLast(p, charArr));
			}
			insertNext(charArr, p + 1, current.mid);
		} else if (comparison < 0) {//char is less than current
			if (current.left == null) {
				current.left = new TTNode (charArr[p], isLast(p, charArr));
				if (isLast(p, charArr)) {return;}
			}
			insertNext(charArr, p, current.left);
		} else {//otherwise char is greater than current
			if (current.right == null) {
				current.right = new TTNode (charArr[p], isLast(p, charArr));
				if (isLast(p, charArr)) {return;}
			}
			insertNext(charArr, p, current.right);
		}
	}
    
    private boolean findNext (char[] charArr, int p, TTNode current, boolean findPrefix) {
    	if (current == null) {return false;}
    	int comparison = compareChars(charArr[p], current.letter);
    	if (comparison == 0) {
    		if (isLast(p, charArr)) {
    			if (findPrefix) {
    				return true;
    			} else {
    				return current.wordEnd;
    			}
    		}
    		return findNext (charArr, p + 1, current.mid, findPrefix);
    	}
    	if (comparison < 0) {
    		return findNext (charArr, p, current.left, findPrefix);
    	}
    	return findNext (charArr, p, current.right, findPrefix);
    }
    
    private char[] suggestNext (TTNode current, char[] arr, int best) {
    	System.out.println("current value: " + current.letter);
    	if (arr.length > best && best != -1) {return arr;}
    	if (current.wordEnd) {
        	if (arr.length < best || best == -1) {
        		best = arr.length;
        	}
        	System.out.println("Found end of word: " + new String(arr));
    		return arr;
    	}
    	char[] mid = new char[0], left = new char[0], right = new char[0];
    	if (current.mid != null) {
    		mid = suggestNext(current.mid, addLetter(arr, current.mid.letter), best);
    	}
    	if (current.left != null) {
    		arr[arr.length - 1] = current.left.letter;
    		left = suggestNext(current.left, arr, best);
    	}
    	if (current.right != null) {
    		System.out.print("Going right: " + new String(arr));
    		arr[arr.length - 1] = current.right.letter;
    		System.out.println(" -> " + new String(arr));
    		right = suggestNext(current.right, arr, best);
    	}
    	char[] winner = arr;
    	if ((mid.length <= left.length && mid.length != 0) || left.length == 0) {
    		winner = mid;
    	} else if (left.length != 0) {
    		winner = left;
    	}
    	if ((right.length <= winner.length || winner == arr) && right.length != 0) {
    		winner = right;
    	}//if mid, left, and right are empty, use arr
    	System.out.println("Found a winner: " + new String(winner));
    	return winner;
    }
    
    private boolean isLast (int index, char[] arr) {
    	return index == arr.length - 1;
    }
    
    private char[] addLetter (char[] arr, char letter) {
    	char[] result = new char[arr.length + 1];
    	for (int i = 0; i < arr.length; i++) {
    		result[i] = arr[i];
    	}
    	result[result.length - 1] = letter;
    	return result;
    }
    
    /*
     * Advances node from root to last item given in query.
     * Returns null if unable to do so.
     */
    private TTNode advanceTo (char[] query) {
    	TTNode current = root;
    	int position = 0;
    	while (position < query.length) {//iterate current into position
    		//System.out.println("Position: " + position);
    		//if (current != null) {System.out.println("Current: " + current.letter);} else {System.out.println(current);}
    		//if (current.right != null) {System.out.println("Current.right: " + current.right.letter);}
    		//if (current.left != null) {System.out.println("Current.left: " + current.left.letter);}
    		int comparison = compareChars(query[position], current.letter);
    		if (comparison == 0) {
    			position++;
    			if (position < query.length) {
    				if (current.mid == null) {return null;}
    				//System.out.println("Going mid!");
    				current = current.mid;
    			}
    		} else if (comparison < 0) {
    			if (current.left == null) {return null;}
    	    	//System.out.println("Going left!, new current: " + current);
    			current = current.left;
    		} else {
    			if (current.right == null) {return null;}
    			//System.out.println("Going right!, new current: " + current);
    			current = current.right;
    		}
    	}
    	//System.out.println("Done advancing the current node!");
    	return current;
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
