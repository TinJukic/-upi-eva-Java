package hr.fer.zemris.java.hw06.shell;

/**
 * Shell program which the user uses.
 * @author Tin Jukić
 *
 */
public class MyShell {

	private static ShellStatus shellStatus = ShellStatus.CONTINUE;

	public static void main(String[] args) {
		Shell shell = new Shell();

		shell.writeln("Welcome to MyShell v 1.0");

		while (shellStatus.equals(ShellStatus.CONTINUE)) {
			try {
				String[] userInput = shell.readLine().split(" ");

				if (userInput[0].equals("charsets") || userInput[0].equals("cat") || userInput[0].equals("ls")
						|| userInput[0].equals("help") || userInput[0].equals("tree") || userInput[0].equals("copy")
						|| userInput[0].equals("mkdir") || userInput[0].equals("hexdump") || userInput[0].equals("exit")
						|| userInput[0].equals("symbol")) {
					ShellCommand sc = shell.commands().get(userInput[0]);
					String inputString = "";
					for (int i = 1; i < userInput.length; i++) {
						inputString += userInput[i];
						if (i < userInput.length - 1)
							inputString += " ";
					}

					shellStatus = sc.executeCommand(shell, inputString);
				}
			} catch (ShellIOException e) {
				shellStatus = ShellStatus.TERMINATE;
			}
		}
	}

}
