package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * 
 * @author Tin Jukić
 *
 */
public class BarChart {
	private List<XYValue> listXYValues;
	private String xDescription;
	private String yDescription;
	private int yMin;
	private int yMax;
	private int yDistance;
	
	/**
	 * Constructor.
	 * @param listXYValues
	 * @param xDescription
	 * @param yDescription
	 * @param yMin
	 * @param yMax
	 * @param yDistance
	 * @throws IllegalArgumentException when yMin == 0 or yMax < yMin
	 */
	public BarChart(List<XYValue> listXYValues, String xDescription, String yDescription, int yMin, int yMax, int yDistance) throws IllegalArgumentException {
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		if(yMin < 0)
			throw new IllegalArgumentException();
		this.yMin = yMin;
		if(yMax <= yMin)
			throw new IllegalArgumentException();
		this.yMax = yMax;
		this.yDistance = yDistance;
		this.listXYValues = listXYValues;
		for(XYValue value : listXYValues)
			if(value.getY() < yMin)
				throw new IllegalArgumentException();
	}

	public List<XYValue> getListXYValues() {
		return listXYValues;
	}

	public String getxDescription() {
		return xDescription;
	}

	public String getyDescription() {
		return yDescription;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getyDistance() {
		return yDistance;
	}
}
