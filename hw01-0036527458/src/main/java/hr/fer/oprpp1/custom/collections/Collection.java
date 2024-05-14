package hr.fer.oprpp1.custom.collections;

/**
 * Default collection class.
 * @author Tin Jukić
 *
 */
public class Collection {

	/**
	 * Constructor for the collection class (should not be used)
	 */
	protected Collection() {
		// default constructor
	}
	
	/**
	 * 
	 * @return true if the collection is empty, false otherwise
	 */
	boolean isEmpty() {
		if(this.size() == 0) return false;
		return true;
	}
	
	/**
	 * 
	 * @return the size of the collection
	 */
	int size() {
		return 0;
	}
	
	/**
	 * Adds given object into collection.
	 * @param value element which needs to be added into collection
	 */
	void add(Object value) {
		
	}
	
	/**
	 * 
	 * @param value element to be found inside collection
	 * @return true if the elements exist inside collection, false otherwise
	 */
	boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes the given element from the collection.
	 * @param value element to be removed from the collection
	 * @return true if the element was successfully removed from collection, false otherwise
	 */
	boolean remove(Object value) {
		return false;
	}
	
	/**
	 * 
	 * @return array representation of the collection
	 * @throws UnsupportedOperationException
	 */
	Object[] toArray() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Goes through all elements from the collection and for each element applies processor.
	 * @param processor processor to be applied
	 */
	void forEach(Processor processor) {
		
	}
	
	/**
	 * Adds all elements from the given collection into new collection.
	 * @param other other collection which needs to be added into current collection
	 */
	void addAll(Collection other) {
		// this = current collection
		// other = given collection -> remains unchanged
		Collection currentCollection = this; // current collection
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				currentCollection.add(value);
			}
		}
		// should be outside the class, but inside the method addAll
		other.forEach(new LocalProcessor());
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	void clear() {
		
	}
}
