package Notes;

public class intList {
	private int size;
	private int[] items;
	int START_SIZE = 1;
	
	//constructor
	intList () {
		items = new int[START_SIZE];
		size = 0;
	}
	
	//methods
	public void append (int item) {
		items[size] = item;
		size++;
		checkAndGrow();
	}
	
	public int getAt (int index) {
		if (index >= size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return items[index];
	}
	
	public void removeAt (int index) {
		shiftLeft(index);
		size--;
	}
	
	/*
	 * Shift all elements to the the right of an index one left
	 */
	private void shiftLeft (int index) {
		for (int i = index; i < size - 1; i++) {
			items[i] = items[i + 1];
		}
	}
	
	/*
	 * Expands the size of the list whenever it is at capacity
	 */
	private void checkAndGrow () {
		//case: not at capacity, so do nothing
		if (size < items.length) {
			return;
		}
		
		//case: we're at capacity and need to grow
		int[] newItems = new int[items.length * 2];
		
		for (int i = 0; i < items.length; i++) {
			newItems[i] = items[i];
		}
		
		items = newItems;
	}
}
