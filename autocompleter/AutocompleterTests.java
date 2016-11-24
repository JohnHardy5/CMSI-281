package autocompleter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class AutocompleterTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);
    
    // Used as the basic empty Autocompleter to test; 
    // the @Before method is run before every @Test
    Autocompleter ac;
    @Before
    public void init () {
        ac = new Autocompleter();
    }
    
    
    // =================================================
    // Unit Tests
    // =================================================
    
    // Initialization Tests
    // -------------------------------------------------
    @Test
    public void testAutocompleter() {
        assertTrue(ac.isEmpty());
        ac.addTerm("is");
        assertFalse(ac.isEmpty());
    }

    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testAddTerm() {
    	try {ac.addTerm("");} catch (IllegalArgumentException e) {System.out.println("addTerm for empty string fails.");}
    	try {ac.addTerm(null);} catch (IllegalArgumentException e) {System.out.println("addTerm for null fails.");}
        assertTrue(ac.isEmpty());
        ac.addTerm("i");
        assertFalse(ac.isEmpty());
        ac.addTerm("is");
        ac.addTerm("it");
        ac.addTerm("it");
        ac.addTerm("as");
        ac.addTerm("ass");
        ac.addTerm("at");
        ac.addTerm("bat");
    }
    
    @Test
    public void testAddTermHasTerm() {
    	assertFalse(ac.hasTerm("empty"));
    	ac.addTerm("likely");
    	assertFalse(ac.hasTerm("lik"));
    	ac.addTerm("lit");
    	assertTrue(ac.hasTerm("lit"));
    	ac.addTerm("litter");
    	assertTrue(ac.hasTerm("litter"));
    	assertFalse(ac.hasTerm("litt"));
    	ac.addTerm("like");
    	assertTrue(ac.hasTerm("like"));
    }

    @Test
    public void testHasTerm() {
    	try {ac.hasTerm("");} catch (IllegalArgumentException e) {System.out.println("hasTerm for empty string fails.");}
    	try {ac.hasTerm(null);} catch (IllegalArgumentException e) {System.out.println("hasTerm for null fails.");}
    	assertFalse(ac.hasTerm("empty"));
    	assertTrue(ac.isEmpty());
        ac.addTerm("is");
        ac.addTerm("it");
        ac.addTerm("it");
        ac.addTerm("as");
        ac.addTerm("ass");
        ac.addTerm("at");
        ac.addTerm("bat");
        assertTrue(ac.hasTerm("is"));
        assertTrue(ac.hasTerm("it"));
        assertTrue(ac.hasTerm("as"));
        assertTrue(ac.hasTerm("ass"));
        assertTrue(ac.hasTerm("at"));
        assertTrue(ac.hasTerm("bat"));
        assertFalse(ac.hasTerm("ii"));
        assertFalse(ac.hasTerm("i"));
        assertFalse(ac.hasTerm("zoo"));
    	assertFalse(ac.isEmpty());
    }

    @Test
    public void getSuggestedTerm() {
    	try {ac.getSuggestedTerm("");} catch (IllegalArgumentException e) {System.out.println("getSuggested for empty string fails.");}
    	try {ac.getSuggestedTerm(null);} catch (IllegalArgumentException e) {System.out.println("getSuggested for null fails.");}
    	assertEquals(null, ac.getSuggestedTerm("empty"));
    	ac.addTerm("a");
    	assertEquals(null, ac.getSuggestedTerm("i"));
    	assertEquals("a", ac.getSuggestedTerm("a"));
        ac.addTerm("is");
        ac.addTerm("it");
        ac.addTerm("as");
        ac.addTerm("at");
        ac.addTerm("item");
        ac.addTerm("ass");
        ac.addTerm("bat");
        ac.addTerm("bother");
        ac.addTerm("goat");
        ac.addTerm("goad");
        assertEquals("is", ac.getSuggestedTerm("is"));
        ac.addTerm("it");
        assertEquals("is", ac.getSuggestedTerm("is"));
        assertEquals("it", ac.getSuggestedTerm("it"));
        assertEquals("item", ac.getSuggestedTerm("ite"));
        assertEquals("as", ac.getSuggestedTerm("as"));
        assertEquals("bat", ac.getSuggestedTerm("ba"));
        assertEquals("bother", ac.getSuggestedTerm("bo"));
        assertEquals(null, ac.getSuggestedTerm("bad"));
        assertEquals(null, ac.getSuggestedTerm("zoo"));
        String result = ac.getSuggestedTerm("go");
        assertTrue(result.equals("goat") || result.equals("goad"));
    }
    
    @Test
    public void getSortedTerms() {
    	assertEquals(new ArrayList<String>(), ac.getSortedTerms());
    	assertTrue(ac.isEmpty());
        ac.addTerm("is");
        ArrayList<String> oneWord = new ArrayList<String>(Arrays.asList("is"));
        assertEquals(oneWord, ac.getSortedTerms());
        ac.addTerm("it");
        ac.addTerm("as");
        ac.addTerm("itenerary");
        ac.addTerm("ass");
        ac.addTerm("at");
        ac.addTerm("zoo");
        ac.addTerm("bat");
        ac.addTerm("bother");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "as", "ass", "at", "bat", "bother", "is", "it", "itenerary", "zoo"
        ));
        assertEquals(solution, ac.getSortedTerms());
        ac.addTerm("bother");
        ac.addTerm("it");
        assertEquals(solution, ac.getSortedTerms());
    }
}