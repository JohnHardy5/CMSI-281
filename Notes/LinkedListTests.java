package Notes;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListTests {

	@Test
	public void test() {
		LinkedList linkList1 = new LinkedList();
		for (int i = 10; i >=0; i--) {
			linkList1.prepend(i);
		}
		LinkedList.Iterator iterator = linkList1.getIteratorAt(0);
		
	}

}
