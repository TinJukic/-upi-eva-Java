package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {
	
	@Test
	public void nullPointerExceptionWhenCreatingArrayIndexedCollection() {
		try {
			new ArrayIndexedCollection<String>(null);
			fail();
		} catch(NullPointerException e) { /* exception is expected */ }
	}
	
	@Test
	public void illegalArgumentExceptionWhenCreatingArrayIndexedCollection() {
		try {
			new ArrayIndexedCollection<String>(-1);
			fail();
		} catch(IllegalArgumentException e) { /* exception is expected */ }
	}
	
	@Test
	public void creatingArrayIndexedCollectionUsingAnEmptyConstructor() {
		new ArrayIndexedCollection<String>();
	}
	
	@Test
	public void creatingArrayIndexedCollectionUsingConstructorWithInitialCapacity() {
		new ArrayIndexedCollection<String>(10);
	}
	
	@Test
	public void creatingArrayIndexedCollectionUsingConstructorWithOtherCollection() {
		ArrayIndexedCollection<String> a = new ArrayIndexedCollection<>();
		new ArrayIndexedCollection<String>(a);
	}
	
	@Test
	public void creatingArrayIndexedCollectionUsingConstructorWithTwoElements() {
		ArrayIndexedCollection<String> a = new ArrayIndexedCollection<>();
		new ArrayIndexedCollection<String>(a, 10);
	}
	
	@Test
	public void addingNullObjectIntoTheArrayIndexedCollection() {
		try {
			ArrayIndexedCollection<String> a = new ArrayIndexedCollection<>();
			a.add(null);
			fail();
		} catch(NullPointerException e) { /* exception is expected */ }
	}
	
	@Test
	public void addingObjectIntoAnEmptyArrayIndexedCollection() {
		try {
			ArrayIndexedCollection<String> a = new ArrayIndexedCollection<>();
			a.add(new String());
		} catch(NullPointerException e) { fail(); }
	}
	
	@Test
	public void addingObjectIntoTheFullArrayIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(9);
		for(int i = 0; i < 9; i++) a.add(i);
		a.add(10);
		assertEquals(10, a.size());
		assertEquals(10, a.get(9));
	}
	
	@Test
	public void gettingObjectFromTheArrayIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < 16; i++) a.add(i);
		assertEquals(16, a.size());
		assertEquals(5, a.get(5));
	}
	
	@Test
	public void invalidIndexInsideGetArrayIndexedCollection() {
		try {
			ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
			for(int i = 0; i < a.size(); i++) a.add(i);
			assertEquals(5, a.get(-1));
			fail();
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void clearingElementsAndCheckingSizeForArrayIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < a.size(); i++) a.add(i);
		a.clear();
		assertEquals(0, a.size());
	}
	
	@Test
	public void clearingElementsFromArrayIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < a.size(); i++) a.add(i);
		a.clear();
		assertEquals(null, a.get(5));
	}
	
	@Test
	public void insertElementInsideArrayIndexedCollectionThrowingException() {
		try {
			ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
			for(int i = 0; i < 16; i++) a.add(i);
			a.insert(10, 16 + 1);
			fail();
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void insertElementInsideArrayIndexedCollectionWithoutExceptionAndWithoutSpace() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < 16; i++) a.add(i);
		a.insert(10, 5);
		assertEquals(10, a.get(5));
	}
	
	@Test
	public void insertElementInsideArrayIndexedCollectionWithoutExceptionWithSpace() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < 16 - 1; i++) a.add(i);
		a.insert(10, 5);
		assertEquals(10, a.get(5));
	}
	
	@Test
	public void indexOfElementThatDoesNotExistInsideArrayIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < 16; i++) a.add(i);
		assertEquals(-1, a.indexOf(20));
	}
	
	@Test
	public void indexOfElementThatDoesExistInsideArrayIndexedCollection() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < 16; i++) a.add(i);
		assertEquals(5, a.indexOf(5));
	}
	
	@Test
	public void removeElementWithIndexOutOfBoundsExceptionFromArrayIndexedCollection() {
		try {
			ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
			for(int i = 0; i < 16; i++) a.add(i);
			a.remove(a.size());
			fail();
		} catch(IndexOutOfBoundsException e) { /* exception is expected */ }
	}
	
	@Test
	public void removeElementFromArrayIndexedCollectionWithoutException() {
		ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
		for(int i = 0; i < 16; i++) a.add(i);
		a.remove(5);
		// a few tests...
		assertEquals(6, a.get(5));
		assertEquals(13, a.get(12));
		assertEquals(null, a.get(16 - 1));
	}
}
