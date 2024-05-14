package hr.fer.oprpp1.custom.scripting.elems;

/**
 * 
 * @author Tin Jukić
 * @ElementString
 *
 */

public class ElementString extends Element {
	private String value;
	
	/**
	 * 
	 * Constructor
	 * @param value passed as an argument
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * @return value as a String
	 */
	@Override
	public String asText() {
		return this.value;
	}
}
