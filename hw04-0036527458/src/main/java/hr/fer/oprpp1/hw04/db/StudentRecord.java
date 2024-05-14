package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Instances of this class will represent records for each student.
 * @author Tin Jukić
 *
 */
public class StudentRecord {
	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * Constructor for the class StudentRecord
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Public getter
	 * @return students jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Public getter
	 * @return students lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Public getter
	 * @return students firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Public getter
	 * @return students finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Correct implementation of hashCode method.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.jmbag);
	}

	/**
	 * Correct implementation of the equals method.
	 */
	@Override
	public boolean equals(Object obj) {
		// if the given obj value is not StudentRecord or is null
		if(obj == null || !(obj instanceof StudentRecord)) return false;
		
		// JMBAG should be unique
		StudentRecord student = (StudentRecord) obj;
		return this.jmbag.equals(student.jmbag);
	}
}
