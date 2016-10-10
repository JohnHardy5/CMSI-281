package linked_yarn;

import java.util.NoSuchElementException;

public class LinkedYarn implements LinkedYarnInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Node head;
    private int size, uniqueSize, modCount;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    LinkedYarn () {
    	head = new Node(null, 0);
        size = 0;
        uniqueSize = 0;
        modCount = 0;
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
        return head.next == null;
    }
    
    public int getSize () {
        return size;
    }
    
    public int getUniqueSize () {
        return uniqueSize;
    }
    
    public void insert (String toAdd) {
    	Node nodeToAddTo = findFirstNodeWith(toAdd);
    	if (nodeToAddTo != null) {
    		nodeToAddTo.count++;
    		size++;
    	} else {
    		createNode(toAdd);
    	}
        modCount++;
    }
    
    public int remove (String toRemove) {
    	Node nodeToRemoveFrom = findFirstNodeWith(toRemove);
    	if (nodeToRemoveFrom != null) {
    		modCount++;
    		//System.out.println(nodeToRemoveFrom.count);
    		nodeToRemoveFrom.count--;
    		size--;
    		if (nodeToRemoveFrom.count == 0) {
    			destroyNode(nodeToRemoveFrom);
    			return 0;
    		}
    		//System.out.println(nodeToRemoveFrom.count);
    		return nodeToRemoveFrom.count;
    	} else {
    		return 0;
    	}
    }
    
    public void removeAll (String toNuke) {
    	Node nodeToNuke = findFirstNodeWith(toNuke);
    	if (nodeToNuke != null) {
    		modCount++;
    		size -= nodeToNuke.count;
    		destroyNode(nodeToNuke);
    	}
    }
    
    public int count (String toCount) {
    	Node nodeToCount = findFirstNodeWith(toCount);
    	if (nodeToCount != null) {
    		return nodeToCount.count;
    	} else {
    		return 0;
    	}
    }
    
    public boolean contains (String toCheck) {
    	return findFirstNodeWith(toCheck) != null;
    }
    
    public String getMostCommon () {
    	if (isEmpty()) {return null;}
    	String mostCommon = null;
    	int mostCommonCount = 0;
        LinkedYarn.Iterator it = getIterator();
        while (it.hasNext()) {
        	it.next();
        	if (it.current.count > mostCommonCount) {
        		mostCommonCount = it.current.count;
        		mostCommon = it.current.text;
        	}
        }
        return mostCommon;
    }
    
    public LinkedYarn clone () {
        throw new UnsupportedOperationException();
    }
    
    public void swap (LinkedYarn other) {
        throw new UnsupportedOperationException();
    }
    
    public LinkedYarn.Iterator getIterator () {
    	if (isEmpty()) {
    		throw new IllegalStateException();
    	}
        return new Iterator(this);
    }
    
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static LinkedYarn knit (LinkedYarn y1, LinkedYarn y2) {
        throw new UnsupportedOperationException();
    }
    
    public static LinkedYarn tear (LinkedYarn y1, LinkedYarn y2) {
        throw new UnsupportedOperationException();
    }
    
    public static boolean sameYarn (LinkedYarn y1, LinkedYarn y2) {
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    
    //Creates a new node and updates all the next and prev values appropriately.
    private void createNode (String textToAdd) {
    	Node n = new Node(textToAdd, 1);
    	n.next = head.next;
    	if (head.next != null) {
    		head.next.prev = n;
    	}
    	head.next = n;
    	size++;
    	uniqueSize++;
    }
    
    //Searches this linked list for a given string, returning the node with the string or null otherwise.
    private Node findFirstNodeWith(String toFind) {
        LinkedYarn.Iterator it = getIterator();
        while(it.hasNext()) {
        	it.next();
        	if (it.current.text == toFind) {
        		return it.current;
        	}
        }
    	return null;
    }
    
    //Destroys given node by removing all references to it and fixing any gaps left behind.
    private void destroyNode(Node toDestroy) {
    	if (toDestroy.prev == null) {
    		head.next = toDestroy.next;
    	} else {
    		toDestroy.prev.next = toDestroy.next;
    	}
    	if (toDestroy.next != null) {
    		toDestroy.next.prev = toDestroy.prev;
    	}
    	toDestroy.next = null;
    	toDestroy.prev = null;
    	uniqueSize--;
    }
    
    
    // -----------------------------------------------------------
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator implements LinkedYarnIteratorInterface {
        LinkedYarn owner;
        Node current;
        int itModCount, currCount;
        
        Iterator (LinkedYarn y) {
            owner = y;
            current = y.head;
            itModCount = owner.modCount;
            currCount = 0;
        }
        
        public boolean hasNext () {
        	//System.out.println("Current count: " + currCount + " Count: " + current.count + " Next: " + current.next + " IsValid: " + isValid());
            return (currCount < current.count || current.next != null) && isValid();
        }
        
        public boolean hasPrev () {
            return (currCount > 1 || current.prev != null) && isValid();
        }
        
        public boolean isValid () {
            return itModCount == owner.modCount;
        }
        
        public String getString () {
            if (!isValid()) {return null;}
            return current.text;
        }

        public void next () {
            if (!hasNext()) {
            	throw new NoSuchElementException();
            } else if (!isValid()) {
            	throw new IllegalStateException();
            } else if (currCount < current.count) {
            	currCount++;
            	//System.out.println("Moving up one occurence");
            } else {
            	//System.out.println(current);
            	current = current.next;
            	//System.out.println(current);
            	currCount = 1;
            	//System.out.println("Moving up one node");
            }
        }
        
        public void prev () {
            if (!hasPrev()) {
            	throw new NoSuchElementException();
            } else if (!isValid()) {
            	throw new IllegalStateException();
            } else if (currCount > 1) {
            	currCount--;
            } else {
            	current = current.prev;
            	currCount = current.count;
            }
        }
        
        public void replaceAll (String toReplaceWith) {
            current.text = toReplaceWith;
            itModCount += current.count;
            owner.modCount += current.count;
        }
    }
    
    class Node {
        Node next, prev;
        String text;
        int count;
        
        Node (String t, int c) {
            text = t;
            count = c;
        }
    }
    
}