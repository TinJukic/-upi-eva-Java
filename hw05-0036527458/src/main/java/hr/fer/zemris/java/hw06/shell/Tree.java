package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists all elements from the given directory in tree like structure.
 * @author Tin Jukić
 *
 */
public class Tree implements ShellCommand {

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
			treeListing(userInput[0], env, 0);
		} else {
			env.writeln("Wrong number of arguments.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Lists all elements from the given directory in tree like structure.";
		List<String> l = new ArrayList<>();
		l.add(d);
		return l;
	}
	
	// rekurzivni ulazak
	private void treeListing (String userInput, Environment env, int numberOfCalls) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(userInput))) {
			for (Path p : stream) {
				// formatted printing
				String formattedPrinting = "";
				
				for(int i = 0; i < numberOfCalls; i++)
					formattedPrinting += "  ";
				
				if (Files.isDirectory(p)) {
					formattedPrinting += p.getFileName();
					env.writeln(formattedPrinting);
					treeListing(p.toString(), env, numberOfCalls + 1);
				} else {
					formattedPrinting += p.getFileName();
					env.writeln(formattedPrinting);
				}
			}
		} catch (IOException e) {
			env.writeln("File path was not passed correctly.");
		}
	}

}
