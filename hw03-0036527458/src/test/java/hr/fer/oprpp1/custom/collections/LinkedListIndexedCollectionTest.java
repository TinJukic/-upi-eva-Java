package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	@Test
	public void publicConstructorWithoutParametersForLinkedListIndexedCollection() {
		LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
		assertEquals(true, l.isEmpty()); // if the list is empty, it means that the constructor successfully created the list
	}
	
	@Test
	public void publicConstructorWithNullAsParameterForLinkedListIndexedCollection() {
		try {
			new LinkedListIndexedCollection<String>(null);
			fail();
		} catch(NullPointerException e) { /* exception is expected */ }
	}
	
	@Test
	public void publicConstructorWithParameterLinkedListIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < a.size(); i++) a.add(i);
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>(a);
		assertEquals(a.size(), l.size());
	}
	
	@Test
	public void addNullElementIntoLinkedListIndexedCollection() {
		try {
			LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
			l.add(null);
			fail();
		} catch(NullPointerException e) { /* exception is expected */ }
	}
	
	@Test
	public void addElementIntoEmptyLinkedListIndexedCollection() {
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
		l.add(5);
		assertEquals(true, l.contains(5));
	}
	
	@Test
	public void addElementIntoFullLinkedListIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < 16; i++) a.add(i);
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>(a);
		l.add(38);
		assertEquals(38, l.get(a.size())); // the new element should be on the last place inside collection
	}
	
	@Test
	public void getWhenThrowingIndexOutOfBoundsExceptionInLinkedListIndexedCollection() {
		try {
			LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
			l.get(-1);
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void getFirstHalfWhenNotThrowingExceptionInLinkedListIndexedCollection() {
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
		for(int i = 0; i < 16; i++) l.add(i);
		assertEquals(5, l.get(5));
	}
	
	@Test
	public void getSecondHalfWhenNotThrowingExceptionInLinkedListIndexedCollection() {
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
		for(int i = 0; i < 16; i++) l.add(i);
		assertEquals(15, l.get(l.size() - 1));
	}
	
	@Test
	public void clearLinkedListCollection() {
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
		for(int i = 0; i < 16; i++) l.add(i);
		l.clear();
		assertEquals(0, l.size());
		assertEquals(true, l.isEmpty());
	}
	
	@Test
	public void insertWithIndexOutOfBoundsExceptionInLinkedListIndexedCollection() {
		try {
			LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
			l.insert(0, -1);
			fail();
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void insertWithoutIndexOutOfBoundsExceptionInLinkedListIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < 16; i++) a.add(i);
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>(a);
		l.insert(38, 5);
		assertEquals(a.size() + 1, l.size());
		assertEquals(38, l.get(5));
	}
	
	@Test
	public void indexOfWithNullAsAnArgumentInLinkedListIndexedCollection() {
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
		for(int i = 0; i < 16; i++) l.add(i);
		assertEquals(-1, l.indexOf(null));
	}
	
	@Test
	public void indexOfWithNonexistingArgumentInLinkedListIndexedCollection() {
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
		for(int i = 0; i < 16; i++) l.add(i);
		assertEquals(-1, l.indexOf(20));
	}
	
	@Test
	public void indexOfWithExistingArgumentInLinkedListIndexedCollection() {
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
		for(int i = 0; i < 16; i++) l.add(Integer.valueOf(i));
		assertEquals(8, l.indexOf(8));
	}
	
	@Test
	public void removeWithIndexOutOfBoundsExceptionInLinkedListIndexedCollection() {
		try {
			LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
			l.remove(-1);
			fail();
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void removeWithoutIndexOutOfBoundsExceptionInLinkedListIndexedCollection() {
		LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
		for(int i = 0; i < 16; i++) l.add(Integer.valueOf(i));
		l.remove(8);
		assertEquals(9, l.get(8));
		assertEquals(16 - 1, l.size());
		assertEquals(15, l.get((16 - 1) - 1));
	}

}
