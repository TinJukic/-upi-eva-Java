package hr.fer.oprpp1.custom.scripting.elems;

/**
 * 
 * @author Tin Jukić
 * @ElementFunction
 *
 */

public class ElementFunction extends Element {
	private String name;
	
	/**
	 * 
	 * Constructor
	 * @param name passed as an argument
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * @return name as a String
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
