package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Stack class.
 * @author Tin Jukić
 *
 */
public class ObjectStack {
	private ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
	
	/**
	 * 
	 * @return true if the stack is empty, false otherwise
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
	 * Adds given element to the stack.
	 * @param value element to be added
	 */
	public void push(Object value) {
		arrayIndexedCollection.add(value);
	}
	
	/**
	 * 
	 * @return the element on top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object pop() throws EmptyStackException {
		// finds and removes the last element from the collection
		// return value of deleted element
		if(arrayIndexedCollection.size() == 0) throw new EmptyStackException();
		
		int index;
		for(index = arrayIndexedCollection.size() - 1; index >= 0; index++)
			if(arrayIndexedCollection.get(index) != null) break;
		
		Object element = arrayIndexedCollection.get(index);
		arrayIndexedCollection.remove(index);
		
		return element;
	}
	
	/**
	 * 
	 * @return the element on top of the stack, but does not remove it
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object peek() throws EmptyStackException {
		// finds the last element and returns its value
		if(arrayIndexedCollection.size() == 0) throw new EmptyStackException();
		
		int index;
		for(index = arrayIndexedCollection.size() - 1; index >= 0; index++)
			if(arrayIndexedCollection.get(index) != null) break;
		
		return arrayIndexedCollection.get(index);
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		arrayIndexedCollection.clear();
	}
}
