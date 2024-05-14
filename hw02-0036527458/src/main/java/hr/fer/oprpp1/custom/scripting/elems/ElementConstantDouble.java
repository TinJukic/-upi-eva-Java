package hr.fer.oprpp1.custom.scripting.elems;

/**
 * 
 * @author Tin Jukić
 * @ElementConstantDouble
 *
 */

public class ElementConstantDouble extends Element {
	private double value;
	
	/**
	 * 
	 * Constructor
	 * @param value passed as an argument
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * @return String representation of double value
	 */
	@Override
	public String asText() {
		return Double.toString(this.value);
	}
}
