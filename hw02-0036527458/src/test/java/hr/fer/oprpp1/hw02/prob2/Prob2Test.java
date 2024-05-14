package hr.fer.oprpp1.hw02.prob2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

class Prob2Test {

	// EXTRA - used for the third task
	@Test
	public void testNullDocument() {
		try {
			new SmartScriptParser(null);
			fail();
		} catch (SmartScriptParserException e) {
			/* exception is expected */ }
	}

	@Disabled
	@Test
	public void testExample1() throws FileNotFoundException {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		assertEquals(d.getClass(), DocumentNode.class);
	}

	@Disabled
	@Test
	public void testExample1read() throws FileNotFoundException {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		TextNode tn = (TextNode) d.getChild(0);
		assertEquals(tn.getText(), text.toString());
	}

	@Disabled
	@Test
	public void testExample2() throws FileNotFoundException {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		assertEquals(d.getClass(), DocumentNode.class);
	}

	@Disabled
	@Test
	public void testExample2read() throws FileNotFoundException {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		TextNode tn = (TextNode) d.getChild(0);
		assertEquals(tn.getText(), "Ovo je \nsve jedan {$ text node\n");
	}

	@Disabled
	@Test
	public void testExample3() throws FileNotFoundException {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		TextNode tn = (TextNode) d.getChild(0);
		assertEquals(tn.getText(), "Ovo je \nsve jedan \\{$text node\n");
	}

	@Disabled
	@Test
	public void testExample4() throws FileNotFoundException {
		try {
			String text = readExample(4);
			SmartScriptParser parser = new SmartScriptParser(text);
			parser.parse();
			fail();
		} catch (SmartScriptParserException e) {
			/* exception is expected */ }
	}

	@Disabled
	@Test
	public void testExample5() throws FileNotFoundException {
		try {
			String text = readExample(5);
			SmartScriptParser parser = new SmartScriptParser(text);
			parser.parse();
			fail();
		} catch (SmartScriptParserException e) {
			/* exception is expected */ }
	}

	@Disabled
	@Test
	public void testExample6() throws FileNotFoundException {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		TextNode tn = (TextNode) d.getChild(0);
		assertEquals(tn.getText(), "Ovo je OK ");
		parser.parse();
		EchoNode en = (EchoNode) d.getChild(1);

		assertEquals(2, d.numberOfChildren());
		assertEquals(en.getClass(), EchoNode.class);
	}

	@Disabled
	@Test
	public void testExample7() throws FileNotFoundException {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		TextNode tn = (TextNode) d.getChild(0);
		assertEquals(tn.getText(), "Ovo je isto OK ");
		parser.parse();
		EchoNode en = (EchoNode) d.getChild(1);

		assertEquals(2, d.numberOfChildren());
		assertEquals(en.getClass(), EchoNode.class);
	}

	@Disabled
	@Test
	public void testExample8() throws FileNotFoundException {
		try {
			String text = readExample(8);
			SmartScriptParser parser = new SmartScriptParser(text);
			parser.parse();
			DocumentNode d = parser.getDocumentNode();

			TextNode tn = (TextNode) d.getChild(0);
			assertEquals(tn.getText(), "Ovo se ruši ");
			parser.parse();
			EchoNode en = (EchoNode) d.getChild(1);

			assertEquals(2, d.numberOfChildren());
			assertEquals(en.getClass(), EchoNode.class);
			
			fail();
		} catch (SmartScriptParserException e) {
			/* exception is expected */ }
	}

	@Disabled
	@Test
	public void testExample9() {
		try {
			String text = readExample(9);
			SmartScriptParser parser = new SmartScriptParser(text);
			parser.parse();
			DocumentNode d = parser.getDocumentNode();

			TextNode tn = (TextNode) d.getChild(0);
			assertEquals(tn.getText(), "Ovo se ruši ");
			parser.parse();
			EchoNode en = (EchoNode) d.getChild(1);

			assertEquals(2, d.numberOfChildren());
			assertEquals(en.getClass(), EchoNode.class);
			
			fail();
		} catch (SmartScriptParserException e) {
			/* exception is expected */ }
	}
	
