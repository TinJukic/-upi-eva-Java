package hr.fer.oprpp1.custom.collections;

/**
 * 
 * @author Tin Jukić
 * @param <T> value
 *
 */

public interface Processor<T> {
	/**
	 * processes given value
	 * @param value
	 */
	void process(T value);
}
