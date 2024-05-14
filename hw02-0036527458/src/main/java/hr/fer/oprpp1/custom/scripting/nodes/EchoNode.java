package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 * 
 * @author Tin Jukić
 * @EchoNode a node representing a command which generates some textual output dynamically
 *
 */

public class EchoNode extends Node {
	private Element[] elements;
	
	/**
	 * 
	 * Constructor
	 * @param elements
	 * @throws SmartScriptParserException
	 */
	public EchoNode(Element[] elements) throws SmartScriptParserException {
		if(elements.length == 0) throw new SmartScriptParserException();
		
		this.elements = elements;
	}
	
	/**
	 * 
	 * @return elements (array)
	 */
	public Element[] getElements() {
		return this.elements;
	}
}
