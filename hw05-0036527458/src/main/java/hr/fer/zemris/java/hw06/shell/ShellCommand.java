package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface which every shell command must implement.
 * @author Tin Jukić
 *
 */
public interface ShellCommand {
	/**
	 * Executes given shell command and writes result to user.
	 * @param env environment which is to be used
	 * @param arguments arguments passed by user
	 * @return ShellStatus of the command
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * 
	 * @return name of the desired command
	 */
	String getCommandName();
	
	/**
	 * 
	 * @return list of everything supported by the given command
	 */
	List<String> getCommandDescription();
}
