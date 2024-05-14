package hr.fer.zemris.java.gui.layouts;

/**
 * 
 * @author Tin Jukić
 *
 */
public class RCPosition {

	private final int row;
	private final int column;

	/**
	 * Parses the given text and generates new RCPosition object.
	 * @param text user input
	 * @throws CalcLayoutException
	 */
	public static RCPosition parse(String text) throws CalcLayoutException {
		// parses text given by user
		String[] userInputSplit = text.split(",");
		
		if(userInputSplit.length != 2)
			throw new CalcLayoutException();

		// remove blanks (if there are any)
		for (int i = 0; i < userInputSplit.length; i++)
			userInputSplit[i] = userInputSplit[i].strip();

		int row = 0;
		int column = 0;

		row = Integer.parseInt(userInputSplit[0]);
		column = Integer.parseInt(userInputSplit[1]);

		if (row < 1 || row > 5 || column < 1 || column > 7 || (row == 1 && (column > 1 && column < 6)))
			throw new CalcLayoutException();

		return new RCPosition(row, column);
	}

	/**
	 * Public constructor.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter method.
	 * 
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter method.
	 * 
	 * @return column
	 */
	public int getColumn() {
		return column;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof RCPosition))
			return false;
		
		RCPosition other = (RCPosition) obj;
		
		if(this.row == other.getRow() && this.column == other.getColumn())
			return true;
		
		return false;
	}

}
