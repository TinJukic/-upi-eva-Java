package hr.fer.oprpp1.hw02.prob2;

import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class SmartScriptTester {
	private Paths filepath;
	
	public static void main(String[] args) {
		String docBody = "....";
//		String docBody = new String(
//				Files.readAllBytes(Paths.get()),
//					StandardCharsets.UTF_8
//						);
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
//			parser.parse();
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody);
	}
}
