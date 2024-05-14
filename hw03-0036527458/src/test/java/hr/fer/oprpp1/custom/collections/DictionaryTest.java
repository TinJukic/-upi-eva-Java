package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void creatingDictionarySizeTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		assertEquals(0, dictionary.size());
	}
	
	@Test
	void creatingDictionaryIsEmptyTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		assertEquals(true, dictionary.isEmpty());
	}
	
	@Test
	void creatingDictionaryNotEmptyTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(0, "prvi");
		assertEquals(false, dictionary.isEmpty());
	}
	
	@Test
	void addingElementToDictionarySizeTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(0, "prvi");
		assertEquals(1, dictionary.size());
	}
	
	@Test
	void addingElementToDictionaryValueTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(0, "prvi");
		assertEquals("prvi", dictionary.get(0));
	}
	
	@Test
	void nullKeyTest() {
		try {
			Dictionary<Integer, String> dictionary = new Dictionary<>();
			dictionary.put(null, "prvi");
			fail();
		} catch(NullPointerException e) { /* exception expected */ }
	}
	
	@Test
	void nullElementTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(0, null);
		assertEquals(null, dictionary.get(0));
	}

	@Test
	void changinElementValueTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(0, "prvi");
		assertEquals("prvi", dictionary.get(0));
		dictionary.put(0, "nulti");
		assertEquals(1, dictionary.size());
		assertEquals("nulti", dictionary.get(0));
	}
	
	@Test
	void addingFiveElementsTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(0, "nulti");
		dictionary.put(1, "prvi");
		dictionary.put(2, "prvi");
		dictionary.put(3, "treci");
		dictionary.put(4, "cetvrti");
		dictionary.put(2, "drugi");
		assertEquals(5, dictionary.size());
		assertEquals("drugi", dictionary.get(2));
	}
	
	@Test
	void removingAllElementsTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(0, "nulti");
		dictionary.put(1, "prvi");
		dictionary.put(2, "drugi");
		dictionary.put(3, "treci");
		dictionary.put(4, "cetvrti");
		assertEquals(5, dictionary.size());
		dictionary.clear();
		assertEquals(0, dictionary.size());
	}
	
	@Test
	void removingOneElementTest() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(0, "nulti");
		dictionary.put(1, "prvi");
		dictionary.put(2, "drugi");
		dictionary.put(3, "treci");
		dictionary.put(4, "cetvrti");
		assertEquals(5, dictionary.size());
		assertEquals("cetvrti", dictionary.remove(4));
		assertEquals(4, dictionary.size());
	}


}
