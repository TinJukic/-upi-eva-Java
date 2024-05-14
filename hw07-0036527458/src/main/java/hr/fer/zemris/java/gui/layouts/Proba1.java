package hr.fer.zemris.java.gui.layouts;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Test class.
 * @author Tin Jukić
 *
 */
public class Proba1 extends JFrame {
	private static final long serialVersionUID = 1L;
	static JFrame prozor;
	static JPanel p;
	
	/**
	 * Constructor.
	 */
	public Proba1() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Proba pravljenja likova za prvi zadatak");
		setLocation(20, 20);
		setSize(500, 400);
		initGUI();
	}
	
	/**
	 * Initializing GUI.
	 */
	private void initGUI() {
		p = new JPanel(new CalcLayout(3));
		p.setSize(500, 400);
		p.add(new JLabel("x"), new RCPosition(1,1));
		p.add(new JLabel("y"), new RCPosition(2,3));
		p.add(new JLabel("z"), new RCPosition(2,7));
		p.add(new JLabel("w"), new RCPosition(4,2));
		p.add(new JLabel("a"), new RCPosition(4,5));
		p.add(new JLabel("b"), new RCPosition(4,7));
		JLabel nova = new JLabel("nova");
		p.add(nova, RCPosition.parse("5, 6"));
		p.remove(nova);
		
		getContentPane().add(p);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				prozor = new Proba1();
				prozor.pack();
				prozor.setVisible(true);
			}
		});
	}
}
