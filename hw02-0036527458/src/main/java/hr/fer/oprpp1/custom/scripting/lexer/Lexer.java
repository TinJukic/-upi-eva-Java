package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 * 
 * @author Tin Jukić
 * @Lexer for the SmartScriptParser
 *
 */

public class Lexer {
	// decides what to do
	
	private String data;
	private ArrayIndexedCollection tokenCollection;
	private char[] newData;
	private int currentIndex;
	private LexerToken token;
	
	private LexerTokenState state; // depends on the first couple of read elements
	
	/**
	 * Constructor
	 * @param data
	 * @throws SmartScriptParserException
	 */
	public Lexer(String data) throws SmartScriptParserException {
		if(data == null) throw new SmartScriptParserException();
		
		this.data = data;
		this.currentIndex = 0;
		this.state = LexerTokenState.BASIC; // default state
		this.tokenCollection = new ArrayIndexedCollection();
		
		this.newData = this.data.toCharArray(); // convert everything to chars
	}
	
	/**
	 * 
	 * @throws SmartScriptParserException
	 * @return newly created token
	 */
	public LexerToken nextToken() throws SmartScriptParserException {
		if(this.token != null && this.token.getLexerTokenType().equals(LexerTokenType.EOF)) throw new SmartScriptParserException();
		
		String currentElement = "";
		
		boolean firstNoSpaceFound = false;
		for(; this.currentIndex < this.newData.length; this.currentIndex++) {
			char e = this.newData[this.currentIndex];
			if(!Character.isWhitespace(e) && !firstNoSpaceFound) {
				firstNoSpaceFound = true;
			}
			
			if(firstNoSpaceFound)
				if(!Character.isWhitespace(e)) {
					if(currentElement.length() >= 2)
						if(currentElement.charAt(0) == '{' && currentElement.charAt(1) == '$') {
							break;
						}
					currentElement += e;
					this.newData[this.currentIndex] = ' ';
					currentElement.length();
				} else break;
		}
		
		char[] dataChar = currentElement.toCharArray();
		boolean needsToBreak = false;
		
		for(int i = 0; i < dataChar.length; i++) {
			if(needsToBreak) break;
			
			if(!Character.isWhitespace(dataChar[i]))
				if(dataChar[i] == '{' && i + 1 < dataChar.length && !(i - 1 >= 0 && dataChar[i - 1] != '\\')) {
					dataChar[i] = ' ';
					// check what is the next element (if possible)
					i++;
					if(dataChar[i] == '$' && i + 1 < this.newData.length) {
						// change the lexer state to FOR
						// must have an END at the end
						dataChar[i] = ' ';
						for(int j = this.currentIndex; j < this.newData.length; j++) {
							char element = this.newData[j];
							
							if(this.newData[j] == '=' && j + 1 < this.newData.length) {
								this.state = LexerTokenState.ECHO;
								this.currentIndex = j + 1;
								break;
							} else
							if(!Character.isWhitespace(this.newData[j]) && j + 2 < this.newData.length) {
								if(Character.toUpperCase(this.newData[j]) == 'F'
									&& Character.toUpperCase(this.newData[j + 1]) == 'O'
									&& Character.toUpperCase(this.newData[j + 2]) == 'R') {
										this.state = LexerTokenState.FOR;
										this.newData[j] = this.newData[j + 1] = this.newData[j + 2] = ' ';
										this.currentIndex = (j + 2);
										break;
								} else 
									if(Character.toUpperCase(this.newData[j]) == 'E'
									&& Character.toUpperCase(this.newData[j + 1]) == 'N'
									&& Character.toUpperCase(this.newData[j + 2]) == 'D') {
										this.state = LexerTokenState.END;
										this.newData[j] = this.newData[j + 1] = this.newData[j + 2] = ' ';
										i += (j + 2);
										for(int k = i; k < this.newData.length; k++) {
											if(!Character.isWhitespace(this.newData[k]) && k + 1 < this.newData.length) {
												if(this.newData[k] == '$' && this.newData[k + 1] == '}') {
													this.newData[k] = this.newData[k + 1] = ' ';
													i += (k + 1);
													this.tokenCollection.add(new LexerToken(LexerTokenType.END, null));
													this.state = LexerTokenState.BASIC;
												}
											}
										}
								}	
							}
						}
					}
				} else if(dataChar[i] == '@') {
					dataChar[i] = ' ';
					this.state = LexerTokenState.FUNCTION;
				} else if(dataChar[i] == '"') {
					dataChar[i] = ' ';
					this.state = LexerTokenState.VARIABLE;
				} else if(i - 1 > 0 && (dataChar[i] == '\\' || (dataChar[i - 1] == '\\' && dataChar[i] == '{'))) {
					// the escape key should be accepted
					// dataChar[i] = ' ';
					this.state = LexerTokenState.TEXT;
				} else if(dataChar[i] == '+' || dataChar[i] == '-' || dataChar[i] == '*' || dataChar[i] == '/' || dataChar[i] == '^') {
					if(i + 1 < dataChar.length) {
						try {
							Long.parseLong(String.valueOf(dataChar[i + 1]));
							this.state = LexerTokenState.NUMBER;
						}  catch(Exception e) { this.state = LexerTokenState.OPERATOR; }
					} else this.state = LexerTokenState.OPERATOR;
				} else {
					// it could be a number or a string
					for(int j = i; j < dataChar.length; j++) {
						if(!Character.isWhitespace(dataChar[j])) {
							try {
								if(dataChar[j] == '-' && j + 1 < dataChar.length) j++;
								Long.parseLong(String.valueOf(dataChar[j]));
								this.state = LexerTokenState.NUMBER;
								break;
							} catch(Exception e) {
								this.state = LexerTokenState.TEXT;
								break;
							}
						}
					}
				}
			
			// at the end of the each state, reset the state to BASIC
			if(this.state.equals(LexerTokenState.FOR)) {
				String value = "";
				
				for(int j = this.currentIndex; j < this.newData.length; j++) {
					if(this.newData[j] != '$' && j + 1 < this.newData.length && this.newData[j + 1] != '}') {
						value += this.newData[j];
						this.newData[j] = ' ';
					}
					else {
						// dataChar[j] == '&' && dataChar[j + 1] == '}'
						this.newData[j] = ' ';
						j++;
						this.newData[j] = ' ';
						this.tokenCollection.add(new LexerToken(LexerTokenType.FOR, value));
						needsToBreak = true;
						this.currentIndex = 0;
						break;
					}
				}
				
				this.state = LexerTokenState.BASIC;
				needsToBreak = true;
			} else if(this.state.equals(LexerTokenState.ECHO)) {
				String value = "";
				boolean allowedEscapeKey = false;
				
				for(int j = this.currentIndex; j < this.newData.length; j++) {
					if(this.newData[j] != '$' && j + 1 < this.newData.length && this.newData[j + 1] != '}') {
						value += this.newData[j];
					} else if(j + 1 < this.newData.length && this.newData[j] == '\\') {
						j++;
						if(this.newData[j] == '\\') {
							value += this.newData[j];
						} else if(this.newData[j] == '{') {
							allowedEscapeKey = true;
							value += this.newData[j];
						} else throw new SmartScriptParserException();
						
						this.newData[j - 1] = this.newData[j] = ' ';
					} else {
						if(allowedEscapeKey && j + 1 < this.newData.length) {
							allowedEscapeKey = false;
							value += this.newData[j];
							j++;
							value += this.newData[j];
							this.newData[j - 1] = this.newData[j] = ' ';
						} else {
							this.newData[j] = this.newData[j + 1] = ' ';
							this.tokenCollection.add(new LexerToken(LexerTokenType.ECHO, value));
							needsToBreak = true;
							this.currentIndex = 0;
							allowedEscapeKey = false;
							break;
						}
					}
				}
				
				this.state = LexerTokenState.BASIC;
				needsToBreak = true;
			} else if(this.state.equals(LexerTokenState.FUNCTION)) {
				String element = "";
				
				for(int j = i + 1; j < dataChar.length; j++) {
					if(!Character.isWhitespace(dataChar[j])) element += dataChar[j];
					else {
						this.currentIndex = 0;
						for(int k = j; k < dataChar.length; k++) this.newData[this.currentIndex + k] = dataChar[k];
						break;
					}
				}
				
				this.tokenCollection.add(new LexerToken(LexerTokenType.FUNCTION, element));
				this.state = LexerTokenState.BASIC;
				needsToBreak = true;
			} else if(this.state.equals(LexerTokenState.NUMBER)) {
				String value = "";
				boolean isDouble = false;
				
				for(int j = i; j < dataChar.length; j++) {
					if(!Character.isWhitespace(dataChar[j])) {
						if(dataChar[j] == '.') isDouble = true;
						value += dataChar[j];
						dataChar[j] = ' ';
					} else break;
				}
				
				if(isDouble) this.tokenCollection.add(new LexerToken(LexerTokenType.DOUBLE, Double.parseDouble(value)));
				else this.tokenCollection.add(new LexerToken(LexerTokenType.INTEGER, Integer.parseInt(value)));
				this.state = LexerTokenState.BASIC;
				needsToBreak = true;
			} else if(this.state.equals(LexerTokenState.TEXT)) {
				String value = "";
				for(int j = 0; j < dataChar.length; j++) {
					this.newData[j + (this.currentIndex - dataChar.length)] = dataChar[j];
				}
				
				for(int j = this.currentIndex - dataChar.length; j < this.newData.length; j++) {
					if(this.newData[j] != '@' && this.newData[j] != '{') {
						if(this.newData[j] == '\\') {
							if(j + 1 < this.newData.length && (this.newData[j + 1] == '{' || this.newData[j + 1] == '\\')) {
								j++;
								value += this.newData[j];
								this.newData[j] = ' ';
							} else {
								throw new SmartScriptParserException();
							}
						} else {
							// add the escape keys
							value += this.newData[j];
							this.newData[j] = ' ';
						}
					} else {
						this.currentIndex = 0;
//						for(int k = j; k < dataChar.length; k++) this.newData[this.currentIndex + k] = dataChar[k];
						break;
					}
				}
				
				this.tokenCollection.add(new LexerToken(LexerTokenType.STRING, value));
				this.state = LexerTokenState.BASIC;
				needsToBreak = true;
			} else if(this.state.equals(LexerTokenState.OPERATOR)) {
				this.tokenCollection.add(new LexerToken(LexerTokenType.OPERATOR, String.valueOf(dataChar[i])));
				this.state = LexerTokenState.BASIC;
				dataChar[i] = ' ';
				needsToBreak = true;
			} else if(this.state.equals(LexerTokenState.VARIABLE)) {
				String value = "";
				
				for(int j = i; j < dataChar.length; j++) {
					if(dataChar[j] != '"') value += dataChar[j];
					else {
						dataChar[j] = ' ';
						break;
					}
				}
				
				this.tokenCollection.add(new LexerToken(LexerTokenType.VARIABLE, value));
				this.state = LexerTokenState.BASIC;
				needsToBreak = true;
			} else throw new SmartScriptParserException();
		}
		
		return (LexerToken) this.tokenCollection.get(this.tokenCollection.size() - 1);
	}
	
	/**
	 * 
	 * @return token that was last created (doesn't create a new one)
	 */
	public LexerToken getToken() {
		return (LexerToken) this.tokenCollection.get(this.tokenCollection.size() - 1);
	}
}
