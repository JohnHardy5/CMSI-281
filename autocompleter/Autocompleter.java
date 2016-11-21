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
    	System.out.println("Current query: " +query);
    	query = normalizeTerm(query);    	
    	char[] charArr = query.toCharArray();
        if (!findNext(charArr, 0, root, true)) {return null;}//ensure that the prefix exists
    	TTNode current = root;
    	int position = 0;
    	while (position < charArr.length) {//iterate current into position
    		System.out.println("Position: " +position);
    		if (current != null) {System.out.println("Current: " + current.letter);} else {System.out.println(current);}
    		if (current.right != null) {System.out.println("Current.right: " + current.right.letter);}
    		int comparison = compareChars(charArr[position], current.letter);
    		if (comparison == 0) {
    			position++;
    			if (position < charArr.length) {
    				System.out.println("Going mid!");
    				current = current.mid;
    			}
    		} else if (comparison < 0) {
    	    	System.out.println("Going left!");
    			current = current.left;
    		} else {
    			System.out.println("Going right!, new current: " + current);
    			current = current.right;
    		}
    	}
        ArrayList<Character> arrList = new ArrayList<Character>();
        for(char c : charArr) {//create arrList and add to it
            arrList.add(c);
        }
        arrList = suggestNext(current, arrList, -1);
        char[] chars = new char[arrList.size()];
        for (int i = 0; i < chars.length; i++) {
        	chars[i] = arrList.get(i);
        }
        return new String(chars);
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
    
    private ArrayList<Character> suggestNext (TTNode current, ArrayList<Character> arr, int best) {
    	if (arr.size() > best && best != -1) {return arr;}
    	if (current.wordEnd) {
        	if (best == -1) {
        		best = arr.size();
        	}
    		return arr;
    	}
    	ArrayList<Character> mid = arr, left = arr, right = arr;
    	if (current.mid != null) {
    		arr.add(current.mid.letter);
    		mid = suggestNext(current.mid, arr, best);
    	}
    	if (current.left != null) {
    		arr.add(current.left.letter);
    		left = suggestNext(current.left, arr, best);
    	}
    	if (current.right != null) {
    		arr.add(current.right.letter);
    		right = suggestNext(current.right, arr, best);
    	}
    	ArrayList<Character> winner = mid.size() < left.size() ? mid : left;
    	winner = winner.size() < right.size() ? winner : right;
    	return winner;
    }
    
    private boolean isLast (int index, char[] arr) {
    	return index == arr.length - 1;
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
