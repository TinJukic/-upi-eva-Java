package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Loads given database into program.
 * @author Tin Jukić
 *
 */
public class StudentDB {
	private List<String> list = null;
	
	// my program
	public StudentDatabase readFromFile() {
		try {
//			this.list = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			this.list = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
			
//			for(var el : this.list) System.out.println(el);
			
			if(this.list != null) return new StudentDatabase(this.list);
		} catch(IOException e) {
			System.out.println("Could not read the given file.");
		} catch(IllegalGradeValueException e) {
			System.out.println("Final grade is not between 1 and 5.");
		} catch(NumberFormatException e) {
			System.out.println("Final grade is not Number value.");
		} catch(DuplicateElementsException e) {
			System.out.println("Duplicate element.");
		} catch(Exception e) {
			System.out.println("Something went wrong...");
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		StudentDB db = new StudentDB();
//		db.readFromFile();
//	}
}
