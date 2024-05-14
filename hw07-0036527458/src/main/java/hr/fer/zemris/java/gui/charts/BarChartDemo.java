package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Test class.
 * @author Tin Jukić
 *
 */
public class BarChartDemo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private static String xDescription;
	private static String yDescription;
	private static List<XYValue> listXYValue;
	private static int yMin;
	private static int yMax;
	private static int yDistance;
	private static BarChartComponent barChartComponent;
	
	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar Chart Demo");
		setSize(1000, 1000);
		initGUI();
	}
	
	private void initGUI() {
		panel = new JPanel(new BorderLayout());
		getContentPane().add(panel);
		panel.add(barChartComponent);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		barChartComponent.drawComponent(g);
	}
	
	public static void main(String[] args) {
		Path p = Paths.get(args[0]);
		try {
			List<String> f = Files.readAllLines(p);
			
			xDescription = f.get(0);
			System.out.println(xDescription);
			yDescription = f.get(1);
			System.out.println(yDescription);
			String elements = f.get(2);
			String[] elementsSplit = elements.split(" ");
			listXYValue = new LinkedList<>();
			for(String element : elementsSplit) {
				System.out.println(element);
				String components[] = element.split(",");
				int x = Integer.parseInt(components[0]);
				int y = 0;
				if(components.length == 2)
					y = Integer.parseInt(components[1]);
				listXYValue.add(new XYValue(x, y));
			}
			yMin = Integer.parseInt(f.get(3));
			System.out.println(yMin);
			yMax = Integer.parseInt(f.get(4));
			System.out.println(yMax);
			yDistance = Integer.parseInt(f.get(5));
			System.out.println(yDistance);
			barChartComponent = new BarChartComponent(new BarChart(listXYValue, xDescription, yDescription, yMin, yMax, yDistance));
			
			SwingUtilities.invokeLater(() -> {
				new BarChartDemo().setVisible(true);
			});
		} catch (IOException e) {
			System.err.println("Error occured while reading file.");
		}
		
	}
}