	@Disabled
	@Test
	public void testExample10() {
		String text = readExample(10);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		ForLoopNode fln = (ForLoopNode) d.getChild(0);
		assertEquals(fln.getClass(), ForLoopNode.class);
		assertEquals(1, d.numberOfChildren());
		parser.parse();
		assertEquals(1, fln.numberOfChildren());
		assertEquals(fln.getChild(0).getClass(), TextNode.class);
		TextNode tn = (TextNode) fln.getChild(0);
		assertEquals(tn.getText(), "Ispis\r\n");
	}
	
	@Disabled
	@Test
	public void testExample11() {
		String text = readExample(11);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		ForLoopNode fln = (ForLoopNode) d.getChild(0);
		assertEquals(fln.getClass(), ForLoopNode.class);
		assertEquals(1, d.numberOfChildren());
		parser.parse();
		assertEquals(1, fln.numberOfChildren());
		assertEquals(fln.getChild(0).getClass(), TextNode.class);
		TextNode tn = (TextNode) fln.getChild(0);
		assertEquals(tn.getText(), "Ispis\r\n");
	}
	
	@Disabled
	@Test
	public void testExample12() {
		// @function must not appear in for loop
		try {
			String text = readExample(12);
			SmartScriptParser parser = new SmartScriptParser(text);
			parser.parse();
			DocumentNode d = parser.getDocumentNode();
	
			ForLoopNode fln = (ForLoopNode) d.getChild(0);
			assertEquals(fln.getClass(), ForLoopNode.class);
			assertEquals(1, d.numberOfChildren());
			parser.parse();
			assertEquals(1, fln.numberOfChildren());
			assertEquals(fln.getChild(0).getClass(), TextNode.class);
			TextNode tn = (TextNode) fln.getChild(0);
			assertEquals(tn.getText(), "Ispis\r\n");
			
			fail();
		} catch(SmartScriptParserException e) { /* exception is expected */ }
	}
	
	@Disabled
	@Test
	public void testExample13() {
		String text = readExample(13);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		ForLoopNode fln = (ForLoopNode) d.getChild(0);
		assertEquals(fln.getClass(), ForLoopNode.class);
		assertEquals(1, d.numberOfChildren());
		parser.parse();
		assertEquals(1, fln.numberOfChildren());
		assertEquals(fln.getChild(0).getClass(), TextNode.class);
		TextNode tn = (TextNode) fln.getChild(0);
		assertEquals(tn.getText(), "Ispis\r\n");
	}
	
	@Disabled
	@Test
	public void testExample14() {
		String text = readExample(14);
		SmartScriptParser parser = new SmartScriptParser(text);
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		EchoNode en = (EchoNode) d.getChild(0);
		assertEquals(en.getClass(), EchoNode.class);
		assertEquals(1, d.numberOfChildren());
	}
	
	@Test
	public void testExampleForMVN1() {
		SmartScriptParser parser = new SmartScriptParser("{$= i + 1 + 10 @cos $}");
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		EchoNode en = (EchoNode) d.getChild(0);
		assertEquals(en.getClass(), EchoNode.class);
		assertEquals(1, d.numberOfChildren());
	}

