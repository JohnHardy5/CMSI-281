package Notes;

public class LinkedList {
	private Node head;
	private int size;
	
	LinkedList() {
		size = 0;
		head = null;
	}
	
	public int getSize() {
		return size;
	}
	
	public void prepend (int toAdd) {
		Node previousHead = head;
		head = new Node(toAdd);
		head.next = previousHead;
		size++;
	}
	
	/*
	 * Removes the element at the given index, and
	 * repairs references accordingly.
	 */
	public void removeAt (int index) {
		Node current = head,
				prev = null;
		//Step 1: find element to nuke.
		while (current != null && index > 0) {
			prev = current;
			current = current.next;
			index--;
		}
		//Step 2: update references.
		if (current == null) {return;}
		if (current == head) {
			head = current.next;
		}
		if (prev != null) {
			prev.next = current.next;
		}
		//Step 3: update fields (size).
		size--;
	}
	
	public Iterator getIteratorAt (int index) {
		Iterator it = new Iterator();
		while (index > 0) {
			it.next();
			index--;
		}
		return it;
	}
	
	private class Node {
		int data;
		Node next;
		
		Node (int d) {
			data = d;
			next = null;
			
		}
	}
	
	public class Iterator {
		Node current;
		
		Iterator() {
			current = head;//Remember: Nodes are made "on top of" current Linked List
		}
		
		public boolean hasNext() {
			return current != null && current.next != null;
		}
		
		public void next() {
			if (current == null) {return;}
			current = current.next;
		}
		
		public int getCurrentInt() {
			return current.data;
		}
	}
}
