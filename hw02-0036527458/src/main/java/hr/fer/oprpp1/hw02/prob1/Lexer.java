package hr.fer.oprpp1.hw02.prob1;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * 
 * @author Tin Jukić
 *
 */

public class Lexer {
	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private ArrayIndexedCollection collection; // save every new token inside Collection
	private LexerState lexerState;
	
	
	// konstruktor prima ulazni tekst koji se tokenizira
	public Lexer(String text) {
		int size = text.length();
		this.data = new char[size];
		this.collection = new ArrayIndexedCollection();
		this.currentIndex = 0;
		this.lexerState = LexerState.BASIC;
		
		for(int i = 0; i < size; i++) this.data[i] = text.charAt(i);
	}
	
	
	// generira i vraća sljedeći token
	// baca LexerException ako dođe do pogreške
	/**
	 * @return the next token from the input string
	 * @throws LexerException
	 */
	public Token nextToken() throws LexerException {
		if(this.token != null && this.token.getType().equals(TokenType.EOF)) throw new LexerException();
		
		String currentElement = "";
		
		boolean firstNoSpaceFound = false;
		for(; this.currentIndex < this.data.length; this.currentIndex++) {
			char e = this.data[this.currentIndex];
			if(!Character.isWhitespace(e) && !firstNoSpaceFound) {
				firstNoSpaceFound = true;
			}
			
			if(firstNoSpaceFound)
				if(!Character.isWhitespace(e)) {
					currentElement += e;
					this.data[this.currentIndex] = ' ';
					currentElement.length();
				} else break;
		}
		if(this.lexerState.equals(LexerState.BASIC)) {
			if(!currentElement.equals("")) {
				var currentCharArray = currentElement.toCharArray();
				boolean needsToBreak = false;
				String currentString = "";
				String currentLong = "";
				
				for(int i = 0; i < currentCharArray.length; i++) {
					if(needsToBreak) {
						this.currentIndex = 0;
						for(int j = 0; j < currentCharArray.length; j++)
							if(currentCharArray[j] != ' ') {
								this.data[this.currentIndex + j] = currentCharArray[j];
							}
						break;
					}
					
					if(!Character.isWhitespace(currentCharArray[i])) {
						String firstElementIs = "";
						if(Character.isLetter(currentCharArray[i])) {
							firstElementIs = "letter";
							currentString += currentCharArray[i];
							currentCharArray[i] = ' ';
						} else if(currentCharArray[i] == '\\') {
							// if the value is \ (escape key, if it is before number -> number becomes letter then)
							// '\\' is like one char
							currentCharArray[i] = ' ';
							if(i + 1 < currentCharArray.length && currentCharArray[i + 1] != '\\') {
								try {
									i++;
									Long.parseLong(String.valueOf(currentCharArray[i]));
									firstElementIs = "letter";
									currentString += currentCharArray[i];
									currentCharArray[i] = ' ';
								} catch(Exception e) { throw new LexerException(); }
							} else if(i + 1 >= currentCharArray.length) { throw new LexerException(); }
						} else {
							try {
								// if the current char is long
								Long.parseLong(String.valueOf(currentCharArray[i]));
								firstElementIs = "long";
								currentLong += currentCharArray[i];
								currentCharArray[i] = ' ';
							} catch(Exception e) {
								// if the current char is neither long nor letter
								// saving new token...
//								if(currentCharArray[i] == '#') setState(LexerState.EXTENDED);
								
								this.collection.add(new Token(TokenType.SYMBOL, currentCharArray[i]));
								currentCharArray[i] = ' ';
								
								for(int j = i + 1; j < currentCharArray.length; j++) {
									this.currentIndex = 0;
									this.data[this.currentIndex + j] = currentCharArray[j];
								}
								break;
							}
						}
						
						if(i >= currentCharArray.length - 1) {
							// last element
							if(firstElementIs.equals("letter")) {
								// saving new token...
								this.collection.add(new Token(TokenType.WORD, currentString));
								break;
							} else if(firstElementIs.equals("long")) {
								// saving new token...
								long longValueCurrent = Long.parseLong(currentLong);
								this.collection.add(new Token(TokenType.NUMBER, longValueCurrent));
								break;
							}
						}
						
						for(int j = i + 1; j < currentCharArray.length + 1; j++) {
							// this will happen only when \\ exists
							if(j >= currentCharArray.length && firstElementIs.equals("letter")) {
								// saving new token...
								this.collection.add(new Token(TokenType.WORD, currentString));
								needsToBreak = true;
								break;
							}
							
							if(firstElementIs.equals("letter") && currentCharArray[j] != '\\') {
								if(j < currentCharArray.length && Character.isLetter(currentCharArray[j]) && !Character.isWhitespace(currentCharArray[j])) {
									currentString += currentCharArray[j];
									currentCharArray[j] = ' ';
								} else {
									// saving new token...
									this.collection.add(new Token(TokenType.WORD, currentString));
									needsToBreak = true;
									break;
								}
							} else if(j < currentCharArray.length && currentCharArray[j] == '\\' && firstElementIs.equals("letter")) {
								// if the value is \ (escape key, if it is before number -> number becomes letter then)
								// '\\' is like one char
								currentCharArray[j] = ' ';
								if(j + 1 < currentCharArray.length && currentCharArray[j + 1] != '\\') {
									try {
										j++;
										Long.parseLong(String.valueOf(currentCharArray[j]));
										currentString += currentCharArray[j];
										currentCharArray[j] = ' ';
									} catch(Exception e) { throw new LexerException(); }
								} else { 
									j++;
									currentString += currentCharArray[j];
									currentCharArray[j] = ' ';
								}
							} else {
								try {
									if(firstElementIs.equals("long") && j < currentCharArray.length && !Character.isWhitespace(currentCharArray[j]) && !Character.isLetter(currentCharArray[j])) {
										// check whether the current element could be double value
										Long.parseLong(String.valueOf(currentCharArray[j]));
										currentLong += currentCharArray[j];
										currentCharArray[j] = ' ';
									} else {
										// saving new token...
										long longValueCurrent = Long.parseLong(currentLong);
										this.collection.add(new Token(TokenType.NUMBER, longValueCurrent));
										needsToBreak = true;
										break;
									}
								} catch(Exception e) {
									try {
										//throw new LexerException();
										// if the current char is neither long nor letter
										// saving new token...
										long longValueCurrent = Long.parseLong(currentLong);
										this.collection.add(new Token(TokenType.NUMBER, longValueCurrent));
										needsToBreak = true;
										break;
									} catch(Exception error) { throw new LexerException(); }
								}
							}
						}
					}
				}
			} else this.collection.add(new Token(TokenType.EOF, null));
		} else if(this.lexerState.equals(LexerState.EXTENDED)) {
			// all elements are type word, except '#', which is type symbol
			
			if(!currentElement.equals("")) {
				var currentCharArray = currentElement.toCharArray();
				String currentString = "";
				
				for(int i = 0; i < currentCharArray.length; i++) {
					if(!Character.isWhitespace(currentCharArray[i])) {
						if(currentCharArray[i] == '#') {
							if(currentString == "") {
								this.collection.add(new Token(TokenType.SYMBOL, currentCharArray[i]));
								if(i + 1 < currentCharArray.length) i++;
							}

							for(int j = i; j < currentCharArray.length; j++) {
								this.currentIndex = 0;
								this.data[this.currentIndex + j] = currentCharArray[j];
							}
//							setState(LexerState.BASIC);
							break;
						} else {
							currentString += currentCharArray[i];
							currentCharArray[i] = ' ';
						}
					}
				}
				
				// saving new token...
				if(currentString != "")
					this.collection.add(new Token(TokenType.WORD, currentString));
				
				
			} else this.collection.add(new Token(TokenType.EOF, null));
		}
		
		this.token = (Token) this.collection.get(this.collection.size() - 1);
		return this.token; // return last token that was created
	}
	
	
	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	/**
	 * @return the last created token
	 */
	public Token getToken() {
		return (Token) this.collection.get(this.collection.size() - 1);
	}
	
	/**
	 * changes the state of the lexer, when symbol '#' appears
	 * @throws NullPointerException, if state is set to null
	 */
	public void setState(LexerState state) throws NullPointerException {
		if(state == null) throw new NullPointerException();
		this.lexerState = state;
	}
}
