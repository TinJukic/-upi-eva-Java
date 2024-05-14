package hr.fer.oprpp1.hw04.db;

/**
 * Class used for testing the program.
 * @author Tin Jukić
 *
 */
public class Test {

	private static class PrivateClass1 implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}
		
	}
	
	private static class PrivateClass2 implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		
		if(studentDatabase == null) System.out.println("StudentDatabase is null!");
		else {
			StudentRecord student = studentDatabase.forJMBAG("0000000003");
			System.out.printf("%s, %s, %s, %s%n", student.getJmbag(), student.getFirstName(), student.getLastName(), student.getFinalGrade());
		}
	}

}
