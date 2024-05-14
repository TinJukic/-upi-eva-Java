package hr.fer.oprpp1.hw04.db;

/**
 * Interface which filters the elements.
 * @author Tin Jukić
 *
 */
public interface IFilter {
	/**
	 * 
	 * @param record Student record
	 * @return true if the given record is accepted, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
