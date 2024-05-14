package hr.fer.oprpp1.custom.scripting.elems;

/**
 * 
 * @author Tin Jukić
 * @ElementConstantInteger
 *
 */

public class ElementConstantInteger extends Element {
	private int value;
	
	/**
	 * 
	 * Constructor
	 * @param value passed as an argument
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * @return String representation of int value
	 */
	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
}
