package hr.fer.zemris.java.gui.charts;

/**
 * 
 * @author Tin Jukić
 *
 */
public class XYValue {
	private int x;
	private int y;
	
	/**
	 * Constructor.
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
