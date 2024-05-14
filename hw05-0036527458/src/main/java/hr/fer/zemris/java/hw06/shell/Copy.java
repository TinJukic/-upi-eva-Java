package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Copies given file to given location.
 * 
 * @author Tin Jukic
 *
 */
public class Copy implements ShellCommand {

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

		if (userInput.length == 2) {
			// read file and save it to another location
			Path ip = Paths.get(userInput[0]);
			Path op = Paths.get(userInput[1]);
			
			if(Files.isDirectory(ip)) {
				env.writeln("First argument must be file.");
				return ShellStatus.CONTINUE;
			}
			
			if(Files.isDirectory(op)) {
				// program must enable / and \ (depending on the operating system)
				if(userInput[0].contains("/")) {
					String[] firstPath = userInput[0].split("/");
					String fileName = firstPath[firstPath.length - 1];
					userInput[1] += "/" + fileName;
					op = Paths.get(userInput[1]);
				} else if(userInput[0].contains("\\")) {
					String[] firstPath = userInput[0].split("\\\\");
					String fileName = firstPath[firstPath.length - 1];
					userInput[1] += "\\" + fileName;
					op = Paths.get(userInput[1]);
				}
			}
			
			if(op.toFile().exists()) {
				env.writeln("File which you want to copy already exists in the desired directory. Would you like to copy it anyway? Answer y for yes or n for no.");
				String answer = env.readLine();
				if(answer.equals("n")) {
					env.writeln("File copying has been canceled.");
					return ShellStatus.CONTINUE;
				}
			}
			
			BufferedReader br;
			try {
				br = new BufferedReader(
						new InputStreamReader(new BufferedInputStream(Files.newInputStream(ip)), "UTF-8"));
				
				Writer bw = new BufferedWriter(
						new OutputStreamWriter(new BufferedOutputStream(Files.newOutputStream(op)), "UTF-8"));

				br.lines().forEach(line -> {
					try {
						bw.write(line);
						bw.write(System.getProperty("line.separator"));
					} catch (IOException e) {
						env.writeln("Error occured while writing text");
					}
				});
				
				bw.close();
				br.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				env.writeln("Passed arguments were wrong.");
			}
			
		} else {
			env.writeln("Wrong number of arguments.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Copies given file to given location.";
		List<String> l = new ArrayList<>();
		l.add(d);
		l.add("If the file name is not given in desired location, previous file name will be used.");
		return l;
	}

}
