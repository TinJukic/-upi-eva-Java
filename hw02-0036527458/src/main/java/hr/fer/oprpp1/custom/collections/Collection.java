package hr.fer.oprpp1.custom.collections;

/**
 * interface for every Collection instance
 * @author Tin Jukić
 *
 */

public interface Collection {

	// methods in interfaces are by default public and abstract
	/**
	 * default method for the collection
	 * @return boolean value whether the collection is empty or not
	 */
	default boolean isEmpty() {
		// this implementation is correct
		if (this.size() == 0)
			return false;
		return true;
	}

	/**
	 * 
	 * @return current size of no-null elements inside the collection
	 */
	int size();

	/**
	 * adds an object into current collection
	 * @param value
	 */
	void add(Object value);

	/**
	 * 
	 * @param value
	 * @return boolean value whether or not the element
	 */
	boolean contains(Object value);

	/**
	 * 
	 * @param value
	 * @return true if the wanted element was successfully removed from the collection
	 */
	boolean remove(Object value);

	/**
	 * 
	 * @return an array representation of the current collection
	 * @throws UnsupportedOperationException
	 */
	Object[] toArray() throws UnsupportedOperationException;

	/**
	 * goes through each element inside collection
	 * @param processor
	 */
	default void forEach(Processor processor) {
		ElementsGetter elementsGetter = this.createElementsGetter();
		
		while(elementsGetter.hasNextElement())
			processor.process(elementsGetter.getNextElement());
	}

	/**
	 * accepts another collection and its elements adds into current collection
	 * @param other
	 */
	default void addAll(Collection other) {
		// this = current collection
		// other = given collection -> remains unchanged
		Collection currentCollection = this; // current collection
		class LocalProcessor implements Processor {
			@Override
			public void process(Object value) {
				currentCollection.add(value);
			}
		}
		// should be outside the class, but inside the method addAll
		other.forEach(new LocalProcessor());
	}

	/**
	 * removes all elements from the collection
	 */
	void clear();

	/**
	 * 
	 * @return instance of the elements getter
	 */
	ElementsGetter createElementsGetter();

	/**
	 * adds all elements from the given collection into current collection, if the test on the current element has been passed
	 * @param col
	 * @param tester
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter elementsGetter = col.createElementsGetter();

		while (elementsGetter.hasNextElement()) {
			Object element = elementsGetter.getNextElement();
			if (tester.test(element))
				this.add(element);
		}
	}
}
