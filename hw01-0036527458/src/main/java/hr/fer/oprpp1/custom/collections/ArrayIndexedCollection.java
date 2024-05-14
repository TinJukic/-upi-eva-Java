package hr.fer.oprpp1.custom.collections;

/**
 * Array indexed collection class.
 * @author Tin Jukić
 *
 */
public class ArrayIndexedCollection extends Collection {
	private int size;
	private Object[] elements;
	
	// default constructor
	/**
	 * A default constructor for this class.
	 */
	public ArrayIndexedCollection() {
		this.size = 16;
		this.elements = new Object[16];
	}
	
	/**
	 * Constructor for this class with one argument.
	 * @param initialCapacity initial capacity of the collection
	 * @throws IllegalArgumentException
	 */
	public ArrayIndexedCollection(int initialCapacity) throws IllegalArgumentException {
		if(initialCapacity >= 1) {
			this.size = initialCapacity;
			this.elements = new Object[initialCapacity];
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Constructor for this class with one argument.
	 * @param other other collection which needs to be inserted into the new collection
	 * @throws NullPointerException
	 */
	public ArrayIndexedCollection(Collection other) throws NullPointerException {
		if(other != null) {
			if(other.size() > 16) {
				this.size = other.size();
			} else {
				this.size = 16;
			}
			this.elements = new Object[size];
//			this.addAll(other);
			Object[] helperArray = other.toArray();
			for(int i = 0; i < other.size(); i++)
				this.elements[i] = helperArray[i];
			
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * Constructor with two arguments.
	 * @param other other collection which needs to be inserted inside the new collection
	 * @param initialCapacity the initial capacity for the new collection
	 * @throws NullPointerException
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) throws NullPointerException {
		if(other != null) {
			if(initialCapacity < other.size()) {
				this.size = other.size();
			} else {
				this.size = initialCapacity;
			}
			this.elements = new Object[this.size];
//			this.addAll(other);
			Object[] helperArray = other.toArray();
			for(int i = 0; i < other.size(); i++)
				this.elements[i] = helperArray[i];
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * Adds the given element into the collection.
	 * @param value element which needs to be put inside collection
	 * @throws NullPointerException if the value element is null
	 */
	@Override
	void add(Object value) throws NullPointerException {
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
				Object[] helperArray = new Object[this.size];
				
				// copy all elements into helperArray
				for(int j = 0; j < this.size; j++)
					helperArray[j] = this.elements[j];
				
				// double the size
				this.size = this.size * 2;
				this.elements = new Object[this.size];
				
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
	 * 
	 * @param index the index from which to get the element inside collection
	 * @return Object at the given index (if the Object does not exist, null is returned)
	 */
	Object get(int index) {
		// valid indexes -> from 1 to (size-1)
		try {
			return this.elements[index];
		} catch(IndexOutOfBoundsException e) {
			throw e;
		}
	}
	
	/**
	 * Deletes all the elements from the collection.
	 */
	@Override
	void clear() {
		// set all elements to null pointer and don't change the size of the array
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i] != null)
				this.elements[i] = null;
	}
	
	/**
	 * Inserts the given element at the given position.
	 * @param value the element which needs to be inserted
	 * @param position the position on which to insert the element
	 */
	void insert(Object value, int position) {
		try {
			// check whether there is space for new element
			boolean hasSpace = false;
			for(int i = 0; i < this.size; i++)
				if(this.elements[i] == null) {
					hasSpace = true;
					break;
				}
			
			if(!hasSpace) {
				// reallocate the original array, if there is no space for new element
				Object[] helperArray = new Object[this.size];
				this.size = this.size + 1;
				for(int i = 0; i < helperArray.length; i++)
					helperArray[i] = this.elements[i];
				
				this.elements = new Object[this.size];
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
					this.elements[i] = helperArray[i - 1];
			}
		} catch(IndexOutOfBoundsException e) {
			throw e;
		}
	}
	
	/**
	 * @return the size of the current collection
	 */
	@Override
	int size() {
		int size = 0;
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i] != null) size++;
		
		return size;
	}
	
	/**
	 * 
	 * @param value element for which the index is wanted
	 * @return the index of the given element (-1 if the element does not exist inside the collection)
	 */
	int indexOf(Object value) {
		if(value.equals(null)) return -1;
		
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i].equals(value))
				return i;
		
		return -1;
	}
	
	/**
	 * Removes the element at the given index from collection.
	 * @param index index of the element which needs to be removed
	 * @throws IndexOutOfBoundsException if the index is incorrect
	 */
	void remove(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		
		Object[] helperArray = new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			helperArray[i] = this.elements[i];
			this.elements[i] = null; // reset all values
		}
		
		for(int i = 0; i < index; i++)
			this.elements[i] = helperArray[i];
		
		for(int i = index; i < this.size - 1; i++)
			this.elements[i] = helperArray[i + 1];
	}
	
	@Override
	boolean contains(Object value) {
		for(int i = 0; i < this.elements.length; i++)
			if(this.elements[i].equals(value)) return true;
		
		return false;
	}
	
	@Override
	void forEach(Processor processor) {
		for(int i = 0; i < this.size(); i++)
			processor.process(this.elements[i]);
	}

	@Override
	Object[] toArray() throws UnsupportedOperationException {
		Object[] array = new Object[this.size()];
		for(int i = 0; i < this.size(); i++) {
			array[i] = this.elements[i]; 
		}
		
		return array;
	}

	@Override
	boolean remove(Object value) {
		// find the wanted index
		int index = 0;
		boolean found = false;
		Object[] helper = new Object[this.size];
		
		do {
			if(this.elements[index].equals(value)) {
				found = true;
				break;
			}
			index++;
		} while(this.elements[index].equals(value));
		
		for(int i = 0; i < this.size; i++)
			helper[i] = this.elements[i];
		
		for(int i = index; i < this.size - 1; i++) {
			this.elements[i] = helper[i + 1];
		}
		
		if(found) return true;
		else return false;
	}

	@Override
	void addAll(Collection other) {
		super.addAll(other);
	}

	@Override
	boolean isEmpty() {
		return super.isEmpty();
	}
}
