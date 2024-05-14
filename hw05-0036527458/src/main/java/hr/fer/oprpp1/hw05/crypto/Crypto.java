package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class used for encrypting and decrypting of documents.
 * @author Tin Jukić
 *
 */
public class Crypto {

	private static boolean encrypt; // mijenja se ovisno o tome što korisnik želi napraviti s podatkom
	
	/**
	 * Calculates SHA-256 and checks it.
	 * @param documentToCheck name of the document to check
	 */
	public void checksha(String documentToCheck) {
		Path p = Paths.get("src/main/resources/" + documentToCheck);
		String calculatedSHA = "";
		
		System.out.printf("Please provide expected sha-256 digest for %s:%n", documentToCheck);
		
		Scanner sc = new Scanner(System.in);
		String userSHA = sc.nextLine();
		
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			
			try(InputStream is = Files.newInputStream(p)) {
				byte[] buff = new byte[1024 * 4]; // 4 KB
				
				calculatedSHA = Util.bytetohex(sha.digest(is.readAllBytes()));
				
//				while(true) {
//					
//					int r = is.read(buff);
//					if(r<1) {
//						calculatedSHA = Util.bytetohex(sha.digest());
//						break;
//					}
//					
//					// nije se doslo do kraja
//					sha.update(buff);
//				}
			} catch(IOException e) {
				
			} catch(IllegalArgumentException e) {
				
			} catch(Exception e) {
				
			}
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		if(calculatedSHA.equals(userSHA))
			System.out.printf("Digesting completed. Digest of %s matches expected digest.%n", documentToCheck);
		else
			System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s%n", documentToCheck, calculatedSHA);
		
		sc.close();
	}
	
	/**
	 * Encrypts the given document.
	 * @param originalDocument document to be encrypted
	 * @param encryptedDocument encrypted document
	 */
	public void encrypt(String originalDocument, String encryptedDocument) {
		encrypt = true;
		Scanner sc = new Scanner(System.in);
		Path p = Paths.get("src/main/resources/" + originalDocument);
		Path o = Paths.get("src/main/resources/" + encryptedDocument);
		
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		String keyText = sc.nextLine();
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		String ivText = sc.nextLine();
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
		try(InputStream is = Files.newInputStream(p); OutputStream os = Files.newOutputStream(o)) {
			byte[] buff = new byte[1024 * 4]; // 4 KB
			
			while(true) {
				int r = is.read(buff);
				if(r<1) {
					// the end of file
					os.write(cipher.doFinal());
					break;
				}
				
//				cipher.getParameters().getEncoded();
				
				// nije se doslo do kraja
				os.write(cipher.update(buff));
			}
			
		} catch(IOException e) {
			
		} catch(IllegalArgumentException e) {
			
		} catch(Exception e) {
			
		}
		
		System.out.println("Encryption completed. Generated file hw05.crypted.pdf based on file hw05.pdf.");
		
		sc.close();
	}
	
	/**
	 * Decrypts given document.
	 * @param encryptedDocument encrypted document given by user
	 * @param decryptedDocument decrypted document
	 */
	public void decrypt(String encryptedDocument, String decryptedDocument) {
		encrypt = false;
		Scanner sc = new Scanner(System.in);
		Path p = Paths.get("src/main/resources/" + encryptedDocument);
		Path o = Paths.get("src/main/resources/" + decryptedDocument);
		
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		String keyText = sc.nextLine();
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		String ivText = sc.nextLine();
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
		try(InputStream is = Files.newInputStream(p); OutputStream os = Files.newOutputStream(o)) {
			byte[] buff = new byte[1024 * 4]; // 4 KB
			
			while(true) {
				int r = is.read(buff);
				if(r<1) {
					// the end of file
					os.write(cipher.doFinal());
					break;
				}
				
//				cipher.getParameters().getEncoded();
				
				// nije se doslo do kraja
				os.write(cipher.update(buff));
			}
			
		} catch(IOException e) {
			
		} catch(IllegalArgumentException e) {
			
		} catch(Exception e) {
			
		}
		
		System.out.println("Decryption completed. Generated file hw05orig.pdf based on file hw05.crypted.pdf.");
		
		sc.close();
	}
}
