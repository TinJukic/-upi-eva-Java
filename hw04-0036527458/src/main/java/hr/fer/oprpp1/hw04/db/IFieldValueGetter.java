package hr.fer.oprpp1.hw04.db;

/**
 * Interface for FieldGetter elements.
 * @author Tin Jukić
 *
 */
public interface IFieldValueGetter {
	/**
	 * 
	 * @param record Student record
	 * @return String representation of the desired element from record
	 */
	public String get(StudentRecord record);
}
