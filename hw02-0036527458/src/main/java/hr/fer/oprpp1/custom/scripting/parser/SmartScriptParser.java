package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * 
 * Parser which uses lexer for the production of tokens
 * @author Tin Jukić
 *
 */

public class SmartScriptParser {
	private Lexer lexer;
	private ObjectStack objectStack;
	private Node rootNode;
	
	/**
	 * 
	 * Constructor
	 * @param documentBody
	 */
	public SmartScriptParser(String documentBody) {
		try {
			this.lexer = new Lexer(documentBody);
			this.objectStack = new ObjectStack();
			this.rootNode = new DocumentNode();
			
			objectStack.push(rootNode); // initializing main document node - making it the root element at the given node tree
		} catch(Exception e) { throw new SmartScriptParserException(); }
	}
	
	/**
	 * 
	 * Helper function to determine what type of element is to be used
	 * @param value
	 * @return new Element of the appropriate type
	 * @throws SmartScriptParserException
	 */
	private Element forStateElement(String value, String current) throws SmartScriptParserException {
		// check the next element
		Lexer helperLexer = new Lexer(value);
		LexerToken token = helperLexer.nextToken();
		if(token.getLexerTokenType().equals(LexerTokenType.STRING)) {
			return new ElementString((String) token.getLexerTokenValue());
		} else if(token.getLexerTokenType().equals(LexerTokenType.INTEGER)) {
			return new ElementConstantInteger((int) token.getLexerTokenValue());
		} else if(token.getLexerTokenType().equals(LexerTokenType.DOUBLE)) {
			return new ElementConstantDouble((double) token.getLexerTokenValue());
		} else if(token.getLexerTokenType().equals(LexerTokenType.OPERATOR)) {
			return new ElementOperator((String) token.getLexerTokenValue());
		} else if(token.getLexerTokenType().equals(LexerTokenType.FUNCTION) && current.equals("echo")) {
			// functions cannot be in for
			return new ElementFunction((String) token.getLexerTokenValue());
		} else if(token.getLexerTokenType().equals(LexerTokenType.VARIABLE)) {
			return new ElementFunction((String) token.getLexerTokenValue());
		} else throw new SmartScriptParserException();
	}
	
