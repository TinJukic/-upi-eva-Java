package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 * 
 * @author Tin Jukić
 * @ForLoopNode a node representing a single for-loop construct
 *
 */

public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; // can be null
	
	/**
	 * 
	 * Constructor with 4 elements
	 * @param variable cannot be null
	 * @param startExpression cannot be null
	 * @param endExpression cannot be null
	 * @param stepExpression can be null
	 * @throws SmartScriptParseException
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) throws SmartScriptParserException {
		if(variable == null || startExpression == null || endExpression == null) throw new SmartScriptParserException();
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * 
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return this.variable;
	}
	
	/**
	 * 
	 * @return start expression
	 */
	public Element getStartExpression() {
		return this.startExpression;
	}
	
	/**
	 * 
	 * @return end expression
	 */
	public Element getEndExpression() {
		return this.endExpression;
	}
	
	/**
	 * 
	 * @return step expression (can be null)
	 */
	public Element getStepExpression() {
		return this.stepExpression;
	}
}
