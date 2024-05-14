package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * 
 * @author Tin Jukić
 *
 */
public class CalcLayout implements LayoutManager2 {
	private int spaces;
	private int rowCount = 5;
	private int columnCount = 7;
	private RCPosition[][] elements = new RCPosition[rowCount][columnCount]; // cannot contain duplicate elements
	private Component[][] elementsJLabel = new Component[rowCount][columnCount]; // for calculation of minimum, maximum, preferred
	Component c;

	/**
	 * Public constructor which sets the spaces between elements to 0 px.
	 */
	public CalcLayout() {
		this.spaces = 0;
	}

	/**
	 * Public constructor which sets spaces between elements to desired value.
	 * 
	 * @param spaces desired value of spaces between elements
	 */
	public CalcLayout(int spaces) {
		this.spaces = spaces;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		boolean componentFound = false;
		
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < columnCount; j++)
				if(elementsJLabel[i][j] != null && elementsJLabel[i][j].equals(comp)) {
					componentFound = true;
					elements[i][j] = null;
					elementsJLabel[i][j] = null;
					break;
				}
			
			if(componentFound)
				break;
		}
		
		if(!componentFound)
			throw new CalcLayoutException();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension[] preferredSizes = new Dimension[rowCount * columnCount - 5];
		int index = 0;
		
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < columnCount; j++) {
				if(i == 0 && j == 0)
					j = 5;
				if(elementsJLabel[i][j] != null)
					preferredSizes[index++] = elementsJLabel[i][j].getPreferredSize();
			}
		}
		
		int width = 0;
		int height = 0;
		int size = preferredSizes.length;
		
		for(index = 0; index < size; index++)
			if(preferredSizes[index] != null) {
				Dimension preferredSize = preferredSizes[index];
				
				if(preferredSize.width > width)
					width = preferredSize.width;
				
				if(preferredSize.height > height)
					height = preferredSize.height;
			}
		
		Insets parentDimension = parent.getInsets();
		
		int dimensionWidth = parentDimension.left + parentDimension.right + columnCount * width + (columnCount - 1) * spaces;
		int dimensionHeight = parentDimension.top + parentDimension.bottom + rowCount * height + (rowCount - 1) * spaces;
		
		return new Dimension(dimensionWidth, dimensionHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension[] preferredSizes = new Dimension[rowCount * columnCount - 5];
		int index = 0;
		
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < columnCount; j++) {
				if(i == 0 && j == 0)
					j = 5;
				if(elementsJLabel[i][j] != null)
					preferredSizes[index++] = elementsJLabel[i][j].getMinimumSize();
			}
		}
		
		int width = 0;
		int height = 0;
		int size = preferredSizes.length;
		
		for(index = 0; index < size; index++)
			if(preferredSizes[index] != null) {
				Dimension preferredSize = preferredSizes[index];
				
				if(preferredSize.width > width)
					width = preferredSize.width;
				
				if(preferredSize.height > height)
					height = preferredSize.height;
			}
		
		Insets parentDimension = parent.getInsets();
		
		int dimensionWidth = parentDimension.left + parentDimension.right + columnCount * width + (columnCount - 1) * spaces;
		int dimensionHeight = parentDimension.top + parentDimension.bottom + rowCount * height + (rowCount - 1) * spaces;
		
		return new Dimension(dimensionWidth, dimensionHeight);
	}

	@Override
	public void layoutContainer(Container parent) {
		// calculates the positions of the elements
		
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < columnCount; j++) {
				if(elementsJLabel[i][j] != null) {
					if(i == 0 && j == 1)
						j = 5;
					
					Component label = elementsJLabel[i][j];
					
					int compWidth = parent.getSize().width;
					int compHeight = parent.getSize().height;
					
					int width = 0;
					int height = 0;
					
					if(i == 0 && j == 0) {
						width = (int) ((compWidth / columnCount) * 5 + 0.5);
						height = (int) (compHeight / rowCount + 0.5);
					} else {
						// even distribution of sizes
						if(i % 2 == 0)
							width = (int) (compWidth / columnCount + 0.5);
						else
							width = (int) (compWidth / columnCount);
						if(j % 2 == 0)
							height = (int) (compHeight / rowCount + 0.5);
						else
							height = (int) (compHeight / rowCount);
					}
					
					int xLabel = (int) (j * width + spaces);
					int yLabel = (int) (i * height + spaces);
					
					label.setBounds(xLabel, yLabel, (int) (width - 2 * spaces), (int) (height - 2 * spaces));
					
					elementsJLabel[i][j] = label;
				}
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints == null || comp == null)
			throw new NullPointerException();
		
		if(!(constraints instanceof RCPosition) && !(constraints instanceof String))
			throw new IllegalArgumentException();

		RCPosition rcp;
		if (constraints instanceof String)
			rcp = RCPosition.parse((String) constraints);
		else
			rcp = (RCPosition) constraints;
		
		int x = rcp.getRow();
		int y = rcp.getColumn();
		
		if(x < 1 || x > 5 || y < 1 || y > 7 || (x == 1 && (y > 1 && y < 6)))
			throw new CalcLayoutException();
		
		if(elements[x - 1][y - 1] == null)
			elements[x - 1][y - 1] = rcp;
		else
			throw new CalcLayoutException();
		
		if(elementsJLabel[x - 1][y - 1] == null)
			elementsJLabel[x - 1][y - 1] = comp;
		else
			throw new CalcLayoutException();
		
		c = comp;
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension[] preferredSizes = new Dimension[rowCount * columnCount - 5];
		int index = 0;
		
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < columnCount; j++) {
				if(i == 0 && j == 0)
					j = 5;
				if(elementsJLabel[i][j] != null)
					preferredSizes[index++] = elementsJLabel[i][j].getMaximumSize();
			}
		}
		
		int width = 0;
		int height = 0;
		int size = preferredSizes.length;
		
		for(index = 0; index < size; index++)
			if(preferredSizes[index] != null) {
				Dimension preferredSize = preferredSizes[index];
				
				if(preferredSize.width > width)
					width = preferredSize.width;
				
				if(preferredSize.height > height)
					height = preferredSize.height;
			}
		
		Insets parentDimension = target.getInsets();
		
		int dimensionWidth = parentDimension.left + parentDimension.right + columnCount * width + (columnCount - 1) * spaces;
		int dimensionHeight = parentDimension.top + parentDimension.bottom + rowCount * height + (rowCount - 1) * spaces;
		
		return new Dimension(dimensionWidth, dimensionHeight);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		double allignment = 0.0;
		
		return (float) allignment;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		double allignment = 0.0;
		
		return (float) allignment;
	}

	@Override
	public void invalidateLayout(Container target) {
		// no implementation
	}
}