	/**
	 * 
	 * parses string given in the constructor
	 * @throws SmartScriptParserException
	 */
	public void parse() throws SmartScriptParserException {
		try {
			// check what type is this token
			LexerToken nextToken = this.lexer.nextToken();
			this.rootNode = (Node) objectStack.peek(); // to get the last created node
			
			if(nextToken.getLexerTokenType().equals(LexerTokenType.STRING)) {
				TextNode tn = new TextNode((String) nextToken.getLexerTokenValue());
				this.rootNode.addChildNode(tn);
			} else if(nextToken.getLexerTokenType().equals(LexerTokenType.FOR)) {
				// razmaci ??????
				
				String tokenValue = (String) nextToken.getLexerTokenValue();
				char[] valueChar = tokenValue.toCharArray();
				ElementVariable variable = null;
				Element startExpression = null;
				Element endExpression = null;
				Element stepExpression = null;
				
				boolean zadnjiProlaz = false;
				do {
					boolean firstNoSpaceFound = false;
					String value = "";
					boolean firstElementIsLetter = false;
					boolean needsToBreak = false;
					boolean string = false;
					
					for(int i = 0; i < valueChar.length; i++) {
						if(needsToBreak) break;
						
						if(!Character.isWhitespace(valueChar[i]) && !firstNoSpaceFound) firstNoSpaceFound = true;
						
						if(firstNoSpaceFound) {
							for(int j = i; j < valueChar.length; j++) {
								if(!Character.isWhitespace(valueChar[j])) {
									var el = valueChar[j];
									
									if(string && valueChar[j] == '"') {
										string = false;
										valueChar[j] = ' ';
										needsToBreak = true;
										break;
									}
									if(valueChar[j] == '"' && !string) {
										string = true;
										valueChar[j] = ' ';
									}
									else {
										if(valueChar[j] == '"' && !string) break;
										value += valueChar[j];
										valueChar[j] = ' ';
									}
								} else {
									needsToBreak = true;
									break;
								}
							}
						}
							
					}
					
					if(firstNoSpaceFound && value != "") {
						if(variable == null) {
							// check
							if(!firstElementIsLetter && Character.isLetter(value.charAt(0))) {
								firstElementIsLetter = true;
							} else if(!firstElementIsLetter && !Character.isLetter(value.charAt(0))) throw new SmartScriptParserException();
							variable = new ElementVariable(value);
							firstNoSpaceFound = false;
							value = "";
						} else if(startExpression == null) {
							startExpression = forStateElement(value, "for");
							firstNoSpaceFound = false;
							value = "";
						} else if(endExpression == null) {
							endExpression = forStateElement(value, "for");
							firstNoSpaceFound = false;
							value = "";
						} else if(stepExpression == null) {
							zadnjiProlaz = true;
							stepExpression = forStateElement(value, "for");
							firstNoSpaceFound = false;
							value = "";
						}
					} else if(value == "" && (variable == null || startExpression == null || endExpression == null))
						throw new SmartScriptParserException();
					
					if(value != "" && zadnjiProlaz) throw new SmartScriptParserException();
					
					if(value == "" && !zadnjiProlaz && variable != null && startExpression != null && endExpression != null) zadnjiProlaz = true;
				} while(variable == null || startExpression == null || endExpression == null || (stepExpression == null && !zadnjiProlaz));
				
				ForLoopNode forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
				this.rootNode.addChildNode(forLoopNode);
				this.objectStack.push(forLoopNode);
			} else if(nextToken.getLexerTokenType().equals(LexerTokenType.END)) {
				this.objectStack.pop();
				if(this.objectStack.peek() == null) throw new SmartScriptParserException();
			} else if(nextToken.getLexerTokenType().equals(LexerTokenType.ECHO)) {
				try {
					ArrayIndexedCollection a = new ArrayIndexedCollection();
					char[] newData = nextToken.getLexerTokenValue().toString().toCharArray();
					boolean firstNoSpace = false;
					String value = "";
					
					for(int i = 0; i < newData.length; i++) {
						if(!firstNoSpace && !Character.isWhitespace(newData[i])) firstNoSpace = true;
						
						if(firstNoSpace && newData[i] == '"' && i + 1 < newData.length) {
							newData[i] = ' ';
							for(int j = i + 1; j < newData.length; j++) {
								if(newData[j] == '\\' && j + 1 < newData.length) {
									j++;
									// escape key
									if(newData[j] == '"') {
										value += newData[j];
									} else if(newData[j] == 'n') {
										value += '\n';
									} else if(newData[j] == 't') {
										value += '\t';
									} else if(newData[j] == 'r') {
										value += '\r';
									} else throw new SmartScriptParserException();
									
									newData[j - 1] = newData[j] = ' ';
								} else if(newData[j] != '"') {
									value += newData[j];
									newData[j] = ' ';
								} else {
									newData[j] = ' ';
									a.add(forStateElement(value, "echo"));
									firstNoSpace = false;
									value = "";
									break;
								}
							}
						} else if(firstNoSpace && Character.isWhitespace(newData[i])) {
							a.add(forStateElement(value, "echo"));
							firstNoSpace = false;
							value = "";
						} else if(firstNoSpace && !Character.isWhitespace(newData[i])) value += newData[i];
					}
					
					Object[] elements = a.toArray();
					Element[] saveElements = new Element[elements.length];
					for(int i = 0; i < saveElements.length; i++) saveElements[i] = (Element) elements[i];
					
					EchoNode en = new EchoNode(saveElements);
					this.rootNode.addChildNode(en);
				} catch(Exception e) { throw e; }
				this.rootNode = (Node) this.objectStack.peek();
			} else throw new SmartScriptParserException();
		} catch(Exception e) { throw new SmartScriptParserException(); }
	}

	public DocumentNode getDocumentNode() {
		return (DocumentNode) this.rootNode;
	}
	
	/**
	 * compares number of children that nodes have and that they are the same class of nodes
	 * @param obj (Node)
	 * @return the result of that comparison
	 */
	@Override
	public boolean equals(Object obj) {
		Node node = (Node) obj;
		Node thisNode = (Node) this.rootNode;
		
		if(node.numberOfChildren() == thisNode.numberOfChildren() && node.getClass() == thisNode.getClass()) return true;
		return false;
	}

	/**
	 * @return a String representation of the elements inside given Node
	 */
	@Override
	public String toString() {
		String string = "";
		
		DocumentNode dn = this.getDocumentNode();
		if(dn.getChild(0).getClass().equals(TextNode.class)) {
			TextNode tn = (TextNode) dn.getChild(0);
			string = tn.getText();
		} else if(dn.getChild(0).getClass().equals(EchoNode.class)) {
			EchoNode en = (EchoNode) dn.getChild(0);
			string = en.getElements().toString();
		} else if(dn.getChild(0).getClass().equals(ForLoopNode.class)) {
			ForLoopNode fln = (ForLoopNode) dn.getChild(0);
			string += fln.getVariable();
			string += ", ";
			string += fln.getStartExpression();
			string += ", ";
			string += fln.getEndExpression();
			string += ", ";
			if(fln.getStepExpression() != null) string += fln.getStepExpression();
			else string += "null";
		}
		
		return string;
	}

	/**
	 * 
	 * @return the last created node
	 */
	public Node getNode() {
		return this.rootNode;
	}
}
