package hr.fer.oprpp1.hw05.crypto;

import java.util.Scanner;

/**
 * Main program which is used by user.
 * @author Tin Jukić
 *
 */
public class Main {

	public static void main(String[] args) {
		// program must ask user for key and value
		System.out.println("Please enter one of the following queries:");
		System.out.println(" checksha <file>");
		System.out.println(" encrypt <file> <encryptedFile>");
		System.out.println(" decrypt <encryptedFile> <decryptedFile>");

		Scanner sc = new Scanner(System.in);
		String[] userQuery = sc.nextLine().split(" ");
		
		Crypto c = new Crypto();

		if (userQuery[0].equals("checksha") && userQuery.length == 2) {
			c.checksha(userQuery[1]);
		} else if (userQuery[0].equals("encrypt") && userQuery.length == 3) {
			c.encrypt(userQuery[1], userQuery[2]);
		} else if (userQuery[0].equals("decrypt") && userQuery.length == 3) {
			c.decrypt(userQuery[1], userQuery[2]);
		} else {
			System.out.println("Wrong query!");
		}
		
		sc.close();
	}

}
