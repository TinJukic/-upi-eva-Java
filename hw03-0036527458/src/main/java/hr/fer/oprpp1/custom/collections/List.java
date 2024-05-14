package hr.fer.oprpp1.custom.collections;

/**
 * interface for all list instances
 * @author Tin Jukić
 * @param <T> value
 *
 */
public interface List<T> extends Collection<T> {
	/**
	 * 
	 * @param index
	 * @return object at the given index
	 */
	T get(int index);
	
	/**
	 * inserts value at the given position
	 * @param value
	 * @param position
	 */
	void insert(T value, int position);
	
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
