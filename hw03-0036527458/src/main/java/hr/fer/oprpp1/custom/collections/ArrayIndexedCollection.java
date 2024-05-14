package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * 
 * Stores elements
 * @author Tin Jukić
 * @param <T> value
 *
 */

public class ArrayIndexedCollection<T> implements List<T> {
	private int size;
	private T[] elements;
	private long modificationCount = 0;
	
	private static class ElementsGetterClass<T> implements ElementsGetter<T> {
		private int index;
		private int collectionSize;
		private T[] collectionElements;
		private double savedModificationCount;
		private ArrayIndexedCollection<T> reference;
		
		/**
		 * private constructor
		 * @param collectionSize
		 * @param collectionElements
		 * @param savedModificationCount
		 * @param reference
		 */
		private ElementsGetterClass(int collectionSize, T[] collectionElements, double savedModificationCount, ArrayIndexedCollection<T> reference) {
			this.collectionSize = collectionSize;
			this.collectionElements = collectionElements;
			this.savedModificationCount = savedModificationCount;
			this.index = 0;
			this.reference = reference;
		}
		
		/**
		 * 
		 * @return true if the collection has been altered after creation of the elements getter
		 */
		private boolean modificationNumberChanged() {
			if(this.reference.modificationCount != this.savedModificationCount) return true;
			
			return false;
		}
		
		/**
		 * @return true if the collection has next element
		 * @throws NoSuchElementException, ConcurrentModificationException
		 */
		@Override
		public boolean hasNextElement() throws NoSuchElementException, ConcurrentModificationException {
			if(this.modificationNumberChanged()) throw new ConcurrentModificationException();
			if(!this.hasNextElement()) throw new NoSuchElementException();
			
			if(this.index >= 0 && this.index < this.collectionSize) return true;		
			return false;
		}
		
		/**
		 * @return next element inside the collection, if the collection has the next element
		 * @throws NoSuchElementException, ConcurrentModificationException
		 */
		@Override
		public T getNextElement() throws NoSuchElementException, ConcurrentModificationException {
			T element = this.collectionElements[this.index];
			this.index++;
			
			return element;
		}
	}
	
	// default constructor
	/**
	 * a default constructor for this class
	 */
	public ArrayIndexedCollection() {
		this.size = 16;
		this.elements = (T[]) new Object[16];
	}
	
