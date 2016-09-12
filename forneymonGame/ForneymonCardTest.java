package forneymonGame;

import static org.junit.Assert.*;

import org.junit.Test;

public class ForneymonCardTest {
	
	@Test
	public void generalTest () {
        FlippingForneymonCard burny = new FlippingForneymonCard("burny", "Burnymon", false);
        assertEquals(burny.toString(), "Burnymon: burny");
        burny.flip();
        assertEquals(burny.toString(), "?: ?");
        //"?: ?"
        System.out.println(burny);
    }
}
