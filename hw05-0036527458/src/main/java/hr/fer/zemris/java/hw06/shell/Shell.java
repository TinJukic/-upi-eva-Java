package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Abstraction of the shell usage.
 * @author Tin Jukić
 *
 */
public class Shell implements Environment {
	private String PROMPTSYMBOL = ">";
	private String MULTILINESYMBOL = "|";
	private String MORELINESSYMBOL = "\\";

	private Scanner sc;
	private SortedMap<String, ShellCommand> commandsMap;

	/**
	 * Shell constructor.
	 */
	public Shell() {
		sc = new Scanner(System.in);
		commandsMap = new TreeMap<>();

		commandsMap.put("charsets", new Charsets());
		commandsMap.put("cat", new Cat());
		commandsMap.put("ls", new Ls());
		commandsMap.put("tree", new Tree());
		commandsMap.put("copy", new Copy());
		commandsMap.put("mkdir", new Mkdir());
		commandsMap.put("hexdump", new Hexdump());
		commandsMap.put("exit", new Exit());
		commandsMap.put("symbol", new Symbol());
		commandsMap.put("help", new Help());
	}

	/**
	 * Reads user input.
	 * @return user input as String
	 * @throws ShellIOException if something goes wrong
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			write(PROMPTSYMBOL + " ");
			String[] userInput = sc.nextLine().split(" ");

			// if a line ends with '/', more lines are expected
			while (userInput[userInput.length - 1].equals(MORELINESSYMBOL)) {
				write(MULTILINESYMBOL + " ");
				String[] newUserInput = sc.nextLine().split(" ");
				String[] previousUserInput = userInput;
				String[] updatedUserInput = new String[newUserInput.length + previousUserInput.length - 1];

				int i = 0;
				for (String s : previousUserInput)
					if (!s.equals(MORELINESSYMBOL))
						updatedUserInput[i++] = s;

				for (String s : newUserInput)
					updatedUserInput[i++] = s;

				userInput = updatedUserInput;
			}

			String returnString = "";
			for (int i = 0; i < userInput.length; i++) {
				returnString += userInput[i];
				if (i < userInput.length - 1)
					returnString += " ";
			}

			return returnString;
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	/**
	 * Writes to user without new line.
	 * @throws ShellIOException if something goes wrong
	 */
	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	/**
	 * Writes to user with new line.
	 * @throws ShellIOException if something goes wrong
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	/**
	 * Enables usage of shell commands.
	 * @return sorted map of all commands provided by shell
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commandsMap);
	}

	/**
	 * @return character for multiline symbol
	 */
	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL.charAt(0);
	}

	/**
	 * Changes character for multiline symbol.
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol.toString();
	}

	/**
	 * @return character for prompt symbol
	 */
	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL.charAt(0);
	}

	/**
	 * Changes character for prompt symbol.
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol.toString();
	}

	/**
	 * @return character for morelines symbol
	 */
	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL.charAt(0);
	}

	/**
	 * Changes character for morelines symbol.
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol.toString();
	}
}
