package hr.fer.oprpp1.custom.collections;

/**
 * interface for all tester instances
 * @author Tin Jukić
 * @param <T> value
 *
 */
public interface Tester<T> {
	
	/**
	 * 
	 * @param obj
	 * @return true if the given object has passed the test
	 */
	boolean test(T obj);

}
