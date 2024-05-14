package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Writes directory listing on screen.
 * 
 * @author Tin Jukić
 *
 */
public class Ls implements ShellCommand {

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
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(userInput[0]))) {
				for (Path p : stream) {
					// formatted printing
					String formattedPrinting = "";
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Path path = Paths.get(p.toString());
					BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
							LinkOption.NOFOLLOW_LINKS);
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

					if (attributes.isDirectory()) {
						formattedPrinting += "drwx ";
					} else {
						if(p.getFileName().endsWith(".exe") || p.getFileName().endsWith(".sh") || p.getFileName().endsWith(".sh~"))
							formattedPrinting += "-rwx ";
						else
							formattedPrinting += "-rw- ";
					}
					
					String fileSize = Long.toString(attributes.size());
					String fileSizePrint = "";
					for(int i = 0; i < 10 - fileSize.length(); i++)
						fileSizePrint += " ";
					fileSizePrint += fileSize;
					
					formattedPrinting += fileSizePrint + " ";
					formattedPrinting += formattedDateTime + " ";
					formattedPrinting += p.getFileName();
					
					env.writeln(formattedPrinting);
				}
			} catch (IOException e) {
				env.writeln("File path was not passed correctly.");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Wrong number of arguments.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Writes directory listing on screen.";
		List<String> l = new ArrayList<>();
		l.add(d);
		return l;
	}

}
