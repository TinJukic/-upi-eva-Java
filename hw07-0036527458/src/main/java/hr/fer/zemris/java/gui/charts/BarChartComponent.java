package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * 
 * @author Tin Jukić
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	private BarChart referenceBarChart;
	
	/**
	 * Gets the reference bar chart.
	 * @param referenceBarChart
	 */
	public BarChartComponent(BarChart referenceBarChart) {
		this.referenceBarChart = referenceBarChart;
	}
	
	/**
	 * Drawing component
	 * @param g
	 * @return this component
	 */
	public JComponent drawComponent(Graphics g) {
		System.out.println("Drawing components...");
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAT = g2d.getTransform();
		Rectangle containerDimension = this.getBounds();
		
		// drawing string
		g2d.drawString(this.referenceBarChart.getxDescription(), (int) (containerDimension.width / 2.0) - 2 * this.referenceBarChart.getxDescription().length(), containerDimension.height + 20);
		
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		g2d.drawString(this.referenceBarChart.getyDescription(), (int) (-containerDimension.height / 2) - this.referenceBarChart.getyDescription().length(), 25);
		g2d.setTransform(defaultAT);
		
		int yDistance = this.referenceBarChart.getyDistance();
		int yMin = this.referenceBarChart.getyMin();
		int yMax = this.referenceBarChart.getyMax();
		int yCount = yMax + yDistance;
		System.out.println(yCount);
		int yHeight = (int) ((containerDimension.height - 75) / yCount);
		
		var referenceBarChartXYList = this.referenceBarChart.getListXYValues();
		int referenceBarChartSize = referenceBarChartXYList.size();
		int xListRef[] = new int[referenceBarChartSize];
		int yListRef[] = new int[referenceBarChartSize];
		
		for(int i = 0; i < referenceBarChartSize; i++) {
			xListRef[i] = referenceBarChartXYList.get(i).getX();
			yListRef[i] = referenceBarChartXYList.get(i).getY();
		}
		
		int xMax = xListRef[0];
		for(int i = 1; i < xListRef.length; i++)
			if(xMax < xListRef[i])
				xMax = xListRef[i];
		int componentSizeX = (int) ((containerDimension.width - 120) / (xMax));
		
		int xList[] = new int[xMax + 1];
		int yList[] = new int[xMax + 1];
		
		for(int i = 0; i < referenceBarChartSize; i++) {
			int x = xListRef[i];
			int y = yListRef[i];
			
			xList[x] = x;
			yList[x] = y;
		}
		
		for(int i = 0; i < xList.length; i++) {
			if(i != 0)
				g2d.drawLine(100 + i * componentSizeX, containerDimension.height - 70, 100 + i * componentSizeX, containerDimension.height - 80);
			if(i == xMax - 1 || i == xMax)
				g2d.drawLine(100 + (i + 1) * componentSizeX, containerDimension.height - 70, 100 + (i + 1) * componentSizeX, containerDimension.height - 80);
			g2d.drawString(String.valueOf(i + 1), (100 + componentSizeX / 2) + i * componentSizeX, containerDimension.height - 50);
			g2d.setPaint(Color.orange);
			g2d.fillRect(100 + (i - 1) * componentSizeX, (containerDimension.height - 75) - yHeight * yList[i], componentSizeX, yHeight * yList[i]);
			g2d.setPaint(Color.black);
			if(i != 0)
				g2d.drawRect(100 + (i - 1) * componentSizeX, (containerDimension.height - 75) - yHeight * yList[i], componentSizeX, yHeight * yList[i]);
		}
		
		// drawing line
		g2d.drawLine(95, containerDimension.height - 75, containerDimension.width, containerDimension.height - 75);
		int xLineXArray[] = {containerDimension.width - 10, containerDimension.width - 10, containerDimension.width};
		int xLineYArray[] = {containerDimension.height - 70, containerDimension.height - 80, containerDimension.height - 75};
		g2d.drawPolygon(xLineXArray, xLineYArray, 3);
		g2d.drawLine(100, 40, 100, containerDimension.height - 70);
		g2d.setBackground(Color.black);
		g2d.fillPolygon(xLineXArray, xLineYArray, 3);
		int yLineXArray[] = {100, 95, 105};
		int yLineYArray[] = {40, 50, 50};
		g2d.drawPolygon(yLineXArray, yLineYArray, 3);
		g2d.setBackground(Color.black);
		g2d.fillPolygon(yLineXArray, yLineYArray, 3);
		
		for(int y = yMin; y <= yMax; y += yDistance) {
			g2d.drawString(String.valueOf(y), 80, containerDimension.height - 70 - y * (yHeight));
			if(y != yMin)
				g2d.drawLine(95, containerDimension.height - 75 - y * (yHeight), 105, containerDimension.height - 75 - y * (yHeight));
		}

		return this;
	}

}
