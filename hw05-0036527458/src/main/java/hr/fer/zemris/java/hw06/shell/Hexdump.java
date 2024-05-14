package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Prints hex representation of the opened file.
 * 
 * @author Tin Jukić
 *
 */
public class Hexdump implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.equals("")) {
			env.writeln("Wrong number of arguments.");
			return ShellStatus.CONTINUE;
		}

		String[] userInput = arguments.split(" ");

		// check if the given document has quotes
		String[] helperArray = new String[userInput.length];
		int index = 0;
		for (int i = 0; i < userInput.length; i++) {
			if (userInput[i].charAt(0) == '"') {
				String quoteString = "";
				for (; i < userInput.length; i++) {
					char[] userInputChar = userInput[i].toCharArray();
					char[] quoteStringChar = new char[userInput[i].length() - 1];
					if (userInputChar[0] == '"') {
						for (int j = 1; j < userInputChar.length; j++) {
							char inputChar = userInputChar[j];
							quoteStringChar[j - 1] = inputChar;
						}
						String inputCharString = String.copyValueOf(quoteStringChar);
						quoteString += inputCharString;
					} else if (userInputChar[userInputChar.length - 1] == '"') {
						for (int j = 0; j < userInputChar.length - 1; j++) {
							char inputChar = userInputChar[j];
							quoteStringChar[j] = inputChar;
						}
						String inputCharString = String.copyValueOf(quoteStringChar);
						quoteString += inputCharString;
						helperArray[index++] = quoteString;
						quoteString = "";
						break;
					} else {
						quoteString += userInput[i];
					}
					quoteString += " ";
				}
			} else {
				helperArray[index++] = userInput[i];
			}
		}

		int size = 0;
		for (String s : helperArray)
			if (s != null)
				size++;

		userInput = new String[size];
		index = 0;
		for (String s : helperArray)
			if (s != null)
				userInput[index++] = s;
		
		if(userInput.length == 1) {
			Path p = Paths.get(userInput[0]);
			if(Files.isDirectory(p))
				env.writeln("Directory was passed instead of file name.");
			else {
				int brojIspisa = 0;
				
				try(InputStream is = Files.newInputStream(p)) {
					while(true) {
						byte[] buff = new byte[16];
						int r = is.read(buff);
						if(r<1) {
							break;
						}
						
						// nije se doslo do kraja
						String prviElement = "";
						
						for(int i = 0; i < 8 - Integer.valueOf(brojIspisa).toString().length(); i++)
							prviElement += "0";
						prviElement += Integer.valueOf(brojIspisa).toString();
						prviElement += ": ";
						brojIspisa += 10;
						env.write(prviElement);
						
						String drugiElement = "";
						int len = -1;
						for(byte b : buff) {
							len++;
							String s = "";
							
							char hexDigit[] = new char[2]; // uvijek moraju biti 2 znaka
							hexDigit[0] = Character.forDigit((int) (b >> 4) & 0xF, 16);
							hexDigit[1] = Character.forDigit((int) (b & 0xF), 16);
							
							for(int i = 0; i < hexDigit.length; i++)
								if(Character.isLetter(hexDigit[i]))
									hexDigit[i] = Character.toUpperCase(hexDigit[i]);
							
							if((new String(hexDigit)).equals("00"))
								s += "  ";
							else
								s += new String(hexDigit);
							
							if(len < 7)
								s += " ";
							
							drugiElement += s;
							
							if(len == 7)
								break;
						}
						env.write(drugiElement);
						env.write("|");
						
						len++;
						
						String treciElement = "";
						for(; len < buff.length; len++) {
							byte b = buff[len];
							String s = "";
							
							char hexDigit[] = new char[2]; // uvijek moraju biti 2 znaka
							hexDigit[0] = Character.forDigit((int) (b >> 4) & 0xF, 16);
							hexDigit[1] = Character.forDigit((int) (b & 0xF), 16);
							
							for(int i = 0; i < hexDigit.length; i++)
								if(Character.isLetter(hexDigit[i]))
									hexDigit[i] = Character.toUpperCase(hexDigit[i]);
							
							if((new String(hexDigit)).equals("00"))
								s += "  ";
							else
								s += new String(hexDigit);
							
							s += " ";
							
							treciElement += s;
						}
						env.write(treciElement);
						
						env.write("| ");
						
						String cetvrtiElement = "";
						for(byte b : buff) {
							if((b < 32 || b > 127) && b != 0)
								cetvrtiElement += ".";
							else
								cetvrtiElement += (char)b;
						}
						env.writeln(cetvrtiElement);
					}
				} catch(IOException e) {
					env.writeln("Error occurred.");
				} catch(IllegalArgumentException e) {
					env.writeln("Error occurred.");
				}
			}
		} else {
			env.writeln("Wrong number of arguments.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Prints hex representation of the opened file.";
		List<String> l = new ArrayList<>();
		l.add(d);
		return l;
	}

}
