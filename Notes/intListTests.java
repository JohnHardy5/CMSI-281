package Notes;

import static org.junit.Assert.*;

import org.junit.Test;

public class intListTests {

	@Test
	public void test() {
		intList inty = new intList();
		inty.append(2);
		inty.append(4);
		inty.append(8);
		assertEquals(inty.getAt(1), 4);
	}
}
