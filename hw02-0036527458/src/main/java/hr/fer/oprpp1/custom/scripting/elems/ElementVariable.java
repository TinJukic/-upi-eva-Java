package hr.fer.oprpp1.custom.scripting.elems;

/**
 * 
 * @author Tin Jukić
 * @ElementVariable 
 *
 */

public class ElementVariable extends Element {
	private String name;
	
	/**
	 * 
	 * Constructor
	 * @param name passed as an argument
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * @return String
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
