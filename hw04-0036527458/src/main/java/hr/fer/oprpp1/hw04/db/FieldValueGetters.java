package hr.fer.oprpp1.hw04.db;

/**
 * Class which gets elements from StudentRecord class.
 * @author Tin Jukić
 *
 */
public class FieldValueGetters {
	/**
	 * Gets students firstName.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> {
		return record.getFirstName();
	};
	
	/**
	 * Gets students lastName.
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> {
		return record.getLastName();
	};
	
	/**
	 * Gets students JMBAG.
	 */
	public static final IFieldValueGetter JMBAG = (record) -> {
		return record.getJmbag();
	};
}
