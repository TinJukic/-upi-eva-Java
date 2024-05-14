package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Terminates the program.
 * @author Tin Jukić
 *
 */
public class Exit implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.equals(""))
			return ShellStatus.TERMINATE;
		
		env.writeln("Wrong number of arguments.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Terminates the shell program.";
		List<String> l = new ArrayList<>();
		l.add(d);
		return l;
	}

}
