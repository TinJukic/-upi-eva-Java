package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * 
 * used to determine which element it is
 * @author Tin Jukić
 *
 */

public class LexerToken {
	private LexerTokenType type;
	private Object value;
	
	/**
	 * 
	 * Constructor
	 * @param type
	 * @param value
	 */
	LexerToken(LexerTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * 
	 * @return type of the lexer token
	 */
	public LexerTokenType getLexerTokenType() {
		return this.type;
	}
	
	/**
	 * 
	 * @return value of the lexer token
	 */
	public Object getLexerTokenValue() {
		return this.value;
	}
}
