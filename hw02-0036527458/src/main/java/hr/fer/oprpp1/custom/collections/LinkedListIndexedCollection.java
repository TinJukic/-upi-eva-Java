package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * stores elements
 * @author Tin Jukić
 *
 */

public class LinkedListIndexedCollection implements List {
	/**
	 * private static class for nodes used to store elements in this collection
	 * @author Tin Jukić
	 *
	 */
	private static class ListNode {
		// pointers
		ListNode previous;
		ListNode next;
		Object valueStorage;
		
		/**
		 * constructor for the node element
		 * @param previous
		 * @param next
		 * @param valueStorage
		 */
		public ListNode(ListNode previous, ListNode next, Object valueStorage) {
			this.previous = previous;
			this.next = next;
			this.valueStorage = valueStorage;
		}
	}
	
	/**
	 * private static class which represents the correct implementation for this collection
	 * @author Tin Jukić
	 *
	 */
	private static class ElementsGetterClass implements ElementsGetter {
		private ListNode currentCollectionElement;
		private double savedModificationCount;
		private LinkedListIndexedCollection currentCollection;
		
		/**
		 * constructor for the elements getter instance
		 * @param first
		 * @param savedModificationCount
		 * @param currentCollection
		 */
		public ElementsGetterClass(ListNode first, double savedModificationCount, LinkedListIndexedCollection currentCollection) {
			this.currentCollectionElement = first;
			this.savedModificationCount = savedModificationCount;
			this.currentCollection = currentCollection;
		}
		
		/**
		 * 
		 * @return true if the collection has been altered after creation of the elements getter
		 */
		private boolean modificationNumberChanged() {
			if(this.currentCollection.modificationCount != this.savedModificationCount) return true;
			
			return false;
		}

		/**
		 * true if the collection has next element
		 */
		@Override
		public boolean hasNextElement() {
			if(this.currentCollectionElement == null) return false;
			return true;
		}

