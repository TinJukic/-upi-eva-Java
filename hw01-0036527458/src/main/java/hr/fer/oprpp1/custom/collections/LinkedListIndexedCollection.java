package hr.fer.oprpp1.custom.collections;

/**
 * Linked list indexed collection class.
 * @author Tin Jukić
 *
 */
public class LinkedListIndexedCollection extends Collection {
	/**
	 * Static class used for storing elements inside collection.
	 * @author Tin Jukić
	 *
	 */
	private static class ListNode {
		// pointers
		ListNode previous;
		ListNode next;
		Object valueStorage;
		
		/**
		 * Constructor for Node class.
		 * @param previous previous element
		 * @param next next element
		 * @param valueStorage value of the element
		 */
		public ListNode(ListNode previous, ListNode next, Object valueStorage) {
			this.previous = previous;
			this.next = next;
			this.valueStorage = valueStorage;
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;

	/**
	 * Public default constructor.
	 */
	public LinkedListIndexedCollection() {
		// default constructor
		this.first = this.last = null;
		this.size = 0;
	}
	
	/**
	 * Constructor with one parameter.
	 * @param collection other collection to be added to the new collection
	 * @throws NullPointerException if the given collection if null
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
	 * Adds given element into collection.
	 * @param value element to be added into collection
	 * @throws NullPointerException if the given element is null
	 */
	@Override
	void add(Object value) throws NullPointerException {
		if(value == null) throw new NullPointerException();
		
		// creating new ListNode element, to store Object value into list memory
		ListNode ln = new ListNode(this.last, null, value);
		this.last = ln; // setting the last element to be added element
		this.size = this.size + 1;
		
		// if the old collection is empty, set newly added element to be the first one
		if(this.first == null) this.first = this.last = ln;
	}
	
	/**
	 * @return the size of the collection
	 */
	@Override
	int size() {
		return this.size;
	}
	
	/**
	 * Gets the Object at the given index from the collection.
	 * @param index index at which to get element
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the index is wrong
	 */
	Object get(int index) throws IndexOutOfBoundsException {
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
	 * Removes all the elements from the collection.
	 */
	@Override
	void clear() {
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
	}
	
	/**
	 * Inserts the given element at the given position.
	 * @param value element to be added into collection
	 * @param position position at which the element needs to be added
	 * @throws IndexOutOfBoundsException if the position is wrong
	 */
	void insert(Object value, int position) throws IndexOutOfBoundsException {
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
	}
	
	/**
	 * 
	 * @param value element to be found inside collection
	 * @return the index of the wanted element
	 */
	int indexOf(Object value) {
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
	 * Removes the element at the given index from the collection.
	 * @param index index from which to remove the element
	 * @throws IndexOutOfBoundsException if the index is incorrect
	 */
	void remove(int index) throws IndexOutOfBoundsException {
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
	}
	
	/**
	 * @param value element to be found inside collection
	 * @return true if the element exists inside collection, false otherwise
	 */
	@Override
	boolean contains(Object value) {
		ListNode current = this.first;
		while(current != null) {
			if(current.valueStorage == value) return true;
			current = current.next;
		}
		
		return false;
	}

	/**
	 * Check if the collection is empty.
	 * @return true if the collection is empty, false otherwise
	 */
	@Override
	boolean isEmpty() {
		// if the first element exists inside the list, that means the list is not empty
		if(this.first != null) return false;
		else return true;
	}
	
	/**
	 * Goes through each element inside collection and applies the given processor.
	 * @param processor processor to be applied
	 */
	@Override
	void forEach(Processor processor) {
		ListNode current = this.first;
		do {
			processor.process(current.valueStorage);
			current = current.next;
		} while(current != this.last.next);
	}

	/**
	 * @return the array representation of the given collection
	 * @throws UnsupportedOperationException
	 */
	@Override
	Object[] toArray() throws UnsupportedOperationException {
		Object[] array = new Object[this.size];
		ListNode current = this.first;
		int i = 0;
		do {
			array[i] = current.valueStorage;
			current = current.next;
			i++;
		} while(i < this.size);
		
		return array;
	}

	@Override
	void addAll(Collection other) {
		super.addAll(other);
	}

	@Override
	boolean remove(Object value) {
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
		
		return found;
	}
}
