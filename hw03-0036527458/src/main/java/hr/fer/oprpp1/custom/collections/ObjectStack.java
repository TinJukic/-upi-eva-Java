package hr.fer.oprpp1.custom.collections;

/**
 * Stack which stores elements
 * @author Tin Jukić
 * @param <T> value
 */

public class ObjectStack<T> {
	private ArrayIndexedCollection<T> arrayIndexedCollection = new ArrayIndexedCollection<>();
	
	/**
	 * 
	 * @return true if the stack is empty
	 */
	public boolean isEmpty() {
		return arrayIndexedCollection.isEmpty();
	}
	
	/**
	 * 
	 * @return the size of the stack
	 */
	public int size() {
		return arrayIndexedCollection.size();
	}
	
	/**
	 * Pushes new element onto stack.
	 * @param value
	 */
	public void push(T value) {
		arrayIndexedCollection.add(value);
	}
	
	/**
	 * Pops the first element on the stack.
	 * @return popped element
	 * @throws EmptyStackException
	 */
	public T pop() throws EmptyStackException {
		// finds and removes the last element from the collection
		// return value of deleted element
		if(arrayIndexedCollection.size() == 0) throw new EmptyStackException();
		
		int index;
		for(index = arrayIndexedCollection.size() - 1; index >= 0; index++)
			if(arrayIndexedCollection.get(index) != null) break;
		
		T element = arrayIndexedCollection.get(index);
		arrayIndexedCollection.remove(index);
		
		return element;
	}
	
	/**
	 * 
	 * @return the value of the first element on stack
	 * @throws EmptyStackException
	 */
	public T peek() throws EmptyStackException {
		// finds the last element and returns its value
		if(arrayIndexedCollection.size() == 0) throw new EmptyStackException();
		
		int index;
		for(index = arrayIndexedCollection.size() - 1; index >= 0; index++)
			if(arrayIndexedCollection.get(index) != null) break;
		
		return arrayIndexedCollection.get(index);
	}
	
	/**
	 * Deletes all elements on stack.
	 */
	public void clear() {
		arrayIndexedCollection.clear();
	}
}
