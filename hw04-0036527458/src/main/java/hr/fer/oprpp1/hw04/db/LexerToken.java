package hr.fer.oprpp1.hw04.db;

/**
 * Token class for Lexer
 * @author Tin Jukic
 *
 */
public class LexerToken {
	private TokenType type;
	private String value;
	
	/**
	 * Constructor which creates LexerToken object
	 * @param type
	 * @param value
	 */
	public LexerToken(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter method.
	 * @return Token type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Getter method.
	 * @return Token value
	 */
	public String getValue() {
		return value;
	}
}
