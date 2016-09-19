package intlist;
/*
 * Written by John Hardy
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class IntListTests {

	@Test
	public void generalTests() {
		IntList inty = new IntList();
		for (int i = 0; i <= 10; i++) {
			inty.append(i);
		}
		assertEquals(inty.getAt(2), 2);
		
		inty.insertAt(35, 3);
		assertEquals(inty.getAt(3), 35);
		assertEquals(inty.getAt(11), 10);
		
		inty.removeAt(3);
		assertEquals(inty.getAt(3), 3);
		
		inty.prepend(-1);
		assertEquals(inty.getAt(1), 0);
	}

}