		/**
		 * @return next element inside the collection, if the collection has the next element
		 * @throws NoSuchElementException, ConcurrentModificationException
		 */
		@Override
		public Object getNextElement() throws NoSuchElementException, ConcurrentModificationException {
			if(this.modificationNumberChanged()) throw new ConcurrentModificationException();
			if(!this.hasNextElement()) throw new NoSuchElementException();
			
			Object element = this.currentCollectionElement.valueStorage;
			this.currentCollectionElement = this.currentCollectionElement.next;
			
			return element;
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	private double modificationCount = 0;

	/**
	 * a default constructor for this class
	 */
	public LinkedListIndexedCollection() {
		// default constructor
		this.first = this.last = null;
		this.size = 0;
	}
	
	/**
	 * a constructor with another collection as an argument, it has to copy all elements from the given collection
	 * @param collection
	 * @throws NullPointerException
	 */
	public LinkedListIndexedCollection(Collection collection) throws NullPointerException {
		// elements from collection should be copied into this new Collection
		if(collection == null) throw new NullPointerException();
		
		ArrayIndexedCollection a = (ArrayIndexedCollection)collection;
		
		this.size = a.size();
		this.first = new ListNode(null, null, a.get(0));
		
		ListNode current = this.first;
		for(int i = 1; i < a.size(); i++) {
			current.next = new ListNode(current, null, a.get(i));
			current = current.next;
		}
		
		this.last = current;
	}
	
	/**
	 * adds an object into current collection;
	 * @param value
	 * @throws NullPointerException
	 */
	@Override
	public void add(Object value) throws NullPointerException {
		if(value == null) throw new NullPointerException();
		
		this.modificationCount = this.modificationCount + 1;
		
		// creating new ListNode element, to store Object value into list memory
		ListNode ln = new ListNode(this.last, null, value);
		
		// if the old collection is empty, set newly added element to be the first one
		if(this.first == null) {
			this.first = this.last = ln;
		}
		else {
			this.last.next = ln; // setting the last element to be added element
			this.last = ln;
		}
		
		this.size = this.size + 1;
	}
	
	/**
	 * @return current size of no-null elements inside the collection
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * @return the object from the index location inside the collection;
	 * @throws NullPointerException
	 */
	public Object get(int index) throws IndexOutOfBoundsException {
		if(index >= this.size() || index < 0) throw new IndexOutOfBoundsException();
		
		int half = this.size() / 2;
		if(index <= half) {
			// start from the beginning
			int i = 0;
			
			// if the first element is the element that is wanted
			if(i == index)
				return this.first.valueStorage;

//			ListNode listNode = this.first;
			ListNode nextListNode = this.first.next;
			
			do {
				i++;
				if(index == i)
					return nextListNode.valueStorage;
				else {
					// go to the next element
					nextListNode = nextListNode.next;
				}
			} while(i < index);
		} else {
			// start from the end
			int i = this.size() - 1;
			
			// if the last element is the element that is wanted
			if(i == index)
				return this.last.valueStorage;
			
			ListNode previousListNode = this.last.previous;
			
			do {
				i--;
				if(index == i)
					return previousListNode.valueStorage;
				else {
					// go to the previous element
					previousListNode = previousListNode.previous;
				}
			} while(i > index);
		}
		
		return null;
	}
	
	/**
	 * removes all elements from the collection
	 */
	@Override
	public void clear() {
		ListNode listNode = this.first;
		
		// remove all elements from the collection -> set them to null pointer
		do {
			listNode.previous = null;
			listNode.valueStorage = null;
			ListNode nextListNode = listNode.next;
			listNode.next = null;
			listNode = nextListNode;
		} while(listNode.valueStorage != null && listNode.next != null && listNode.previous != null);
		
		// collection forgets about current linked list
		this.first = null;
		this.last = null;
		this.size = 0;
		this.modificationCount = this.modificationCount + 1;
	}
	
	/**
	 * inserts object value at given position, with each new object the size of the collection is increased by 1
	 * @param value
	 * @param position
	 * @throws IndexOutOfBoundsException
	 */
	public void insert(Object value, int position) throws IndexOutOfBoundsException {
		if(position < 0 || position > this.size()) throw new IndexOutOfBoundsException();
		
		// start from the first element
		ListNode listNode = this.first;
		
		for(int i = 0; i < position - 1; i++) {
			// find the position
			listNode = listNode.next;
		}
		
		// position found -> switch elements
		ListNode newElement = new ListNode(listNode, listNode.next, value);
		listNode.next = newElement;
		
		this.size = this.size + 1;
		this.modificationCount = this.modificationCount + 1;
	}
	
	/**
	 * @return index of the element at which it is stored inside collection, -1 if the element does not exist
	 * @param value
	 */
	public int indexOf(Object value) {
		ListNode listNode = this.first;
		int i = 0;
		do {
			if(listNode.valueStorage.equals(value)) return i;
			else {
				i++;
				listNode = listNode.next;
			}
		} while(listNode.next != null);
		
		// if the wanted value is not found
		return -1;
	}
	
	/**
	 * removes the element from the collection at the given index and shifts the elements
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	public void remove(int index) throws IndexOutOfBoundsException {
		// legal indexes -> from 1 to size-1
		if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		
		ListNode listNode = this.first;
		
		// find the wanted index
		for(int i = 0; i < index - 1; i++)
			listNode = listNode.next;
		
		// wanted index found
		ListNode previous = listNode.previous;
		ListNode next = listNode.next;
		listNode.valueStorage = null;
		
		previous.next = next;
		next.previous = previous;
		
		this.size = this.size - 1;
		this.modificationCount = this.modificationCount + 1;
	}
	
	/**
	 * @return boolean value whether or not the element value is inside collection
	 * @param value
	 */
	@Override
	public boolean contains(Object value) {
		ListNode current = this.first;
		while(current != null) {
			if(current.valueStorage == value) return true;
			current = current.next;
		}
		
		return false;
	}

	/**
	 * @return boolean value whether the collection is empty or not
	 */
	@Override
	public boolean isEmpty() {
		// if the first element exists inside the list, that means the list is not empty
		if(this.first != null) return false;
		else return true;
	}

	/**
	 * @return creates an array and returns it
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Object[] toArray() throws UnsupportedOperationException {
		Object[] array = new Object[this.size()];
		ListNode current = this.first;
		int i = 0;
		while(i < this.size()) {
			array[i] = current.valueStorage;
			current = current.next;
			i++;
		}
		
		return array;
	}

	/**
	 * accepts another collection and its elements adds into current collection
	 * @param other
	 */
	@Override
	public void addAll(Collection other) {
		List.super.addAll(other);
		this.modificationCount = this.modificationCount + 1;
	}

	/**
	 * removes the first instance of the given element
	 * @return true if the element was successfully removed from the collection or false otherwise
	 * @param value
	 */
	@Override
	public boolean remove(Object value) {
		// determine which element should be removed from the collection
		ListNode current = this.first;
		boolean found = false;
		do {
			if(current.valueStorage.equals(value)) {
				// element found
				found = true;
				break;
			}
			current = current.next;
		} while(current != this.last.next);
		
		ListNode previous = current.previous;
		ListNode next = current.next;
		
		previous.next = current.next;
		if(next != null) next.previous = current.previous;
		
		current.previous = current.next = null;
		current.valueStorage = null;
		
		this.size = this.size - 1;
		
		this.modificationCount = this.modificationCount + 1;
		
		return found;
	}

	/**
	 * @return new instance of element getter
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterClass(this.first, this.modificationCount, this);
	}
}
