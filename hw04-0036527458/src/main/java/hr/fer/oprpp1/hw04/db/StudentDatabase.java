package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Database of StudentRecord objects
 * @author Tin Jukić
 *
 */
public class StudentDatabase {
	private List<StudentRecord> studentRecordList;
	private TreeMap<Integer, StudentRecord> knownStudentRecordMap;
	
	/**
	 * Public constructor for StudentDatabase class.
	 * @param databaseInput list of the elements needed to be put inside database
	 * @throws NumberFormatException
	 * @throws IllegalGradeValueException
	 * @throws DuplicateElementsException
	 */
	public StudentDatabase(List<String> databaseInput) throws NumberFormatException, IllegalGradeValueException, DuplicateElementsException {
		this.studentRecordList = new ArrayList<>();
		this.knownStudentRecordMap = new TreeMap<>();
		
		
		for(String element : databaseInput) {
			if(element.length() <= 1) break;
			
			String jmbag = "";
			String lastName = "";
			String firstName = "";
			int finalGrade = 0;
			
			String[] podniz = element.split("\\t");
			
			if(jmbag.equals("")) jmbag = podniz[0].strip();
			if(lastName.equals("")) lastName = podniz[1].strip();
			if(firstName.equals("")) firstName = podniz[2].strip();
			if(finalGrade == 0) finalGrade = Integer.parseInt(podniz[3].strip());
			
			if(finalGrade < 1 || finalGrade > 5) throw new IllegalGradeValueException();
			
			var student = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			if(this.studentRecordList.contains(student)) throw new DuplicateElementsException();
			
			// everything was ok -> add the student to map
			this.studentRecordList.add(student);
			
			// if jmbag is known
			if(!jmbag.equals("")) {
				this.knownStudentRecordMap.put(jmbag.hashCode(), student);
			}
		}
	}
	
	/**
	 * 
	 * @param jmbag Students JMBAG
	 * @return Student which has the given JMBAG
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return this.knownStudentRecordMap.get(jmbag.hashCode());
	}
	
	/**
	 * Filters through StudentRecord database.
	 * @param filter filter used to filter through database
	 * @return list of the accepted students
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new ArrayList<>();
		
		for(StudentRecord s : this.studentRecordList) {
			if(filter.accepts(s)) {
				temp.add(s);
			}
		}
		
		return temp;
	}
}
