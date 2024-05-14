package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * has all the states that a lexer can be in
 * @author Tin Jukić
 *
 */

public enum LexerTokenState {
	BASIC, NUMBER, FOR, OPERATOR, ECHO, TEXT, FUNCTION, VARIABLE, END
}