	/**
	 * a constructor with initial capacity as an argument
	 * @param initialCapacity
	 * @throws IllegalArgumentException
	 */
	public ArrayIndexedCollection(int initialCapacity) throws IllegalArgumentException {
		if(initialCapacity >= 1) {
			this.size = initialCapacity;
			this.elements = (T[]) new Object[initialCapacity];
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * a constructor with another collection as an argument, it has to copy all elements from the given collection
	 * @param other
	 * @throws NullPointerException
	 */
	public ArrayIndexedCollection(Collection<T> other) throws NullPointerException {
		if(other != null) {
			if(other.size() > 16) {
				this.size = other.size();
			} else {
				this.size = 16;
			}
			this.elements = (T[]) new Object[this.size];
//			this.addAll(other);
			T[] helperArray = other.toArray();
			for(int i = 0; i < other.size(); i++)
				this.elements[i] = helperArray[i];
			
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * a constructor with two arguments: initialCapacity and other
	 * @param other
	 * @param initialCapacity
	 * @throws NullPointerException
	 */
	public ArrayIndexedCollection(Collection<T> other, int initialCapacity) throws NullPointerException {
		if(other != null) {
			if(initialCapacity < other.size()) {
				this.size = other.size();
			} else {
				this.size = initialCapacity;
			}
			this.elements = (T[]) new Object[this.size];
//			this.addAll(other);
			T[] helperArray = other.toArray();
			for(int i = 0; i < other.size(); i++)
				this.elements[i] = helperArray[i];
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * adds an object into current collection;
	 * @throws NullPointerException
	 */
	@Override
	public void add(T value) throws NullPointerException {
		// check if there is an empty space inside this array
		boolean emptySpace = false;
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i] == null) {
				emptySpace = true;
				break;
			}
		
		if(value != null) {
			if(!emptySpace) {
				// no space -> reallocate the array
				// helperArray should store the copy of the previous array
				this.modificationCount += 1;
				
				T[] helperArray = (T[]) new Object[this.size];
				
				// copy all elements into helperArray
				for(int j = 0; j < this.size; j++)
					helperArray[j] = this.elements[j];
				
				// double the size
				this.size = this.size * 2;
				this.elements = (T[]) new Object[this.size];
				
				// return all elements from helperArray into original array, which is now two times the size
				for(int i = 0; i < this.size / 2; i++)
					this.elements[i] = helperArray[i];
			}
			
			// add new element into array
			for(int i = 0; i < this.elements.length; i++) {
				if(this.elements[i] == null) {
					// put the new value in the first empty space
					this.elements[i] = value;
					break;
				}
			}
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * gets the element from the collection at the given index
	 * @param index
	 */
	public T get(int index) {
		// valid indexes -> from 1 to (size-1)
		try {
			return this.elements[index];
		} catch(IndexOutOfBoundsException e) {
			throw e;
		}
	}
	
	/**
	 * removes all elements from the collection
	 */
	@Override
	public void clear() {
		// set all elements to null pointer and don't change the size of the array
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i] != null)
				this.elements[i] = null;
		
		this.modificationCount += 1;
	}
	
	/**
	 * inserts object value at given position, if necessary, multiplies the size by 2
	 * @param value
	 * @param position
	 * @throws IndexOutOfBoundsException
	 */
	public void insert(T value, int position) {
		try {
			this.modificationCount += 1;
			
			// check whether there is space for new element
			boolean hasSpace = false;
			for(int i = 0; i < this.size; i++)
				if(this.elements[i] == null) {
					hasSpace = true;
					break;
				}
			
			if(!hasSpace) {
				// reallocate the original array, if there is no space for new element
				T[] helperArray = (T[]) new Object[this.size];
				this.size = this.size + 1;
				for(int i = 0; i < helperArray.length; i++)
					helperArray[i] = this.elements[i];
				
				this.elements = (T[]) new Object[this.size];
				for(int i = 0; i < position; i++)
					this.elements[i] = helperArray[i];

				this.elements[position] = value;
				for(int i = position + 1; i < this.size(); i++)
					this.elements[i] = helperArray[i - 1];
			} else {
				Object[] helperArray = new Object[this.size];
				for(int i = 0; i < helperArray.length; i++)
					helperArray[i] = this.elements[i];
				for(int i = position; i < this.size(); i++)
					helperArray[i] = this.elements[i];
				this.elements[position] = value;
				for(int i = position + 1; i < this.size; i++)
					this.elements[i] = (T) helperArray[i - 1];
			}
		} catch(IndexOutOfBoundsException e) {
			throw e;
		}
	}
	
	/**
	 * returns current size of no-null elements inside the collection
	 */
	@Override
	public int size() {
		int size = 0;
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i] != null) size++;
		
		return size;
	}
	
	/**
	 * returns index of the element at which it is stored inside collection, -1 if the element does not exist
	 * @param value
	 */
	public int indexOf(Object value) {
		if(value.equals(null)) return -1;
		
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i].equals(value))
				return i;
		
		return -1;
	}
	
	/**
	 * removes the element from the collection at the given index and shifts the elements
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	public void remove(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		
		this.modificationCount += 1;
		
		T[] helperArray = (T[]) new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			helperArray[i] = this.elements[i];
			this.elements[i] = null; // reset all values
		}
		
		for(int i = 0; i < index; i++)
			this.elements[i] = helperArray[i];
		
		for(int i = index; i < this.size - 1; i++)
			this.elements[i] = helperArray[i + 1];
	}
	
	/**
	 * returns boolean value whether or not the element value is inside collection
	 * @param value
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i].equals(value)) return true;
		
		return false;
	}

	/**
	 * creates an array and returns it;
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Object[] toArray() throws UnsupportedOperationException {
		Object[] array = new Object[this.size()];
		for(int i = 0; i < this.size(); i++) {
			array[i] = this.elements[i]; 
		}
		
		return array;
	}

	/**
	 * returns true if the element was successfully removed from the collection or false otherwise, removes the first instance of the given element
	 * @param value
	 */
	@Override
	public boolean remove(Object value) {
		// find the wanted index
		int index = 0;
		boolean found = false;
		T[] helper = (T[]) new Object[this.size];
		
		do {
			if(this.elements[index].equals(value)) {
				found = true;
				break;
			}
			index++;
		} while(!found);
		
		if(found) {
			this.modificationCount += 1;
			
			for(int i = 0; i < this.size; i++)
				helper[i] = this.elements[i];
			
			for(int i = index; i < this.size - 1; i++) {
				this.elements[i] = helper[i + 1];
			}
		
			return true;
		}
		else return false;
	}

	/**
	 * accepts another collection and its elements adds into current collection
	 * @param other
	 */
	@Override
	public void addAll(Collection<? extends T> other) {
		this.modificationCount += 1;
		List.super.addAll(other);
	}

	/**
	 * returns boolean value whether the collection is empty or not; calls the implementation form Collection class
	 */
	@Override
	public boolean isEmpty() {
		return List.super.isEmpty();
	}

	/**
	 * @return new instance of element getter
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterClass<>(this.size(), this.elements, this.modificationCount, this);
	}
}
