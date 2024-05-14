package hr.fer.oprpp1.custom.collections;

/**
 * interface for all list instances
 * @author Tin Jukić
 *
 */
public interface List extends Collection {
	/**
	 * 
	 * @param index
	 * @return object at the given index
	 */
	Object get(int index);
	
	/**
	 * inserts value at the given position
	 * @param value
	 * @param position
	 */
	void insert(Object value, int position);
	
	/**
	 * 
	 * @param value
	 * @return index of the first instance of the given value
	 */
	int indexOf(Object value);
	
	/**
	 * removes element at the given index
	 * @param index
	 */
	void remove(int index);
}
