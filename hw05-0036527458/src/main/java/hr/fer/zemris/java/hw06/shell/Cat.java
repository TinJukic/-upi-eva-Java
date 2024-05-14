package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads data from the given file and writes its content on screen.
 * 
 * @author Tin Jukić
 *
 */
public class Cat implements ShellCommand {

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

		if (userInput.length == 1) {
			// The first argument is path to some file and is mandatory
			Path p = Paths.get(userInput[0]);
			Charset defaultCharset = Charset.defaultCharset();

			if (Files.isDirectory(p))
				env.writeln("Directory was passed instead of file name.");
			else {
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new BufferedInputStream(Files.newInputStream(p)), defaultCharset));
					br.lines().forEach(line -> env.writeln(line));
					br.close();
				} catch (IOException e) {
					env.writeln("File path was not passed correctly.");
					return ShellStatus.CONTINUE;
				}
			}
		} else if (userInput.length == 2) {
			// The second argument is charset name that should be used to interpret chars
			// from bytes
			Path p = Paths.get(userInput[0]);
			
			try {
				Charset charset = Charset.forName(userInput[1]);
				
				if (Files.isDirectory(p))
					env.write("Directory was passed instead of file name.");
				else {
					try {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(new BufferedInputStream(Files.newInputStream(p)), charset));
						br.lines().forEach(line -> env.writeln(line));
						br.close();
					} catch (IOException e) {
						env.writeln("File path was not passed correctly.");
						return ShellStatus.CONTINUE;
					}
				}
			} catch(UnsupportedCharsetException e) {
				env.writeln("Unsupported charset was used.");
			}

		} else {
			env.writeln("Wrong number of arguments.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Reads data from the given file and writes its content on screen.";
		List<String> l = new ArrayList<>();
		l.add(d);
		return l;
	}

}
