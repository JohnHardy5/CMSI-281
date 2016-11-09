package tree.heap;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class BinaryHeapTests {

	@Test
	public void getSortedElementsTests() {
		BinaryHeap pile = new BinaryHeap();
		
		assertEquals(0, pile.getSortedElements().size());
		
		pile.insert(50);
		pile.insert(25);
		pile.insert(20);
		pile.insert(8);
		
		int[] arr = {8, 20, 25, 50};
		ArrayList<Integer> sorted = pile.getSortedElements();
		for (int i = 0; i < arr.length; i++) {
			int current = sorted.get(i);//assertEquals does not know how to discern between Integer and int
			assertEquals(arr[i], current);
		}
		
	}

}
