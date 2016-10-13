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
        return size;
    }
    
    public int getUniqueSize () {
        return uniqueSize;
    }
    
    public void insert (String toAdd) {
    	Node nodeToAddTo = findFirstNodeWith(toAdd);
    	if (nodeToAddTo != null) {
    		nodeToAddTo.count++;
    	} else {
    		this.addNewNode(toAdd, 1);
        	uniqueSize++;
    	}
		size++;
        modCount++;
    }
    
    public int remove (String toRemove) {
    	Node nodeToRemoveFrom = this.findFirstNodeWith(toRemove);
    	if (nodeToRemoveFrom != null) {
    		this.modCount++;
    		//System.out.println(nodeToRemoveFrom.count);
    		nodeToRemoveFrom.count--;
    		this.size--;
    		if (nodeToRemoveFrom.count == 0) {
    			this.destroyNode(nodeToRemoveFrom);
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
    	return (this.findFirstNodeWith(toCheck) != null);
    }
    
    public String getMostCommon () {
    	if (isEmpty()) {return null;}
    	String mostCommon = null;
    	int mostCommonCount = 0;
        Iterator it = getIterator();
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
        LinkedYarn linkYarnClone = new LinkedYarn();
        linkYarnClone.size = this.size;
        linkYarnClone.uniqueSize = this.uniqueSize;
        linkYarnClone.modCount = this.modCount;
        Node currentNode = this.head;
        for (int i = 0; i < this.uniqueSize; i++) {
        	//System.out.println("Creating new node with: " + it.getString() + " " + it.current.count);
        	linkYarnClone.addNewNode(currentNode.text, currentNode.count);
        	if (currentNode.next != null) {
        		currentNode = currentNode.next;
        	}
        }
        return linkYarnClone;
    }
    
    public void swap (LinkedYarn other) {
        other.modCount++;
        modCount++;
        //System.out.println("other: " + other.head);
       // System.out.println("this: " + head);
        Node originalHead = this.head;
        this.head = other.head;
        other.head = originalHead;
        //System.out.println("other: " + other.head);
        //System.out.println("this: " + head);
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
    	if (y1.size != y2.size || y1.uniqueSize != y2.uniqueSize) {return false;}
        LinkedYarn.Iterator it = y1.getIterator();
        for (int i = 0; i < y1.getSize(); i++) {
        	if (!y2.contains(it.getString()) || y2.count(it.getString()) != it.current.count) {
        		return false;
        	}
        	if (it.hasNext()) {
        		it.next();
        	}
        }
        return true;
    }
    
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    
    //Adds a new node to the given LinkedYarn and updates all the next and prev values appropriately.
    private void addNewNode (String t, int c) {
    	Node n = new Node(t, c);
    	//System.out.println("Created node with; " + n.text + " " + n.count)
    	if (!this.isEmpty()) {
    		//System.out.println("N is : " + n);
    		//System.out.println("Yarn head is: " + yarnToAddTo.head);
    		//System.out.println("Yarn.head is: " + yarnToAddTo.head);
    		n.next = this.head;
    		this.head.prev = n;
    		//System.out.println("Yarn.head.prev is: " + yarnToAddTo.head.prev);
    		//System.out.println("Swapping positions of n and another node.");
    	}
    	this.head = n;
    	
    	//System.out.println("N is: " + n);
    	//System.out.println("head is: " + yarnToAddTo.head);
    }
    
    //Searches this linked list for a given string, returning the node with the string or null otherwise.
    private Node findFirstNodeWith(String toFind) {
    	if (this.isEmpty()) {return null;}
    	Node currentNode = this.head;
        while (currentNode != null) {
        	//System.out.println("    " + currentNode.text + "   " + toFind);
        	if (currentNode.text.equals(toFind)) {
        		return currentNode;
        	} else {//iterate to next node
        		currentNode = currentNode.next;
        	}
        }
    	return null;
    }
    
    //Destroys given node by removing all references to it and fixing any gaps left behind.
    private void destroyNode(Node toDestroy) {
        this.uniqueSize--;
    	Node previousNode = toDestroy.prev;
    	Node nextNode = toDestroy.next;
    	if (uniqueSize == 0) {
    		head = null;
    		return;
    	}
    	if (toDestroy == this.head) {//toDestroy is first item
    		head = nextNode;
    		nextNode.prev = null;
    	} else if (nextNode == null) {//toDestroy is last item
    		previousNode.next = null;
    	} else {//toDestroy is in the middle of two nodes
    		previousNode.next = nextNode;
    		nextNode.prev = previousNode;
    	}
    }
    
    /*
     * TODO: DELETE DELETE DELETE
     */
    public String toString() {
    	if (isEmpty()) {
    		return "EMPTY LY";
    	}
        Node currentNode = this.head;
        System.out.println(uniqueSize);
        for (int i = 0; i < this.uniqueSize; i++) {
        	System.out.println("Node: " + i + " Text: " + currentNode.text + " Count: " + currentNode.count);
        	if (currentNode.next != null) {
        		currentNode = currentNode.next;
        	}
        }
    	return "";
    }
    /*
     * DELETE DELETE DELETE
     */
    
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
        	//System.out.println("Current count: " + currCount + " Count: " + current.count + " Next: " + current.next + " IsValid: " + isValid());
            return (currCount < current.count || current.next != null) && isValid();
        }
        
        public boolean hasPrev () {//TODO: fix
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
            	//System.out.println("    " + current.count);
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