package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * 
 * @author Tin Jukić
 * @TextNode a node representing a piece of textual data
 *
 */

public class TextNode extends Node {
	private String text;
	
	/**
	 * 
	 * Constructor
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * 
	 * @return text as a String
	 */
	public String getText() {
		return this.text;
	}
}
