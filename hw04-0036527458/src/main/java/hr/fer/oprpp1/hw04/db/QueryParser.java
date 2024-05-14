package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a parser of query statement.
 * @author Tin Jukić
 *
 */
public class QueryParser {
	private List<LexerToken> tokenList;
	private Lexer lexer;
	
	/**
	 * Public constructor for Parser class.
	 * @param query query given by the user
	 */
	public QueryParser(String query) {
		// query keyword is not sent into parser
		lexer = new Lexer(query);
		tokenList = lexer.nextToken();
	}
	
	/**
	 * 
	 * @return true if the query just has jmbag="...", false otherwise
	 */
	public boolean isDirectQuery() {
		if(this.tokenList.size() != 3) return false;
		if(this.tokenList.get(0).getType().equals(TokenType.JMBAG)
			&& this.tokenList.get(1).getType().equals(TokenType.EQUALS)
			&& this.tokenList.get(2).getType().equals(TokenType.VALUE)) return true;
		
		return false;
	}
	
	/**
	 * 
	 * @return String representation of the queried JMBAG, if the query is direct
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) return null;
		return this.tokenList.get(2).getValue();
	}
	
	/**
	 * 
	 * @return list of ConditionalExpression to be processed
	 */
	public List<ConditionalExpression> getQuery() {
		List<ConditionalExpression> conditionalExpressionList = new ArrayList<>();
		
		for(int i = 0; i < this.tokenList.size(); i++) {
			// variables needed to create new ConditionalExpression
			IFieldValueGetter getter = null;
			String value = null;
			IComparisonOperator compOp = null;
			
			if(this.tokenList.get(i).getType().equals(TokenType.AND))
				i++;
			
			if(this.tokenList.get(i).getType().equals(TokenType.JMBAG)) {
				getter = FieldValueGetters.JMBAG;
			} else if(this.tokenList.get(i).getType().equals(TokenType.FIRST_NAME)) {
				getter = FieldValueGetters.FIRST_NAME;
			} else if(this.tokenList.get(i).getType().equals(TokenType.LAST_NAME)) {
				getter = FieldValueGetters.LAST_NAME;
			} else {
				return null;
			}
			
			i++;
			
			if(this.tokenList.get(i).getType().equals(TokenType.EQUALS)) {
				compOp = ComparisonOperators.EQUALS;
			} else if(this.tokenList.get(i).getType().equals(TokenType.DIFFERENT)) {
				compOp = ComparisonOperators.NOT_EQUALS;
			} else if(this.tokenList.get(i).getType().equals(TokenType.GREATER)) {
				compOp = ComparisonOperators.GREATER;
			} else if(this.tokenList.get(i).getType().equals(TokenType.GREATER_OR_EQUAL)) {
				compOp = ComparisonOperators.GREATER_OR_EQUALS;
			} else if(this.tokenList.get(i).getType().equals(TokenType.LESS)) {
				compOp = ComparisonOperators.LESS;
			} else if(this.tokenList.get(i).getType().equals(TokenType.LESS_OR_EQUAL)) {
				compOp = ComparisonOperators.LESS_OR_EQUALS;
			} else if(this.tokenList.get(i).getType().equals(TokenType.LIKE)) {
				compOp = ComparisonOperators.LIKE;
			} else {
				return null;
			}
			
			i++;
			
			if(this.tokenList.get(i).getType().equals(TokenType.VALUE)) {
				value = this.tokenList.get(i).getValue();
			} else {
				return null;
			}
			
			if(getter != null && value != null && compOp != null)
				conditionalExpressionList.add(new ConditionalExpression(getter, value, compOp));
			else {
				return null;
			}
		}
		
		return conditionalExpressionList;
	}
}
