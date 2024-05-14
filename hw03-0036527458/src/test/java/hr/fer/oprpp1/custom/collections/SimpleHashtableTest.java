package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

	@Test
	void creatingSimpleHashtableDefaultConstructorTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>();
		assertEquals(SimpleHashtable.class, table.getClass());
	}
	
	@Test
	void creatingSimpleHashtableInitialCapacityConstructorTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		assertEquals(SimpleHashtable.class, table.getClass());
	}
	
	@Test
	void putOneElementTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		table.put(0, "nulti");
		assertEquals(1, table.size());
		assertEquals("nulti", table.get(0));
	}
	
	@Test
	void putFourElementsTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		assertEquals("nulti", table.get(0));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("treci", table.get(3));
	}
	
	@Test
	void overwriteElementValueTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		assertEquals("nulti", table.get(0));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("treci", table.get(3));
		table.put(3, "overwrite");
		assertEquals("overwrite", table.get(3));
		assertEquals(4, table.size());
	}
	
	@Test
	void containsKeyTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		assertEquals("nulti", table.get(0));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("treci", table.get(3));
		table.put(3, "overwrite");
		assertEquals("overwrite", table.get(3));
		assertEquals(4, table.size());
		assertEquals(true, table.containsKey(0));
		assertEquals(false, table.containsKey(5));
		assertEquals(false, table.containsKey(null));
	}
	
	@Test
	void containsValueTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		assertEquals("nulti", table.get(0));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("treci", table.get(3));
		table.put(3, "overwrite");
		assertEquals("overwrite", table.get(3));
		assertEquals(4, table.size());
		assertEquals(true, table.containsValue("nulti"));
		assertEquals(false, table.containsValue(""));
		assertEquals(false, table.containsKey(null));
	}
	
	@Test
	void addingNullKeyTest() {
		try {
			SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
			table.put(null, "nulti");
			fail();
		} catch(NullPointerException e) { /* exception is expected */ }
	}
	
	@Test
	void removeMiddleElementTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		table.remove(2);
		assertEquals(3, table.size());
		assertEquals(false, table.containsKey(2));
		assertEquals(false, table.containsValue("drugi"));
		assertEquals("nulti", table.get(0));
		assertEquals("prvi", table.get(1));
		assertEquals("treci", table.get(3));
	}
	
	@Test
	void removeFirstElementTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		table.remove(0);
		assertEquals(3, table.size());
		assertEquals(false, table.containsKey(0));
		assertEquals(false, table.containsValue("nulti"));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("treci", table.get(3));
	}
	
	@Test
	void removeLastElementTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		table.remove(3);
		assertEquals(3, table.size());
		assertEquals(false, table.containsKey(3));
		assertEquals(false, table.containsValue("treci"));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("nulti", table.get(0));
	}
	
	@Test
	void isEmptyTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(3);
		assertEquals(true, table.isEmpty());
		table.put(0, "element");
		assertEquals(false, table.isEmpty());
	}
	
	@Test
	void putFourElementsSizeTwoTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(2);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		assertEquals("nulti", table.get(0));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("treci", table.get(3));
	}
	
	@Test
	void toStringTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(2);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		assertEquals("nulti", table.get(0));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("treci", table.get(3));
		assertEquals("[0=nulti, 1=prvi, 2=drugi, 3=treci]", table.toString());
	}
	
	@Test
	void toArrayTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(2);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		
		var array = table.toArray();
		assertEquals(4, array.length);
		assertEquals(0, array[0].getKey());
		assertEquals("nulti", array[0].getValue());
		assertEquals(1, array[1].getKey());
		assertEquals("prvi", array[1].getValue());
		assertEquals(2, array[2].getKey());
		assertEquals("drugi", array[2].getValue());
		assertEquals(3, array[3].getKey());
		assertEquals("treci", array[3].getValue());
	}
	
	@Test
	void clearTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(2);
		table.put(0, "nulti");
		table.put(1, "prvi");
		table.put(2, "drugi");
		table.put(3, "treci");
		assertEquals(4, table.size());
		assertEquals("nulti", table.get(0));
		assertEquals("prvi", table.get(1));
		assertEquals("drugi", table.get(2));
		assertEquals("treci", table.get(3));
		table.clear();
		assertEquals(0, table.size());
		assertEquals(true, table.isEmpty());
	}

}
