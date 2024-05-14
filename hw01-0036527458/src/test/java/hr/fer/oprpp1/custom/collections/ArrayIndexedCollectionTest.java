package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {
	
	@Test
	public void nullPointerExceptionWhenCreatingArrayIndexedCollection() {
		try {
			new ArrayIndexedCollection(null);
			fail();
		} catch(NullPointerException e) { /* exception is expected */ }
	}
	
	@Test
	public void illegalArgumentExceptionWhenCreatingArrayIndexedCollection() {
		try {
			new ArrayIndexedCollection(-1);
			fail();
		} catch(IllegalArgumentException e) { /* exception is expected */ }
	}
	
	@Test
	public void creatingArrayIndexedCollectionUsingAnEmptyConstructor() {
		new ArrayIndexedCollection();
	}
	
	@Test
	public void creatingArrayIndexedCollectionUsingConstructorWithInitialCapacity() {
		new ArrayIndexedCollection(10);
	}
	
	@Test
	public void creatingArrayIndexedCollectionUsingConstructorWithOtherCollection() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		new ArrayIndexedCollection(a);
	}
	
	@Test
	public void creatingArrayIndexedCollectionUsingConstructorWithTwoElements() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		new ArrayIndexedCollection(a, 10);
	}
	
	@Test
	public void addingNullObjectIntoTheArrayIndexedCollection() {
		try {
			ArrayIndexedCollection a = new ArrayIndexedCollection();
			a.add(null);
			fail();
		} catch(NullPointerException e) { /* exception is expected */ }
	}
	
	@Test
	public void addingObjectIntoAnEmptyArrayIndexedCollection() {
		try {
			ArrayIndexedCollection a = new ArrayIndexedCollection();
			a.add(new Object());
		} catch(NullPointerException e) { fail(); }
	}
	
	@Test
	public void addingObjectIntoTheFullArrayIndexedCollection() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(9);
		for(int i = 0; i < 9; i++) a.add(i);
		a.add(10);
		assertEquals(10, a.size());
		assertEquals(10, a.get(9));
	}
	
	@Test
	public void gettingObjectFromTheArrayIndexedCollection() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		for(int i = 0; i < 16; i++) a.add(i);
		assertEquals(16, a.size());
		assertEquals(5, a.get(5));
	}
	
	@Test
	public void invalidIndexInsideGetArrayIndexedCollection() {
		try {
			ArrayIndexedCollection a = new ArrayIndexedCollection();
			for(int i = 0; i < a.size(); i++) a.add(i);
			assertEquals(5, a.get(-1));
			fail();
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void clearingElementsAndCheckingSizeForArrayIndexedCollection() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		for(int i = 0; i < a.size(); i++) a.add(i);
		a.clear();
		assertEquals(0, a.size());
	}
	
	@Test
	public void clearingElementsFromArrayIndexedCollection() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		for(int i = 0; i < a.size(); i++) a.add(i);
		a.clear();
		assertEquals(null, a.get(5));
	}
	
	@Test
	public void insertElementInsideArrayIndexedCollectionThrowingException() {
		try {
			ArrayIndexedCollection a = new ArrayIndexedCollection();
			for(int i = 0; i < 16; i++) a.add(i);
			a.insert(10, 16 + 1);
			fail();
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void insertElementInsideArrayIndexedCollectionWithoutExceptionAndWithoutSpace() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		for(int i = 0; i < 16; i++) a.add(i);
		a.insert(10, 5);
		assertEquals(10, a.get(5));
	}
	
	@Test
	public void insertElementInsideArrayIndexedCollectionWithoutExceptionWithSpace() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		for(int i = 0; i < 16 - 1; i++) a.add(i);
		a.insert(10, 5);
		assertEquals(10, a.get(5));
	}
	
	@Test
	public void indexOfElementThatDoesNotExistInsideArrayIndexedCollection() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		for(int i = 0; i < 16; i++) a.add(i);
		assertEquals(-1, a.indexOf(20));
	}
	
	@Test
	public void indexOfElementThatDoesExistInsideArrayIndexedCollection() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		for(int i = 0; i < 16; i++) a.add(i);
		assertEquals(5, a.indexOf(5));
	}
	
	@Test
	public void removeElementWithIndexOutOfBoundsExceptionFromArrayIndexedCollection() {
		try {
			ArrayIndexedCollection a = new ArrayIndexedCollection();
			for(int i = 0; i < 16; i++) a.add(i);
			a.remove(a.size());
			fail();
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void removeElementFromArrayIndexedCollectionWithoutException() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		for(int i = 0; i < 16; i++) a.add(i);
		a.remove(5);
		// a few tests...
		assertEquals(6, a.get(5));
		assertEquals(13, a.get(12));
		assertEquals(null, a.get(16 - 1));
	}
}
