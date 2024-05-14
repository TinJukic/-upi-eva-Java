package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents conditional expression statement
 * @author Tin Jukić
 *
 */
public class ConditionalExpression {
	private IFieldValueGetter iFieldGetter;
	private String stringLiteral;
	private IComparisonOperator iComparisonOperator;
	
	/**
	 * Public constructor
	 * @param iFieldValueGetter
	 * @param stringLiteral
	 * @param iComparisomOperator
	 */
	public ConditionalExpression(IFieldValueGetter iFieldValueGetter, String stringLiteral, IComparisonOperator iComparisomOperator) {
		this.iFieldGetter = iFieldValueGetter;
		this.stringLiteral = stringLiteral;
		this.iComparisonOperator = iComparisomOperator;
	}

	/**
	 * Getter for iFieldValue
	 * @return iFieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return iFieldGetter;
	}

	/**
	 * Getter for stringLieral
	 * @return stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for comparisonOperator
	 * @return comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return iComparisonOperator;
	}
	
	
}
