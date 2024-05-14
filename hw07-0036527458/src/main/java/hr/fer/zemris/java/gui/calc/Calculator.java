package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Calculator method.
 * @author Tin Jukić
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CalcModelImpl calc;
	private JButton[] changableButtons;
	private boolean inverse;
	private JPanel panel;
	private Double[] stack;
	
	/**
	 * Constructor
	 */
	public Calculator() {
		calc = new CalcModelImpl();
		changableButtons = new JButton[7];
		inverse = false;
		stack = new Double[20];
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		initGUI();
		pack();
	}
	
	/**
	 * Calculator needs to calculate current operation first.
	 */
	private void calculate() {
		double r = calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue());
		calc.setValue(r);
	}
	
	/**
	 * On number press listener.
	 * @param button number button
	 * @param result number which is pressed
	 * @return
	 */
	private ActionListener numberButtonActionListener(JButton button, JLabel result) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.insertDigit(Integer.parseInt(button.getText()));
				result.setText(calc.toString());
			}
		};
	}
	
	/**
	 * Setting button properties.
	 * @param button
	 */
	private void setButtons(JButton button) {
		button.setBackground(Color.lightGray);
		button.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setFont(button.getFont().deriveFont(15f));
		button.setOpaque(true);
	}
	
	/**
	 * Inverses the buttons.
	 * @param result
	 */
	private void inverseButtons(JLabel result) {
		if(!inverse) {
			panel.remove(changableButtons[0]);
			JButton log = new JButton("log");
			setButtons(log);
			log.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = Math.log10(calc.getValue());
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(log, "3,1");
			changableButtons[0] = log;
			
			panel.remove(changableButtons[1]);
			JButton ln = new JButton("ln");
			setButtons(ln);
			ln.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = Math.log(calc.getValue());
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(ln, "4,1");
			changableButtons[1] = ln;
			
			panel.remove(changableButtons[2]);
			JButton xOnN = new JButton("x^n");
			setButtons(xOnN);
			xOnN.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(calc.isActiveOperandSet())
						calculate();
					calc.setActiveOperand(calc.getValue());
					calc.setPendingBinaryOperation((x, n) -> {
						return Math.pow(x, n);
					});
					calc.clear();
				}
			});
			panel.add(xOnN, "5,1");
			changableButtons[2] = xOnN;
			
			panel.remove(changableButtons[3]);
			JButton sin = new JButton("sin");
			setButtons(sin);
			sin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = Math.sin(calc.getValue());
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(sin, "2,2");
			changableButtons[3] = sin;
			
			panel.remove(changableButtons[4]);
			JButton cos = new JButton("cos");
			setButtons(cos);
			cos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = Math.cos(calc.getValue());
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(cos, "3,2");
			changableButtons[4] = cos;
			
			panel.remove(changableButtons[5]);
			JButton tan = new JButton("tan");
			setButtons(tan);
			tan.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = Math.tan(calc.getValue());
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(tan, "4,2");
			changableButtons[5] = tan;
			
			panel.remove(changableButtons[6]);
			JButton ctg = new JButton("ctg");
			setButtons(ctg);
			ctg.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = (double) (1 / Math.tan(calc.getValue()));
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(ctg, "5,2");
			changableButtons[6] = ctg;
			
			setVisible(true);
		} else {
			panel.remove(changableButtons[0]);
			JButton tenOnX = new JButton("10^x");
			setButtons(tenOnX);
			tenOnX.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(calc.isActiveOperandSet())
						calculate();
					calc.setActiveOperand(10.0);
					calc.setPendingBinaryOperation((ten, x) -> Math.pow(ten, x));
				}
			});
			panel.add(tenOnX, "3,1");
			changableButtons[0] = tenOnX;
			
			panel.remove(changableButtons[1]);
			JButton eOnX = new JButton("e^x");
			setButtons(eOnX);
			eOnX.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(calc.isActiveOperandSet())
						calculate();
					calc.setActiveOperand(Math.E);
					calc.setPendingBinaryOperation((E, x) -> Math.pow(E, x));
				}
			});
			panel.add(eOnX, "4,1");
			changableButtons[1] = eOnX;
			
			panel.remove(changableButtons[2]);
			JButton xOnNMinusOne = new JButton("x^(1/n)");
			setButtons(xOnNMinusOne);
			xOnNMinusOne.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(calc.isActiveOperandSet())
						calculate();
					calc.setActiveOperand(calc.getValue());
					calc.setPendingBinaryOperation((x, n) -> Math.pow(x, 1/n));
					calc.clear();
				}
			});
			panel.add(xOnNMinusOne, "5,1");
			changableButtons[2] = xOnNMinusOne;
			
			panel.remove(changableButtons[3]);
			JButton arcsin = new JButton("arcsin");
			setButtons(arcsin);
			arcsin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = Math.asin(calc.getValue());
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(arcsin, "2,2");
			changableButtons[3] = arcsin;
			
			panel.remove(changableButtons[4]);
			JButton arccos = new JButton("arccos");
			setButtons(arccos);
			arccos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = Math.acos(calc.getValue());
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(arccos, "3,2");
			changableButtons[4] = arccos;
			
			panel.remove(changableButtons[5]);
			JButton arctan = new JButton("arctan");
			setButtons(arctan);
			arctan.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = Math.atan(calc.getValue());
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(arctan, "4,2");
			changableButtons[5] = arctan;
			
			panel.remove(changableButtons[6]);
			JButton arcctg = new JButton("arcctg");
			setButtons(arcctg);
			arcctg.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double r = (double) (1 / Math.atan(calc.getValue()));
					calc.setValue(r);
					result.setText(calc.toString());
				}
			});
			panel.add(arcctg, "5,2");
			changableButtons[6] = arcctg;
			
			setVisible(true);
		}
	}
	
	/**
	 * Result label formating.
	 * @param numberLabel
	 */
	private void setNumberLabels(JButton numberLabel) {
		numberLabel.setBackground(Color.lightGray);
		numberLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberLabel.setVerticalAlignment(SwingConstants.CENTER);
		numberLabel.setFont(numberLabel.getFont().deriveFont(30f));
		numberLabel.setOpaque(true);
	}
	
	/**
	 * Initializing GUI.
	 */
	private void initGUI() {
		// panel must implement CalcLayout in order to show the calculator correctly
		panel = new JPanel(new CalcLayout(3)); // panel == CalcValueListener
		
		JLabel result = new JLabel(calc.toString(), SwingConstants.RIGHT);
		result.setBackground(Color.yellow);
		result.setFont(result.getFont().deriveFont(30f));
		result.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		result.setText(calc.toString());
		result.setOpaque(true);
		panel.add(result, "1,1");
		
		JButton equals = new JButton("=");
		setButtons(equals);
		equals.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double r = calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue());
				calc.setValue(r);
				result.setText(calc.toString());
			}
		});
		panel.add(equals, "1,6");
		
		JButton clr = new JButton("clr");
		setButtons(clr);
		clr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.clear();
				result.setText(calc.toString());
			}
		});
		panel.add(clr, "1,7");
		
		JButton oneDividedX = new JButton("1/x");
		setButtons(oneDividedX);
		oneDividedX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double r = (double) (1 / calc.getValue());
				calc.setValue(r);
				result.setText(calc.toString());
			}
		});
		panel.add(oneDividedX, "2,1");
		
		JButton log = new JButton("log");
		setButtons(log);
		log.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double r = Math.log10(calc.getValue());
				calc.setValue(r);
				result.setText(calc.toString());
			}
		});
		panel.add(log, "3,1");
		changableButtons[0] = log;
		
		JButton ln = new JButton("ln");
		setButtons(ln);
		ln.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double r = Math.log(calc.getValue());
				calc.setValue(r);
				result.setText(calc.toString());
			}
		});
		panel.add(ln, "4,1");
		changableButtons[1] = ln;
		
		JButton xOnN = new JButton("x^n");
		setButtons(xOnN);
		xOnN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calc.isActiveOperandSet())
					calculate();
				calc.setActiveOperand(calc.getValue());
				calc.setPendingBinaryOperation((x, n) -> {
					return Math.pow(x, n);
				});
				calc.clear();
			}
		});
		panel.add(xOnN, "5,1");
		changableButtons[2] = xOnN;
		
		JButton sin = new JButton("sin");
		setButtons(sin);
		sin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double r = Math.sin(calc.getValue());
				calc.setValue(r);
				result.setText(calc.toString());
			}
		});
		panel.add(sin, "2,2");
		changableButtons[3] = sin;
		
		JButton cos = new JButton("cos");
		setButtons(cos);
		cos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double r = Math.cos(calc.getValue());
				calc.setValue(r);
				result.setText(calc.toString());
			}
		});
		panel.add(cos, "3,2");
		changableButtons[4] = cos;
		
		JButton tan = new JButton("tan");
		setButtons(tan);
		tan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double r = Math.tan(calc.getValue());
				calc.setValue(r);
				result.setText(calc.toString());
			}
		});
		panel.add(tan, "4,2");
		changableButtons[5] = tan;
		
		JButton ctg = new JButton("ctg");
		setButtons(ctg);
		ctg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double r = (double) (1 / Math.tan(calc.getValue()));
				calc.setValue(r);
				result.setText(calc.toString());
			}
		});
		panel.add(ctg, "5,2");
		changableButtons[6] = ctg;
		
		JButton plusMinus = new JButton("+/-");
		setButtons(plusMinus);
		plusMinus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.swapSign();
				result.setText(calc.toString());
			}
		});
		panel.add(plusMinus, "5,4");
		
		JButton dot = new JButton(".");
		setButtons(dot);
		dot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.insertDecimalPoint();
				result.setText(calc.toString());
			}
		});
		panel.add(dot, "5,5");
		
		JButton divide = new JButton("/");
		setButtons(divide);
		divide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calc.isActiveOperandSet())
					calculate();
				calc.setActiveOperand(calc.getValue());
				calc.setPendingBinaryOperation((left, right) -> left / right);
				calc.clear();
			}
		});
		panel.add(divide, "2,6");
		
		JButton multiply = new JButton("*");
		setButtons(multiply);
		multiply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calc.isActiveOperandSet())
					calculate();
				calc.setActiveOperand(calc.getValue());
				calc.setPendingBinaryOperation((left, right) -> left * right);
				calc.clear();
			}
		});
		panel.add(multiply, "3,6");
		
		JButton minus = new JButton("-");
		setButtons(minus);
		minus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calc.isActiveOperandSet())
					calculate();
				calc.setActiveOperand(calc.getValue());
				calc.setPendingBinaryOperation((left, right) -> left - right);
				calc.clear();
			}
		});
		panel.add(minus, "4,6");
		
		JButton plus = new JButton("+");
		setButtons(plus);
		plus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calc.isActiveOperandSet())
					calculate();
				calc.setActiveOperand(calc.getValue());
				calc.setPendingBinaryOperation(Double::sum);
				calc.clear();
			}
		});
		panel.add(plus, "5,6");
		
		JButton reset = new JButton("reset");
		setButtons(reset);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.clearAll();
				for(int i = 0; i < 20; i++)
					stack[i] = null;
				result.setText(calc.toString());
			}
		});
		panel.add(reset, "2,7");
		
		JButton push = new JButton("push");
		setButtons(push);
		push.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < 20; i++)
					if(stack[i] == null)
						stack[i] = calc.getValue();
				
				calc.clear();
				result.setText(calc.toString());
			}
		});
		panel.add(push, "3,7");
		
		JButton pop = new JButton("pop");
		setButtons(pop);
		pop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 20 - 1; i >= 0; i--)
					if(stack[i] != null) {
						calc.setValue(stack[i]);
						stack[i] = null;
					}
				
				result.setText(calc.toString());
			}
		});
		panel.add(pop, "4,7");
		
		JButton zero = new JButton("0");
		setNumberLabels(zero);
		zero.addActionListener(numberButtonActionListener(zero, result));
		panel.add(zero, "5,3");
		
		JButton one = new JButton("1");
		setNumberLabels(one);
		one.addActionListener(numberButtonActionListener(one, result));
		panel.add(one, "4,3");
		
		JButton two = new JButton("2");
		setNumberLabels(two);
		two.addActionListener(numberButtonActionListener(two, result));
		panel.add(two, "4,4");
		
		JButton three = new JButton("3");
		setNumberLabels(three);
		three.addActionListener(numberButtonActionListener(three, result));
		panel.add(three, "4,5");
		
		JButton four = new JButton("4");
		setNumberLabels(four);
		four.addActionListener(numberButtonActionListener(four, result));
		panel.add(four, "3,3");
		
		JButton five = new JButton("5");
		setNumberLabels(five);
		five.addActionListener(numberButtonActionListener(five, result));
		panel.add(five, "3,4");
		
		JButton six = new JButton("6");
		setNumberLabels(six);
		six.addActionListener(numberButtonActionListener(six, result));
		panel.add(six, "3,5");
		
		JButton seven = new JButton("7");
		setNumberLabels(seven);
		seven.addActionListener(numberButtonActionListener(seven, result));
		panel.add(seven, "2,3");
		
		JButton eight = new JButton("8");
		setNumberLabels(eight);
		eight.addActionListener(numberButtonActionListener(eight, result));
		panel.add(eight, "2,4");
		
		JButton nine = new JButton("9");
		setNumberLabels(nine);
		nine.addActionListener(numberButtonActionListener(nine, result));
		panel.add(nine, "2,5");
		
		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inverse)
					inverse = true;
				else
					inverse = false;
				
				inverseButtons(result);
			}
		});
		panel.add(inv, "5,7");
		
		getContentPane().add(panel);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
