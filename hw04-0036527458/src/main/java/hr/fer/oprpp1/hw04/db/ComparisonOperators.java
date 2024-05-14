package hr.fer.oprpp1.hw04.db;

/**
 * Class which has all comparison operators
 * @author Tin Jukić
 *
 */
public class ComparisonOperators {
	
	/**
	 * Operator less
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> {
		if(v1.compareTo(v2) < 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Operator less or equals
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> {
		if(v1.compareTo(v2) <= 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Operator greater
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> {
		if(v1.compareTo(v2) > 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Operator greater or equals
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> {
		if(v1.compareTo(v2) >= 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Operator equals
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> {
		if(v1.compareTo(v2) == 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Operator not equals
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> {
		if(v1.compareTo(v2) != 0) {
			return true;
		}
		return false;
	};
	
	/**
	 * Operator like
	 */
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		int duljinaDva = v2.length();
		String[] dijelovi = new String[2];
		
		int number = 0;
		for(int i = 0; i < v2.length(); i++) {
			if(v2.charAt(i) == '*') number++;
		}
		
		if(number >= 2)
			return false;
		
		if(v2.charAt(0) == '*') {
			String dodaj = "";
			dijelovi[0] = dodaj;
			
			char[] el = v2.toCharArray();
			for(int i = 1; i < el.length; i++) {
				dodaj += el[i];
			}
			
			dijelovi[1] = dodaj;
			
		} else if(v2.charAt(duljinaDva - 1) == '*') {
			String dodaj = "";
			dijelovi[1] = dodaj;
			
			char[] el = v2.toCharArray();
			for(int i = 0; i < el.length - 1; i++) {
				dodaj += el[i];
			}
			
			dijelovi[0] = dodaj;
		} else {
			dijelovi = v2.split("\\*");
		}
		
		if(dijelovi.length != 2) return false; // provjeri
		if(dijelovi[0].length() >= v1.length() || dijelovi[1].length() >= v1.length()) return false;
		
		// pocetak
		for(int i = 0; i < dijelovi[0].length(); i++) {
			if(dijelovi[0].charAt(i) != v1.charAt(i)) 
				return false;
		}
		
		// kraj
		for(int i = 0; i < dijelovi[1].length(); i++) {
			if(dijelovi[1].charAt(dijelovi[1].length() - 1 - i) != v1.charAt(v1.length() - 1 - i))
				return false;
		}
		
		if(dijelovi[0].length() + dijelovi[1].length() > v1.length())
			return false;
		
		return true;
	};

}
