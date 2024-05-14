package hr.fer.zemris.java.hw06.shell;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists all supported charsets.
 * @author Tin Jukić
 *
 */
public class Charsets implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.equals("")) {
			var c = Charset.availableCharsets();
			c.forEach((k, v) -> env.writeln(c.get(k).toString()));
		} else {
			env.writeln("Wrong number of arguments.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		String d = "Lists all supported charsets.";
		List<String> l = new ArrayList<>();
		l.add(d);
		return l;
	}

}
