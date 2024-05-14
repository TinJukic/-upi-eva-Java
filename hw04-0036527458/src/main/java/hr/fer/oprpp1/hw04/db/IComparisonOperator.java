package hr.fer.oprpp1.hw04.db;

/**
 * Interface which is used to implement strategy.
 * @author Tin Jukić
 *
 */
public interface IComparisonOperator {
	/**
	 * Checks two given values.
	 * @param value1
	 * @param value2
	 * @return true if the condition is satisfied, false instead
	 */
	public boolean satisfied(String value1, String value2);
}
