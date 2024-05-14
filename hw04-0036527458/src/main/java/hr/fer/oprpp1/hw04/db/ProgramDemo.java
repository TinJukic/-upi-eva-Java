package hr.fer.oprpp1.hw04.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Program which the end user will use when making query.
 * User inputs query and the program prints the table of the elements from database and the number of found elements.<br><br>
 * <b>EXAMPLE:</b> <i>query jmbag = "0000000003"</i><br>
 * <b>EXAMPLE:</b> <i>query firstName < "T"</i><br>
 * <b>EXAMPLE:</b> <i>query firstName LIKE "B" and lastName > "M"</i><br> 
 * @author Tin Jukić
 *
 */
public class ProgramDemo {

	public static void main(String[] args) {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String dataRead = "";
		
		System.out.println("Welcome! Please write your query!");
		
		while(true) {
			System.out.print("> ");
			try {
				dataRead = br.readLine();
				char[] dataReadChar = dataRead.toCharArray();
				
				// if the user wants to exit the program
				if(dataRead.equals("exit"))
					break;
				
				// first word must be query
				String query = "query";
				for(int i = 0; i < query.length(); i++)
					if(dataReadChar[i] != query.charAt(i))
						throw new Exception();
				
				dataRead = "";
				
				for(int i = query.length(); i < dataReadChar.length; i++)
					dataRead += dataReadChar[i];
				
				QueryParser parser = new QueryParser(dataRead);
				List<StudentRecord> list = studentDatabase.filter(new QueryFilter(parser.getQuery()));
				
				if(parser.isDirectQuery())
					System.out.println("Using index for record retrieval.");
				
				int najduljeIme = 0;
				int najduljePrezime = 0;
				
				for(var record : list) {
					if(record.getFirstName().length() > najduljeIme) najduljeIme = record.getFirstName().length();
					if(record.getLastName().length() > najduljePrezime) najduljePrezime = record.getLastName().length();
				}
				
				if(list.size() != 0) {
					System.out.printf("+%s+%s+%s+%s+%n", "=".repeat(10 + 2), "=".repeat(najduljePrezime + 2), "=".repeat(najduljeIme + 2), "=".repeat(1 + 2));
					
					for(var record : list) {
//						System.out.printf("| %s | %s | %s | %d |%n",
//								record.getJmbag(), record.getLastName(), record.getFirstName(), record.getFinalGrade());
						
						System.out.printf("| %s | ", record.getJmbag());
						
						for(int i = 0; i <= najduljePrezime; i++) {
							if(i < record.getLastName().length()) {
								System.out.printf("%c", record.getLastName().charAt(i));
							} else {
								System.out.printf(" ");
							}
						}
						
						System.out.printf("| ");
						
						for(int i = 0; i <= najduljeIme; i++) {
							if(i < record.getFirstName().length()) {
								System.out.printf("%c", record.getFirstName().charAt(i));
							} else {
								System.out.printf(" ");
							}
						}
						
						System.out.printf("| %d |%n", record.getFinalGrade());
					}
					
					System.out.printf("+%s+%s+%s+%s+%n", "=".repeat(10 + 2), "=".repeat(najduljePrezime + 2), "=".repeat(najduljeIme + 2), "=".repeat(1 + 2));
				}
				
				System.out.printf("Records selected: %d%n%n", list.size());
			} catch(Exception e) {
				System.out.println("Your query was wrong! Please try again!");
			}
		}
		
		System.out.println("Goodbye!");
	}

}
