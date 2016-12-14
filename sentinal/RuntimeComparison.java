package sentinal;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.LinkedList;
import static org.junit.Assert.*;
import org.junit.Test;

public class RuntimeComparison {

	private final static int TEST_COUNT = 10000;
	
	@Test
	public void testHashTable() {
		Hashtable<Integer, Integer> l = new Hashtable<Integer, Integer>();
		for (int i = 0; i < TEST_COUNT; i++) {
			l.put(i, i);
		}
		System.out.println("Beginning Hashtable test.");
		for (int j = 0; j < TEST_COUNT; j++) {
			System.out.println(l.get(j));
		}
	}
	
	@Test
	public void testPrependLinkedList() {
		LinkedList<Integer> l = new LinkedList<Integer>();
		for (int i = 0; i < TEST_COUNT; i++) {
			l.push(i);
		}
		System.out.println("Beginning LinkedList prepend test.");
		for (int j = 0; j < TEST_COUNT; j++) {
			System.out.println(l.get(j));
		}
	}

	@Test
	public void testAppendLinkedList() {
		LinkedList<Integer> l = new LinkedList<Integer>();
		for (int i = 0; i < TEST_COUNT; i++) {
			l.add(i);
		}
		System.out.println("Beginning LinkedList append test.");
		for (int j = 0; j < TEST_COUNT; j++) {
			System.out.println(l.get(j));
		}
	}
	
	@Test
	public void testPrependArrayList() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < TEST_COUNT; i++) {
			a.add(0, i);
		}
		System.out.println("Beginning LinkedList prepend test.");
		for (int j = 0; j < TEST_COUNT; j++) {
			System.out.println(a.get(j));
		}
	}
	
	@Test
	public void testAppendArrayList() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < TEST_COUNT; i++) {
			a.add(i);
		}
		System.out.println("Beginning LinkedList append test.");
		for (int j = 0; j < TEST_COUNT; j++) {
			System.out.println(a.get(j));
		}
	}
}
