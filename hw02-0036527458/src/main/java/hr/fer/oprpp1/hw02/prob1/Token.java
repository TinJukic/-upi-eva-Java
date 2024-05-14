package hr.fer.oprpp1.hw02.prob1;

/**
 * Class for tokens.
 * @author Tin Jukić
 *
 */
public class Token {
	private TokenType tokenType;
	private Object value;
	
	/**
	 * Constructor
	 * @param type type of the token
	 * @param value value of the token
	 */
	public Token(TokenType type, Object value) {
		this.tokenType = type;
		this.value = value;
	}
	
	/**
	 * Getter
	 * @return value of the token
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Getter
	 * @return token type
	 */
	public TokenType getType() {
		return this.tokenType;
	}
}