	@Test
	public void testExampleForMVN2() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR sco_re \"-1\"10 \"1\" $}\r\n"
				+ "	Ispis\r\n"
				+ "{$      END $}");
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		ForLoopNode fln = (ForLoopNode) d.getChild(0);
		assertEquals(fln.getClass(), ForLoopNode.class);
		assertEquals(1, d.numberOfChildren());
		parser.parse();
		assertEquals(1, fln.numberOfChildren());
		assertEquals(fln.getChild(0).getClass(), TextNode.class);
		TextNode tn = (TextNode) fln.getChild(0);
		assertEquals(tn.getText(), "Ispis\r\n");
	}
	
	@Test
	public void testExampleForMVN3() {
		// @function must not appear in for loop
		try {
			SmartScriptParser parser = new SmartScriptParser("{$ FOR i @function 10 $}\r\n"
					+ "	Ispis\r\n"
					+ "{$END$}");
			parser.parse();
			DocumentNode d = parser.getDocumentNode();
	
			ForLoopNode fln = (ForLoopNode) d.getChild(0);
			assertEquals(fln.getClass(), ForLoopNode.class);
			assertEquals(1, d.numberOfChildren());
			parser.parse();
			assertEquals(1, fln.numberOfChildren());
			assertEquals(fln.getChild(0).getClass(), TextNode.class);
			TextNode tn = (TextNode) fln.getChild(0);
			assertEquals(tn.getText(), "Ispis\r\n");
			
			fail();
		} catch(SmartScriptParserException e) { /* exception is expected */ }
	}
	
	@Test
	public void testExampleForMVN4() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i 0 10 $}\r\n"
				+ "	Ispis\r\n"
				+ "{$END$}");
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		ForLoopNode fln = (ForLoopNode) d.getChild(0);
		assertEquals(fln.getClass(), ForLoopNode.class);
		assertEquals(1, d.numberOfChildren());
		parser.parse();
		assertEquals(1, fln.numberOfChildren());
		assertEquals(fln.getChild(0).getClass(), TextNode.class);
		TextNode tn = (TextNode) fln.getChild(0);
		assertEquals(tn.getText(), "Ispis\r\n");
	}
	
	@Test
	public void testExampleForMVN5() throws FileNotFoundException {
		SmartScriptParser parser = new SmartScriptParser("Ovo je \r\n"
				+ "sve jedan \\{$ text node\r\n"
				+ "");
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		assertEquals(d.getClass(), DocumentNode.class);
	}
	
	@Test
	public void testExampleForMVN6() throws FileNotFoundException {
		try {
			SmartScriptParser parser = new SmartScriptParser("Ovo se ruši s iznimkom \\n \r\n"
					+ "jer je escape ilegalan ovdje.\r\n"
					+ "");
			parser.parse();
			fail();
		} catch (SmartScriptParserException e) {
			/* exception is expected */ }
	}
	
	@Test
	public void testExampleForMVN7() throws FileNotFoundException {
		try {
			SmartScriptParser parser = new SmartScriptParser("Ovo se ruši \"s iznimkom \\n\" \r\n"
					+ "jer je escape ilegalan ovdje.\r\n"
					+ "");
			parser.parse();
			fail();
		} catch (SmartScriptParserException e) {
			/* exception is expected */ }
	}
	
	@Test
	public void testExampleForMVN8() throws FileNotFoundException {
		SmartScriptParser parser = new SmartScriptParser("Ovo je OK {$ = \"String ide\r\n"
				+ "u više redaka\r\n"
				+ "čak tri\" $}\r\n"
				+ "");
		parser.parse();
		DocumentNode d = parser.getDocumentNode();

		TextNode tn = (TextNode) d.getChild(0);
		assertEquals(tn.getText(), "Ovo je OK ");
		parser.parse();
		EchoNode en = (EchoNode) d.getChild(1);

		assertEquals(2, d.numberOfChildren());
		assertEquals(en.getClass(), EchoNode.class);
	}
	
	@Test
	public void testExampleForMVN9() throws FileNotFoundException {
		try {
			SmartScriptParser parser = new SmartScriptParser("Ovo se ruši {$ = \"String ide\r\n"
					+ "u više \\{$ redaka\r\n"
					+ "čak tri\" $}\r\n"
					+ "");
			parser.parse();
			DocumentNode d = parser.getDocumentNode();

			TextNode tn = (TextNode) d.getChild(0);
			assertEquals(tn.getText(), "Ovo se ruši ");
			parser.parse();
			EchoNode en = (EchoNode) d.getChild(1);

			assertEquals(2, d.numberOfChildren());
			assertEquals(en.getClass(), EchoNode.class);
			
			fail();
		} catch (SmartScriptParserException e) {
			/* exception is expected */ }
	}
	
	private String readExample(int n) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
			if (is == null)
				throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch (IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}

}
