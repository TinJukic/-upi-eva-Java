package hr.fer.zemris.java.hw06.shell;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShellTest {

	@Test
	void getMoreLinesSymbolTest() {
		try {
			Shell shell = new Shell();
			assertEquals('\\', shell.getMorelinesSymbol());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void getMultiLineSymbolTest() {
		try {
			Shell shell = new Shell();
			assertEquals('|', shell.getMultilineSymbol());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void getPromptSymbolTest() {
		try {
			Shell shell = new Shell();
			assertEquals('>', shell.getPromptSymbol());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void setMoreLinesSymbolTest() {
		try {
			Shell shell = new Shell();
			shell.setMorelinesSymbol('!');
			assertEquals('!', shell.getMorelinesSymbol());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void setMultiLineSymbolTest() {
		try {
			Shell shell = new Shell();
			shell.setMultilineSymbol('#');
			assertEquals('#', shell.getMultilineSymbol());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void setPromptSymbolTest() {
		try {
			Shell shell = new Shell();
			shell.setPromptSymbol('$');
			assertEquals('$', shell.getPromptSymbol());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkCatCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Cat.class, shell.commands().get("cat").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkCharsetCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Charsets.class, shell.commands().get("charsets").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkCopyCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Copy.class, shell.commands().get("copy").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkExitCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Exit.class, shell.commands().get("exit").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkHelpCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Help.class, shell.commands().get("help").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkHexdumpCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Hexdump.class, shell.commands().get("hexdump").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkLsCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Ls.class, shell.commands().get("ls").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkMkdirCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Mkdir.class, shell.commands().get("mkdir").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkSymbolCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Symbol.class, shell.commands().get("symbol").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@Test
	void checkTreeCommandsTest() {
		try {
			Shell shell = new Shell();
			assertEquals(Tree.class, shell.commands().get("tree").getClass());
		} catch(Exception e) {
			fail("Not yet implemented");
		}
	}

}
