package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Lexer for processing input strings
 * @author Tin Jukić
 *
 */
public class Lexer {
	private char[] query;
	private List<LexerToken> tokenOutput;
	
	/**
	 * Public constructor for Lexer class.
	 * @param inputData
	 */
	public Lexer(String query) {
		this.query = query.toCharArray();
		this.tokenOutput = new ArrayList<>();
	}
	
	/**
	 * 
	 * @return the list of tokens from the given query String
	 */
	public List<LexerToken> nextToken() {
		int queryLength = this.query.length;
		boolean isWhiteSpace = true;
		List<String> razdvojeniElementi = new ArrayList<>();
		String element = "";
		
		for(int i = 0; i < queryLength; i++) {
			if(!Character.isWhitespace(this.query[i])) {
				if(isWhiteSpace) isWhiteSpace = false;
				element += this.query[i];
				this.query[i] = ' ';
			} else if(!isWhiteSpace) {
				isWhiteSpace = true;
				if(!element.equals(""))
					razdvojeniElementi.add(element);
				element = "";
			}
		}
		
		if(!element.equals("") && !isWhiteSpace) {
			isWhiteSpace = true;
			razdvojeniElementi.add(element);
			element = "";
		}
		
		for(int i = 0; i < razdvojeniElementi.size(); i++) {
			char[] elementChar = razdvojeniElementi.get(i).toCharArray();
			
			for(int j = 0; j < elementChar.length; j++) {
				if(elementChar[j] == 'f') {
					char[] firstName = "firstName".toCharArray();
					boolean jeFirstName = true;
					for(int k = 0; k < firstName.length; k++)
						if(elementChar[j + k] != firstName[k])
							jeFirstName = false;
					
					if(jeFirstName) {
						j += firstName.length - 1;
						tokenOutput.add(new LexerToken(TokenType.FIRST_NAME, "firstName"));
					}
				} else if(elementChar[j] == 'l') {
					char[] lastName = "lastName".toCharArray();
					boolean jeLastName = true;
					for(int k = 0; k < lastName.length; k++)
						if(elementChar[j + k] != lastName[k])
							jeLastName = false;
					
					if(jeLastName) {
						j += lastName.length - 1;
						tokenOutput.add(new LexerToken(TokenType.LAST_NAME, "lastName"));
					}
				} else if(elementChar[j] == 'j') {
					char[] jmbag = "jmbag".toCharArray();
					boolean jeJmbag = true;
					for(int k = 0; k < jmbag.length; k++)
						if(elementChar[j + k] != jmbag[k])
							jeJmbag = false;
					
					if(jeJmbag) {
						j += jmbag.length - 1;
						tokenOutput.add(new LexerToken(TokenType.JMBAG, "jmbag"));
					}
				} else if(elementChar[j] == 'L') {
					char[] like = "LIKE".toCharArray();
					boolean jeLike = true;
					for(int k = 0; k < like.length; k++)
						if(elementChar[j + k] != like[k])
							jeLike = false;
					
					if(jeLike) {
						j += like.length - 1;
						tokenOutput.add(new LexerToken(TokenType.LIKE, "LIKE"));
					}
				} else if(Character.toUpperCase(elementChar[j]) == 'A') {
					char[] and = "AND".toCharArray();
					boolean jeAnd = true;
					for(int k = 0; k < and.length; k++)
						if(Character.toUpperCase(elementChar[j + k]) != and[k])
							jeAnd = false;
					
					if(jeAnd) {
						j += and.length - 1;
						tokenOutput.add(new LexerToken(TokenType.AND, "AND"));
					}
				} else if(elementChar[j] == '"') {
					String value = "";
					for(j = j + 1; j < elementChar.length; j++) {
						if(elementChar[j] != '"') {
							value += elementChar[j];
						} else {
							tokenOutput.add(new LexerToken(TokenType.VALUE, value));
						}
					}
				} else if(elementChar[j] == '=') {
					tokenOutput.add(new LexerToken(TokenType.EQUALS, "="));
				} else if(elementChar[j] == '<') {
					if(elementChar.length != 1 && elementChar[j + 1] == '=') {
						j++;
						tokenOutput.add(new LexerToken(TokenType.LESS_OR_EQUAL, "<="));
					} else {
						tokenOutput.add(new LexerToken(TokenType.LESS, "<"));
					}
				} else if(elementChar[j] == '>') {
					if(elementChar.length != 1 && elementChar[j + 1] == '=') {
						j++;
						tokenOutput.add(new LexerToken(TokenType.GREATER_OR_EQUAL, ">="));
					} else {
						tokenOutput.add(new LexerToken(TokenType.GREATER, ">"));
					}
				} else if(elementChar[j] == '!') {
					if(elementChar.length != 1 && elementChar[j + 1] == '=') {
						j++;
						tokenOutput.add(new LexerToken(TokenType.DIFFERENT , "!="));
					}
				} else {
					// error
				}
			}
			
		}
		
		return tokenOutput;
	}
}
