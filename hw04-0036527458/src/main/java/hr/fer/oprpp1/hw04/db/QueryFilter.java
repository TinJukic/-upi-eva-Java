package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class which test the given query.
 * @author Tin Jukić
 *
 */
public class QueryFilter implements IFilter {
	private List<ConditionalExpression> conditionalExpressionList;

	/**
	 * Public constructor for QueryFilter class.
	 * @param conditionalExpressionList list representation of the given query
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressionList) {
		this.conditionalExpressionList = conditionalExpressionList;
	}
	
	/**
	 * Test the given record and the expression from the conditionalExpressionList.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		// for each given expression
		for(ConditionalExpression expression : conditionalExpressionList) {
			String value = expression.getStringLiteral();
			IFieldValueGetter getter = expression.getFieldGetter();
			IComparisonOperator compOp = expression.getComparisonOperator();
			
			if(!compOp.satisfied(getter.get(record), value)) return false;
		}
		
		return true;
	}

}
