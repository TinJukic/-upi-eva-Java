package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Prints current symbol or changes it.
 * @author Tin Jukić
 *
 */
public class Symbol implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.equals("")) {
			env.writeln("Wrong number of arguments.");
			return ShellStatus.CONTINUE;
		}
		
		String[] userInput = arguments.split(" ");
		
		if(userInput.length == 1) {
			if (userInput[0].equals("PROMPT")) {
				env.write("Symbol for PROMPT is '");
				env.write(env.getPromptSymbol().toString());
				env.writeln("'");
			} else if (userInput[0].equals("MULTILINE")) {
				env.write("Symbol for MULTILINE is '");
				env.write(env.getMultilineSymbol().toString());
				env.writeln("'");
			} else if (userInput[0].equals("MORELINES")) {
				env.write("Symbol for MORELINES is '");
				env.write(env.getMorelinesSymbol().toString());
				env.writeln("'");
			} else {
				env.writeln("Wrong number of arguments.");
			}
		} else if(userInput.length == 2) {
			if (userInput[0].equals("PROMPT")) {
				env.write("Symbol for PROMPT changed from '");
				env.write(env.getPromptSymbol().toString());
				env.write("'");
				env.setPromptSymbol(userInput[1].charAt(0));
				env.write("to '");
				env.write(env.getPromptSymbol().toString());
				env.writeln("'");
			} else if (userInput[0].equals("MULTILINE")) {
				env.write("Symbol for MULTILINE changed from '");
				env.write(env.getMultilineSymbol().toString());
				env.write("'");
				env.setMultilineSymbol(userInput[1].charAt(0));
				env.write("to '");
				env.write(env.getMultilineSymbol().toString());
				env.writeln("'");
			} else if (userInput[0].equals("MORELINES")) {
				env.write("Symbol for MORELINES changed from '");
				env.write(env.getMorelinesSymbol().toString());
				env.write("'");
				env.setMorelinesSymbol(userInput[1].charAt(0));
				env.write("to '");
				env.write(env.getMorelinesSymbol().toString());
				env.writeln("'");
			} else {
				env.writeln("Wrong number of arguments.");
			}
		} else {
			env.writeln("Wrong number of arguments.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Prints current symbol or changes it.";
		List<String> l = new ArrayList<>();
		l.add(d);
		return l;
	}

}
