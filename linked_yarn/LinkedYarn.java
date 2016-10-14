package linked_yarn;

import java.util.NoSuchElementException;
/*
 * Completed by John Hardy
 */

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
        size = 0;
        uniqueSize = 0;
        modCount = 0;
    }
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    public boolean isEmpty () {
        return this.head == null;
    }
    
    public int getSize () {
        return this.size;
    }
    
    public int getUniqueSize () {
        return this.uniqueSize;
    }
    
    public void insert (String toAdd) {
    	Node nodeToAddTo = findFirstNodeWith(toAdd);
    	if (nodeToAddTo != null) {
    		nodeToAddTo.count++;
    	} else {
    		this.addNewNode(toAdd, 1);
    	}
		size++;
        modCount++;
    }
    
    public int remove (String toRemove) {
    	Node nodeToRemoveFrom = findFirstNodeWith(toRemove);
    	if (nodeToRemoveFrom != null) {
    		modCount++;
    		size--;
    		nodeToRemoveFrom.count--;
    		if (nodeToRemoveFrom.count == 0) {
    			destroyNode(nodeToRemoveFrom);
    			return 0;
    		}
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
    	String mostCommon = "";
    	int mostCommonCount = 0;
    	Node currentNode = this.head;
        while (currentNode != null) {
        	if (currentNode.count > mostCommonCount) {
        		mostCommonCount = currentNode.count;
        		mostCommon = currentNode.text;
        	}
        	currentNode = currentNode.next;
        }
        return mostCommon;
    }
    
    public LinkedYarn clone () {
        LinkedYarn linkYarnClone = new LinkedYarn();
        linkYarnClone.size = this.size;
        linkYarnClone.modCount = this.modCount;
        Node currentNode = this.head;
        while (currentNode != null) {
        	linkYarnClone.addNewNode(currentNode.text, currentNode.count);
        	currentNode = currentNode.next;
        }
        return linkYarnClone;
    }
    
    public void swap (LinkedYarn other) {
        other.modCount++;
        this.modCount++;
        Node originalHead = this.head;
        int originalSize = this.size;
        int originalUniqueSize = this.uniqueSize;
        
        this.size = other.size;
        this.uniqueSize = other.uniqueSize;
        other.size = originalSize;
        other.uniqueSize = originalUniqueSize;
        this.head = other.head;
        other.head = originalHead;
    }
    
    public LinkedYarn.Iterator getIterator () {
    	if (isEmpty()) {throw new IllegalStateException();}
        return new Iterator(this);
    }
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static LinkedYarn knit (LinkedYarn y1, LinkedYarn y2) {
    	if (y1.isEmpty()) {return y2.clone();}
    	if (y2.isEmpty()) {return y1.clone();}
    	LinkedYarn y3 = y2.clone();
    	LinkedYarn.Iterator it = y1.getIterator();
        for (int i = 0; i < y1.getSize(); i++) {
        	y3.insert(it.getString());
        	if (it.hasNext()) {
        		it.next();
        	}
        }
        return y3;
    }
    
    public static LinkedYarn tear (LinkedYarn y1, LinkedYarn y2) {
    	if (y2.isEmpty()) {return y1.clone();}
    	LinkedYarn y3 = y1.clone();
    	LinkedYarn.Iterator it = y2.getIterator();
        for (int i = 0; i < y2.getSize(); i++) {
        	if (y3.contains(it.getString())) {
        		y3.remove(it.getString());
        	}
        	if (it.hasNext()) {
        		it.next();
        	}
        }
    	return y3;
    }
    
    public static boolean sameYarn (LinkedYarn y1, LinkedYarn y2) {
        return LinkedYarn.tear(y1, y2).isEmpty() && LinkedYarn.tear(y2, y1).isEmpty();//Thanks professor Forney!
    }
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    
    //Adds a new node to the LinkedYarn and updates all the next and prev values appropriately.
    private void addNewNode (String t, int c) {
    	Node newNode = new Node(t, c);
    	if (!this.isEmpty()) {
    		Node originalHead = this.head;
    		newNode.next = originalHead;
    		originalHead.prev = newNode;
    	}
    	this.head = newNode;
    	this.uniqueSize++;
    }
    
    //Searches the Linked Yarn for a given string, returning the node with the string or null otherwise.
    private Node findFirstNodeWith(String toFind) {
    	if (isEmpty()) {return null;}
    	Node currentNode = this.head;//I am using a node by node approach to improve performance.
        while (currentNode != null) {
        	if (currentNode.text.equals(toFind)) {
        		return currentNode;
        	}
        	currentNode = currentNode.next;
        }
    	return null;
    }
    
    //Destroys given node by removing all references to it and fixing any gaps left behind.
    private void destroyNode(Node toDestroy) {
        uniqueSize--;
    	Node previousNode = toDestroy.prev;
    	Node nextNode = toDestroy.next;
    	if (uniqueSize == 0) {//toDestroy is only item
    		head = null;
    		return;
    	}
    	if (toDestroy == this.head) {//toDestroy is first item
    		this.head = nextNode;
    		nextNode.prev = null;
    	} else if (nextNode == null) {//toDestroy is last item
    		previousNode.next = null;
    	} else {//toDestroy is in the middle of two nodes
    		previousNode.next = nextNode;
    		nextNode.prev = previousNode;
    	}
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
            current = owner.head;
            itModCount = owner.modCount;
            currCount = 1;
        }
        
        public boolean hasNext () {
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
            } else {
            	current = current.next;
            	currCount = 1;
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