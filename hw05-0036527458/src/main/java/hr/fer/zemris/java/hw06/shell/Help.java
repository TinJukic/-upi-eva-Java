package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Prints available methods with descriptions.
 * @author Tin Jukić
 *
 */
public class Help implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.equals("")) {
			var commands = env.commands();
			commands.forEach((k, v) -> env.writeln(k.toString()));
		} else if(arguments.split(" ").length == 1) {
			var commands = env.commands();
			if(commands.get(arguments) != null) {
				ShellCommand sc = env.commands().get(arguments);
				env.writeln(sc.getCommandName());
				sc.getCommandDescription().forEach(e -> env.writeln(" - " + e));
			} else {
				env.writeln("No such command exists.");
			}
		} else {
			env.writeln("Wrong number of arguments.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Prints current symbol or changes it.";
		List<String> l = new ArrayList<>();
		l.add(d);
		return l;
	}

}
