package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * interface for every elements getter instance
 * @author Tin Jukić
 *
 */
public interface ElementsGetter {
	
	/**
	 * 
	 * @return true if the current collection has the next element
	 */
	public boolean hasNextElement();
	
	/**
	 * 
	 * @return the next element from the collection, if it has one
	 * @throws NoSuchElementException
	 */
	public Object getNextElement() throws NoSuchElementException;
	
	/**
	 * processes all the elements from the collection using given processor
	 * @param p
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) p.process(getNextElement());
	}
}
