package hr.fer.oprpp1.hw05.crypto;

/**
 * Utility class which has implemented necessary methods for encryption and decryption.
 * @author Tin Jukic
 *
 */
public class Util {

	/**
	 * Makes a byte array from the given String.
	 * @param keyText given String
	 * @return byte array
	 * @throws IllegalArgumentException if the given String contains illegal value or if it's odd-sized
	 */
	public static byte[] hextobyte(String keyText) throws IllegalArgumentException {
		// must support both uppercase letters and lowercase letters
		if(keyText.length() == 0)
			return new byte[0];
		
		for(int i = 0; i < keyText.length(); i++) {
			if((keyText.charAt(i) < '0' || keyText.charAt(i) > '9') &&
				(keyText.charAt(i) < 'a' || keyText.charAt(i) > 'f') &&
				(keyText.charAt(i) < 'A' || keyText.charAt(i) > 'F') ||
				(keyText.length() % 2 != 0)) throw new IllegalArgumentException();
				
		}
		
		byte[] byteArray = new byte[keyText.length() / 2];
		int j = -1;
		
		// ovdje je i djeljivo s 2 zbog provjere iznad
		for(int i = 0; i < keyText.length(); i++) {
			j++;
			
			int first = Character.digit(keyText.charAt(i), 16);
			i++;
			int second = Character.digit(keyText.charAt(i), 16);
			
			byteArray[j] = (byte) ((first << 4) + second);
		}
		
		return byteArray;
	}
	
	/**
	 * Makes a String from the given byte array.
	 * @param bytearray given byte array
	 * @return String representation of the given byte array
	 */
	public static String bytetohex(byte[] bytearray) {
		// should use lowercase letters
		if(bytearray.length == 0)
			return "";
		
		String s = "";
		
		for(byte b : bytearray) {
			char hexDigit[] = new char[2]; // uvijek moraju biti 2 znaka
			hexDigit[0] = Character.forDigit((int) (b >> 4) & 0xF, 16);
			hexDigit[1] = Character.forDigit((int) (b & 0xF), 16);
			
			s += new String(hexDigit);
		}
		
		return s;
	}
}
