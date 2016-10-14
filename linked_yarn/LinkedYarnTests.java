package linked_yarn;

import static org.junit.Assert.*;
/*
 * Completed by John Hardy
 */

import org.junit.*;
import org.junit.rules.Timeout;

public class LinkedYarnTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);
    
    // Used as the basic empty LinkedYarn to test; the @Before
    // method is run before every @Test
    LinkedYarn ball;
    @Before
    public void init () {
        ball = new LinkedYarn();
    }
    
    
    // =================================================
    // Unit Tests
    // =================================================
    
    // Initialization Tests
    // -------------------------------------------------
    @Test
    public void testInit() {
        assertTrue(ball.isEmpty());
        assertEquals(0, ball.getSize());
        assertEquals(0, ball.count("hello"));
        assertFalse(ball.contains(""));
    }

    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testIsEmpty() {
        assertTrue(ball.isEmpty());
        ball.insert("not_empty");
        assertFalse(ball.isEmpty());
        
        ball.remove("not_empty");
        assertTrue(ball.isEmpty());
        
        ball.insert("not_empty");
        ball.insert("not_empty");
        ball.insert("not_empty");
        ball.removeAll("not_empty");
        assertTrue(ball.isEmpty());
    }

    @Test
    public void testGetSize() {
    	assertEquals(0, ball.getSize());
        ball.insert("dup");
    	assertEquals(1, ball.getSize());
        ball.insert("dup");
        assertEquals(2, ball.getSize());
        ball.insert("unique");
        assertEquals(3, ball.getSize());

        ball.insert("immature_noises");
        ball.insert("lewd_sounds");
        ball.remove("dup");
        assertEquals(4, ball.getSize());
        
        LinkedYarn bigBall = new LinkedYarn();
        for (int i = 0; i < 100; i++) {
        	bigBall.insert("im_a_string!");
        }
        assertEquals(100, bigBall.getSize());
        bigBall.insert("im_also_a_string!!");
        assertEquals(101, bigBall.getSize());
    }

    @Test
    public void testGetUniqueSize() {
    	assertEquals(0, ball.getUniqueSize());
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(1, ball.getUniqueSize());
        ball.insert("unique");
        assertEquals(2, ball.getUniqueSize());

        ball.insert("dup");
        ball.remove("unique");
        assertEquals(1, ball.getUniqueSize());
        
        LinkedYarn ballsOfSteel = new LinkedYarn();
        String stringy = "b";
        for (int i = 0; i < 100; i++) {
        	ballsOfSteel.insert(stringy + Integer.toString(i));
        }
        assertEquals(100, ballsOfSteel.getUniqueSize());
        ballsOfSteel.insert("i_will_get_inserted");
        assertEquals(101, ballsOfSteel.getUniqueSize());
    }

    // LinkedYarn Manipulation Tests
    // -------------------------------------------------
    @Test
    public void testInsert() {
    	assertFalse(ball.contains("dup"));
    	
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        
        assertEquals(2, ball.count("dup"));
        assertFalse(ball.contains(null));
        
        LinkedYarn specialYarn = ball.clone();
        specialYarn.insert("im_special!");
        assertTrue(specialYarn.contains("im_special!"));
        
        LinkedYarn meanYarn = LinkedYarn.tear(specialYarn, ball);
        assertTrue(meanYarn.contains("im_special!"));
        assertFalse(meanYarn.contains("unique"));
        
        LinkedYarn bigBall = new LinkedYarn();
        for (int i = 0; i < 100; i++) {
        	bigBall.insert("hello");
        }
        assertEquals(100, bigBall.getSize());
        assertEquals(1, bigBall.getUniqueSize());
    }

    @Test
    public void testRemove() {
    	assertEquals(0, ball.remove("dup"));
    	
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(2, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        int dups = ball.remove("dup");
        assertEquals(1, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        assertEquals(1, dups);
        
        LinkedYarn betterThanFornsYarn = ball.clone();
        betterThanFornsYarn.remove("dup");
        assertEquals(0, betterThanFornsYarn.getSize());
        assertEquals(0, betterThanFornsYarn.getUniqueSize());
    }

    @Test
    public void testRemoveAll() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(3, ball.getSize());
        assertEquals(2, ball.getUniqueSize());
        ball.removeAll("dup");
        assertEquals(1, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        
        LinkedYarn iHateYarns = new LinkedYarn();
        iHateYarns.removeAll("");
        assertEquals(0, iHateYarns.getSize());
        assertEquals(0, iHateYarns.getUniqueSize());
        
        for (int i = 0; i < 100; i++) {
        	iHateYarns.insert("I_HATE_LINKED_YARNS");
        }
        iHateYarns.removeAll("I_HATE_LINKED_YARNS");
        assertEquals(0, iHateYarns.getSize());
        assertEquals(0, iHateYarns.getUniqueSize());
    }

    @Test
    public void testCount() {
    	assertEquals(0, ball.count("dup"));
    	
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(2, ball.count("dup"));
        assertEquals(1, ball.count("unique"));
        assertEquals(0, ball.count("forneymon"));
        
        LinkedYarn itIsTwelveAm = new LinkedYarn();
        String lateString = "why_Did_I_Procrastinate?";
        for (int i = 0; i < 100; i++) {
        	itIsTwelveAm.insert(lateString + Integer.toString(i));
        }
        assertEquals(1, itIsTwelveAm.count("why_Did_I_Procrastinate?0"));
        itIsTwelveAm.insert("i_Hate_Myself");
        assertEquals(1, itIsTwelveAm.count("i_Hate_Myself"));
    }

    @Test
    public void testContains() {
    	assertFalse(ball.contains("dup"));
        assertFalse(ball.contains(null));
    	
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        assertFalse(ball.contains("forneymon"));
        
        LinkedYarn crazyYarn = new LinkedYarn();
        String crazyString = "alkjsdlgkhapksdjgh";
        for (int i = 0; i < 100; i++) {
        	crazyYarn.insert(crazyString + Integer.toString(i));
        }
        assertTrue(crazyYarn.contains("alkjsdlgkhapksdjgh0"));
        crazyYarn.insert("asldfh");
        assertTrue(crazyYarn.contains("asldfh"));
    }
    // This is tested pretty much everywhere so...


    @Test
    public void testGetMostCommon() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        assertEquals("dup", ball.getMostCommon());
        ball.insert("cool");
        String mc = ball.getMostCommon();
        assertTrue(mc.equals("dup") || mc.equals("cool"));

        LinkedYarn emptyYarn = new LinkedYarn();
        assertEquals(null, emptyYarn.getMostCommon());

        LinkedYarn bigBall = new LinkedYarn();
        for (int i = 0; i < 50; i++) {
        	bigBall.insert("hello");
        	if (i < 25) {
        		bigBall.insert("hello!");
        	}
        }
        bigBall.insert("hi");
        assertEquals("hello", bigBall.getMostCommon());
    }

    // Iterator Tests
    // -------------------------------------------------
    @Test
    public void testIteratorBasics() {
        ball.insert("a");
        ball.insert("a");
        ball.insert("a");
        ball.insert("b");
        LinkedYarn.Iterator it = ball.getIterator();

        // Test next()
        LinkedYarn dolly = ball.clone();
        while (true) {
            String gotten = it.getString();
            assertTrue(dolly.contains(gotten));
            dolly.remove(gotten);
            if (it.hasNext()) {
            	it.next();
            } else {
            	break;
            }
        }
        assertTrue(dolly.isEmpty());
        assertFalse(it.hasNext());
        assertEquals(2, ball.getUniqueSize());
        
        // Test prev()
        dolly = ball.clone();
        while (true) {
            String gotten = it.getString();
            assertTrue(dolly.contains(gotten));
            dolly.remove(gotten);
            if (it.hasPrev()) {
            	it.prev();
            } else {
            	break;
            }
        }
        assertTrue(dolly.isEmpty());
        assertFalse(it.hasPrev());
        assertEquals(2, ball.getUniqueSize());
        
        int countOfReplaced = ball.count(it.getString());
        it.replaceAll("replaced!");
        assertEquals(countOfReplaced, ball.count("replaced!"));
        assertTrue(it.isValid());
        
        ball.insert("c");
        assertFalse(it.isValid());
    }
    
    // Inter-LinkedYarn Tests
    // -------------------------------------------------
    @Test
    public void testClone() {
    	LinkedYarn emptyYarn = ball.clone();
    	assertEquals(0, emptyYarn.getSize());
    	
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        LinkedYarn dolly = ball.clone();
        assertEquals(2, dolly.count("dup"));
        assertEquals(1, dolly.count("unique"));
        dolly.insert("cool");
        assertFalse(ball.contains("cool"));
        
        //This is designed to ensure that all three methods work, mainly clone.
        LinkedYarn yingYangYarn = LinkedYarn.knit(LinkedYarn.tear(dolly.clone(), ball.clone()), ball.clone());
        assertTrue(yingYangYarn.contains("cool"));
        assertEquals(2, yingYangYarn.count("dup"));
    }

    @Test
    public void testSwap() {
    	LinkedYarn emptyYarn = ball.clone();
    	ball.insert("dup");
    	emptyYarn.swap(ball);
    	assertEquals(0, ball.getSize());
    	assertEquals(1, emptyYarn.getSize());
    	
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("yo");
        y2.insert("sup");
        y1.swap(y2);
        assertTrue(y1.contains("yo"));
        assertTrue(y1.contains("sup"));
        assertTrue(y2.contains("dup"));
        assertTrue(y2.contains("unique"));
        assertFalse(y1.contains("dup"));

        LinkedYarn copy = y1.clone();
        LinkedYarn copyCopy = copy.clone();
        LinkedYarn notCopy = y2.clone();
        copy.swap(copyCopy);
        assertTrue(copyCopy.contains("yo"));
        assertTrue(copy.contains("sup"));
        assertFalse(notCopy.contains("yo"));
        notCopy.swap(copyCopy);
        assertFalse(copyCopy.contains("yo"));
        
        LinkedYarn bigBall = new LinkedYarn();
        for (int i = 0; i < 50; i++) {
        	bigBall.insert("hello");
        }
        bigBall.swap(ball);
        assertTrue(bigBall.isEmpty());
        assertEquals(50, ball.getSize());
    }

    // Static Method Tests
    // -------------------------------------------------
    @Test
    public void testKnit() {
    	LinkedYarn emptyYarn = new LinkedYarn();
    	LinkedYarn stillEmpty = LinkedYarn.knit(emptyYarn, ball);
    	assertEquals(0, stillEmpty.getSize());
    	
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("dup");
        y2.insert("cool");
        LinkedYarn y3 = LinkedYarn.knit(y1, y2);
        assertEquals(3, y3.count("dup"));
        assertEquals(1, y3.count("unique"));
        assertEquals(1, y3.count("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
        
        assertEquals(6, y3.getSize());
        
        LinkedYarn minecraft = new LinkedYarn();
        minecraft.insert("creeper");
        minecraft.insert("steve");
        minecraft.insert("enderman");
        
        LinkedYarn unturned = new LinkedYarn();
        unturned.insert("maplestrike");
        unturned.insert("bigJ");
        unturned.insert("matamorez");
        
        LinkedYarn mineturned = LinkedYarn.knit(minecraft, unturned);
        assertTrue(mineturned.contains("bigJ"));
        minecraft.insert("zombie");
        assertFalse(mineturned.contains("zombie"));
    }

    @Test
    public void testTear() {
    	LinkedYarn notEmptyYarn = new LinkedYarn();
    	notEmptyYarn.insert("not_empty");
    	LinkedYarn emptyYarn = LinkedYarn.tear(ball, notEmptyYarn);
    	assertTrue(emptyYarn.isEmpty());
    	assertEquals(0, emptyYarn.getSize());
    	
    	notEmptyYarn = LinkedYarn.tear(notEmptyYarn, emptyYarn);
    	assertFalse(notEmptyYarn.isEmpty());
    	assertEquals(1, notEmptyYarn.getSize());
    	
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("dup");
        y2.insert("cool");
        LinkedYarn y3 = LinkedYarn.tear(y1, y2);
        assertEquals(1, y3.count("dup"));
        assertEquals(1, y3.count("unique"));
        assertFalse(y3.contains("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
        
        assertEquals(3, y1.getSize());
        assertEquals(2, y2.getSize());
        
        LinkedYarn differentYarn = new LinkedYarn();
        differentYarn.insert("different");
        differentYarn.insert("difference");
        
        LinkedYarn sameYarn = new LinkedYarn();
        sameYarn.insert("different");
        sameYarn.insert("difference");
        
        LinkedYarn tornYarn = LinkedYarn.tear(differentYarn, sameYarn);
        assertFalse(tornYarn.contains("different"));
        assertFalse(tornYarn.contains("difference"));
        
        LinkedYarn catchTwentyTwo = new LinkedYarn();
        catchTwentyTwo.insert("Yossarian");
        catchTwentyTwo.insert("Orr");
        catchTwentyTwo.insert("MiloMinderbinder");
        
        LinkedYarn lordOfTheFlies = new LinkedYarn();
        lordOfTheFlies.insert("Jack");
        lordOfTheFlies.insert("Piggy");
        lordOfTheFlies.insert("Ralph");
        
        LinkedYarn catchTheFlies = LinkedYarn.knit(catchTwentyTwo, lordOfTheFlies);
        catchTheFlies = LinkedYarn.tear(catchTheFlies, catchTwentyTwo);
        assertFalse(catchTheFlies.contains("Orr"));
        catchTwentyTwo.insert("DocDaneeka");
        assertFalse(catchTheFlies.contains("DocDaneeka"));
    }

    @Test
    public void testSameYarn() {
    	LinkedYarn emptyYarn = new LinkedYarn();
    	assertTrue(LinkedYarn.sameYarn(emptyYarn, ball));
    	
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("unique");
        y2.insert("dup");
        y2.insert("dup");
        assertTrue(LinkedYarn.sameYarn(y1, y2));
        assertTrue(LinkedYarn.sameYarn(y2, y1));
        y2.insert("test");
        assertFalse(LinkedYarn.sameYarn(y1, y2));
        
        LinkedYarn sameLinkedYarn = new LinkedYarn();
        LinkedYarn sameSameYarn = new LinkedYarn();
        String sameString = "same";
        String sameSameString = "same";
        for (int i = 0; i < 100; i++) {
        	sameLinkedYarn.insert(sameString + Integer.toString(i));
        	sameSameYarn.insert(sameSameString + Integer.toString(i));
        }
        assertTrue(LinkedYarn.sameYarn(sameLinkedYarn, sameSameYarn));
        sameSameYarn.insert("sameSame");
        sameLinkedYarn.insert("sameSame");
        assertTrue(LinkedYarn.sameYarn(sameLinkedYarn, sameSameYarn));
        sameSameYarn.insert("same0");
        assertFalse(LinkedYarn.sameYarn(sameLinkedYarn, sameSameYarn));
    }
}
