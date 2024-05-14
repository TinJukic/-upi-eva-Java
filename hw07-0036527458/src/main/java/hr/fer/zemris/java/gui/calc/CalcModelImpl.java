package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * 
 * @author Tin Jukić
 *
 */
public class CalcModelImpl implements CalcModel {
	private boolean editable;
	private boolean negative;
	private String input; // "" initially (numbers)
	private double numericalValue; // 0.0 initially
	private String displayed; // null initially
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private CalcValueListener calcValueListener;

	/**
	 * Constructor
	 */
	public CalcModelImpl() {
		unfreezeValue();
		negative = false;
		input = "";
		numericalValue = 0.0;
		activeOperand = null;
		pendingOperation = null;
		calcValueListener = null;
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		calcValueListener = l;
//		l.valueChanged(this);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(calcValueListener.equals(l))
			calcValueListener = null;
//		l.valueChanged(this);
	}

	@Override
	public double getValue() {
		return numericalValue;
	}

	@Override
	public void setValue(double value) {
		freezeValue();
		
		activeOperand = value;
		numericalValue = activeOperand;
		
		if(value == Double.NaN)
			displayed = "NaN";
		else if(value == Double.POSITIVE_INFINITY)
			displayed = "Infinity";
		else if(value == Double.NEGATIVE_INFINITY)
			displayed = "-Infinity";
		else
			displayed = Double.toString(value);
		
		if(displayed.contains("-"))
			negative = true;
		else
			negative = false;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		input = "";
		numericalValue = 0.0;
		unfreezeValue();
	}

	@Override
	public void clearAll() {
		clear();
		activeOperand = null;
		pendingOperation = null;
		unfreezeValue();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable())
			throw new CalculatorInputException();
		
		if(isActiveOperandSet())
			clearActiveOperand();
		
		if(negative == true)
			negative = false;
		else
			negative = true;
		
		if(input.equals(""))
			return;
		
		numericalValue = -numericalValue;
		
		if(negative)
			input = "-" + input;
		else {
			String tmp = "";
			for(int i = 1; i < input.length(); i++)
				tmp += input.charAt(i);
			input = tmp;
		}
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(input.contains(".") || !isEditable() || input.equals(""))
			throw new CalculatorInputException();
		
		if(isActiveOperandSet())
			clearActiveOperand();
		
		input += ".";
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable())
			throw new CalculatorInputException();
		
		if(input.equals("0") && digit == 0)
			return;
		
		if(input.equals("0") && digit != 0)
			input = "";
		
		String tmp = input + Integer.toString(digit);
		
		try {
			// check if the string can be formatted
			numericalValue = Double.parseDouble(tmp);
			input = tmp;
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		
		if(numericalValue > Double.MAX_VALUE)
			throw new CalculatorInputException();
	}

	@Override
	public boolean isActiveOperandSet() {
		if(activeOperand != null)
			return true;
		
		return false;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet())
			throw new IllegalStateException();
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	/**
	 * Freezes the editable value.
	 */
	private void freezeValue() {
		editable = false;
		displayed = input;
	}
	
	/**
	 * Unfreezes the editable value.
	 */
	private void unfreezeValue() {
		editable = true;
		displayed = null;
	}
	
	@Override
	public String toString() {
		if(isEditable() && displayed == null && input != "") {
			if(input.equals("0"))
				return "0";
			else if(input.equals("-0.0"))
				return "-0";
			return input;
		} else if(!isEditable() && displayed != null) {
			if(displayed.equals("0.0"))
				return "0";
			else if(displayed.equals("-0.0"))
				return "-0";
			return displayed;
		}
		
		if(negative)
			return "-0";
		else
			return "0";
	}

}
