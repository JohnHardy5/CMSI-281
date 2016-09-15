package forneymonGame;
/*
 * Written by John Hardy
 */
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
	
	@Test
	public void matchTest () {
		FlippingForneymonCard dampy = new FlippingForneymonCard("dampy", "Dampymon", true);
		FlippingForneymonCard leafy = new FlippingForneymonCard("leafy", "Leafymon", false);
		assertEquals(dampy.match(leafy), 2);
		dampy.flip();
		assertEquals(dampy.match(leafy), 0);
		
		FlippingForneymonCard dampy2 = new FlippingForneymonCard("dampy", "Dampymon", false);
		assertEquals(dampy.match(dampy2), 1);
		
		FlippingForneymonCard dampy3 = new FlippingForneymonCard("dimpy", "Dampymon", false);
		assertEquals(dampy.match(dampy3), 0);
	}
}
