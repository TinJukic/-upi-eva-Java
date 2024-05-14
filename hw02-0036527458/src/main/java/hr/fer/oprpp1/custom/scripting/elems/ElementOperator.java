package hr.fer.oprpp1.custom.scripting.elems;

/**
 * 
 * @author Tin Jukić
 * @ElementOperator
 *
 */

public class ElementOperator extends Element {
	private String symbol;
	
	/**
	 * 
	 * Constructor
	 * @param symbol passed as an argument
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return symbol as a String
	 */
	@Override
	public String asText() {
		return this.symbol;
	}
}
