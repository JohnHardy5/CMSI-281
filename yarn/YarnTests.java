package yarn;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 *Completed by John Hardy
 */

public class YarnTests {

    @Test
    public void testYarn() {
        Yarn ball = new Yarn();
        assertEquals(0, ball.count("hello"));
    }

    @Test
    public void testIsEmpty() {
        Yarn ball = new Yarn();
        assertTrue(ball.isEmpty());
        ball.insert("not_empty");
        assertFalse(ball.isEmpty());
        
        ball.remove("not_empty");
        assertTrue(ball.isEmpty());
    }

    @Test
    public void testGetSize() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.getSize(), 2);
        ball.insert("unique");
        assertEquals(ball.getSize(), 3);
        
        ball.insert("immature_noises");
        ball.insert("lewd_sounds");
        ball.remove("dup");
        assertEquals(4, ball.getSize());
        
        Yarn bigBall = new Yarn();
        for (int i = 0; i < 100; i++) {
        	bigBall.insert("im_a_string!");
        }
        assertEquals(100, bigBall.getSize());
        bigBall.insert("im_also_a_string!!");
        assertEquals(101, bigBall.getSize());
    }

    @Test
    public void testGetUniqueSize() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.getUniqueSize(), 1);
        ball.insert("unique");
        assertEquals(ball.getUniqueSize(), 2);
        
        ball.insert("dup");
        ball.remove("unique");
        assertEquals(1, ball.getUniqueSize());
        
        Yarn ballsOfSteel = new Yarn();
        String stringy = "b";
        for (int i = 0; i < 100; i++) {
        	ballsOfSteel.insert(stringy + Integer.toString(i));
        }
        assertEquals(100, ballsOfSteel.getUniqueSize());
        ballsOfSteel.insert("i_wont_get_inserted_:(");
        assertEquals(100, ballsOfSteel.getUniqueSize());
    }

    @Test
    public void testInsert() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        
        Yarn specialYarn = ball.clone();
        specialYarn.insert("im_special!");
        assertTrue(specialYarn.contains("im_special!"));
        
        Yarn meanYarn = Yarn.tear(specialYarn, ball);
        assertTrue(meanYarn.contains("im_special!"));
        assertFalse(meanYarn.contains("unique"));
    }

    @Test
    public void testRemove() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.getSize(), 2);
        assertEquals(ball.getUniqueSize(), 1);
        ball.remove("dup");
        assertEquals(ball.getSize(), 1);
        assertEquals(ball.getUniqueSize(), 1);
        
        Yarn betterThanFornsYarn = ball.clone();
        betterThanFornsYarn.remove("dup");
        assertEquals(0, betterThanFornsYarn.getSize());
        assertEquals(0, betterThanFornsYarn.getUniqueSize());
    }

    @Test
    public void testRemoveAll() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(ball.getSize(), 3);
        assertEquals(ball.getUniqueSize(), 2);
        ball.removeAll("dup");
        assertEquals(ball.getSize(), 1);
        assertEquals(ball.getUniqueSize(), 1);
        
        Yarn iHateYarns = new Yarn();
        iHateYarns.removeAll("");
        assertEquals(0, iHateYarns.getSize());
        assertEquals(0, iHateYarns.getUniqueSize());
    }

    @Test
    public void testCount() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(ball.count("dup"), 2);
        assertEquals(ball.count("unique"), 1);
        assertEquals(ball.count("forneymon"), 0);
        
        Yarn itIsTwelveAm = new Yarn();
        String lateString = "why_Did_I_Procrastinate?";
        for (int i = 0; i < 100; i++) {
        	itIsTwelveAm.insert(lateString + Integer.toString(i));
        }
        assertEquals(1, itIsTwelveAm.count("why_Did_I_Procrastinate?0"));
        itIsTwelveAm.insert("i_Hate_Myself");
        assertEquals(0, itIsTwelveAm.count("i_Hate_Myself"));
    }

    @Test
    public void testContains() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        assertFalse(ball.contains("forneymon"));
        
        Yarn crazyYarn = new Yarn();
        String crazyString = "alkjsdlgkhapksdjgh";
        for (int i = 0; i < 100; i++) {
        	crazyYarn.insert(crazyString + Integer.toString(i));
        }
        assertTrue(crazyYarn.contains("alkjsdlgkhapksdjgh0"));
        crazyYarn.insert("asldfh");
        assertFalse(crazyYarn.contains("asldfh"));
    }

    @Test
    public void testGetNth() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        Yarn comparison = ball.clone();
        for (int i = 0; i < ball.getSize(); i++) {
            String gotten = ball.getNth(i);
            assertTrue(comparison.contains(gotten));
            comparison.remove(gotten);
        }
        
        Yarn coolYarn = new Yarn();
        String chilly = "stay_Frosty";
        for (int i = 0; i < 100; i++) {
        	coolYarn.insert(chilly + Integer.toString(i));
        }
        coolYarn.insert(chilly + "0");
        coolYarn.insert(chilly + "0");
        coolYarn.insert(chilly + "0");
        coolYarn.insert(chilly + "1");
        coolYarn.insert(chilly + "1");
        coolYarn.insert(chilly + "2");
        coolYarn.insert(chilly + "99");
        assertEquals("stay_Frosty98", coolYarn.getNth(104));
        assertEquals("stay_Frosty0", coolYarn.getNth(3));
        assertEquals("", coolYarn.getNth(107));
        /*
         * Note: I know that the order doesn't matter with sets, I just did this to make sure my getNth is working.
         */
    }

    @Test
    public void testGetMostCommon() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        assertEquals(ball.getMostCommon(), "dup");
        ball.insert("cool");
        String mc = ball.getMostCommon();
        assertTrue(mc.equals("dup") || mc.equals("cool"));
        
        Yarn emptyYarn = new Yarn();
        assertEquals(null, emptyYarn.getMostCommon());
    }

    @Test
    public void testClone() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        Yarn dolly = ball.clone();
        assertEquals(dolly.count("dup"), 2);
        assertEquals(dolly.count("unique"), 1);
        dolly.insert("cool");
        assertFalse(ball.contains("cool"));
        
        Yarn yingYangYarn = Yarn.knit(Yarn.tear(dolly.clone(), ball.clone()), ball.clone());
        assertTrue(yingYangYarn.contains("cool"));
        assertEquals(2, yingYangYarn.count("dup"));
    }

    @Test
    public void testSwap() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("yo");
        y2.insert("sup");
        y1.swap(y2);
        assertTrue(y1.contains("yo"));
        assertTrue(y1.contains("sup"));
        assertTrue(y2.contains("dup"));
        assertTrue(y2.contains("unique"));
        assertFalse(y1.contains("dup"));
        
        Yarn copy = y1.clone();
        Yarn copyCopy = copy.clone();
        Yarn notCopy = y2.clone();
        copy.swap(copyCopy);
        assertTrue(copyCopy.contains("yo"));
        assertTrue(copy.contains("sup"));
        assertFalse(notCopy.contains("yo"));
        notCopy.swap(copyCopy);
        assertFalse(copyCopy.contains("yo"));
    }

    @Test
    public void testKnit() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("dup");
        y2.insert("cool");
        Yarn y3 = Yarn.knit(y1, y2);
        assertEquals(y3.count("dup"), 3);
        assertEquals(y3.count("unique"), 1);
        assertEquals(y3.count("cool"), 1);
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
        
        Yarn minecraft = new Yarn();
        minecraft.insert("creeper");
        minecraft.insert("steve");
        minecraft.insert("enderman");
        
        Yarn unturned = new Yarn();
        unturned.insert("maplestrike");
        unturned.insert("bigJ");
        unturned.insert("matamorez");
        
        Yarn mineturned = Yarn.knit(minecraft, unturned);
        assertTrue(mineturned.contains("bigJ"));
        minecraft.insert("zombie");
        assertFalse(mineturned.contains("zombie"));
    }

    @Test
    public void testTear() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("dup");
        y2.insert("cool");
        Yarn y3 = Yarn.tear(y1, y2);
        assertEquals(y3.count("dup"), 1);
        assertEquals(y3.count("unique"), 1);
        assertFalse(y3.contains("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
        
        Yarn differentYarn = new Yarn();
        differentYarn.insert("different");
        differentYarn.insert("difference");
        
        Yarn sameYarn = new Yarn();
        sameYarn.insert("different");
        sameYarn.insert("difference");
        
        Yarn tornYarn = Yarn.tear(differentYarn, sameYarn);
        assertFalse(tornYarn.contains("different"));
        assertFalse(tornYarn.contains("difference"));
        
        
        Yarn catchTwentyTwo = new Yarn();
        catchTwentyTwo.insert("Yossarian");
        catchTwentyTwo.insert("Orr");
        catchTwentyTwo.insert("MiloMinderbinder");
        
        Yarn lordOfTheFlies = new Yarn();
        lordOfTheFlies.insert("Jack");
        lordOfTheFlies.insert("Piggy");
        lordOfTheFlies.insert("Ralph");
        
        Yarn catchTheFlies = Yarn.knit(catchTwentyTwo, lordOfTheFlies);
        catchTheFlies = Yarn.tear(catchTheFlies, catchTwentyTwo);
        assertFalse(catchTheFlies.contains("Orr"));
        catchTwentyTwo.insert("DocDaneeka");
        assertFalse(catchTheFlies.contains("DocDaneeka"));
    }

    @Test
    public void testSameYarn() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("unique");
        y2.insert("dup");
        y2.insert("dup");
        assertTrue(Yarn.sameYarn(y1, y2));
        assertTrue(Yarn.sameYarn(y2, y1));
        y2.insert("test");
        assertFalse(Yarn.sameYarn(y1, y2));
        
        Yarn sameYarn = new Yarn();
        Yarn sameSameYarn = new Yarn();
        String sameString = "same";
        String sameSameString = "same";
        for (int i = 0; i < 100; i++) {
        	sameYarn.insert(sameString + Integer.toString(i));
        	sameSameYarn.insert(sameSameString + Integer.toString(i));
        }
        assertTrue(Yarn.sameYarn(sameYarn, sameSameYarn));
        sameSameYarn.insert("sameSame");
        assertTrue(Yarn.sameYarn(sameYarn, sameSameYarn));
        sameSameYarn.insert("same0");
        assertFalse(Yarn.sameYarn(sameYarn, sameSameYarn));
    }
    
    /*
     * ------------------------
     * BUGS (solved that is)
     * ------------------------
     * 
     * The original getSize() and getUniqueSize() tests helped me fix my helper method destroyObject:
     * -I did not decrement the uniqueSize and size appropriately when deleting an entry.
     * 
     * The original getNth tests helped me find 2 bugs in my getNth method and 1 in my constructor:
     * -I did not iterate through the items properly.
     * -I had originally started the iterator at 0.
     * -I accidentally made two Entry[] called items instead of one (one in constructor and one 
     * declared outside the constructor in private fields).
     * 
     * My knit and tear static methods did not work until I got my getNth to function:
     * -It was a super tricky bug that was traced down to me mismatching i and iterator (took me 45 minutes to find).
     * 
     * There were many other minor bugs like misspellings and mistypes etc., but they are too insignificant
     * to include here.
     */

}
