package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Testing the stack.
 * @author Tin Jukić
 *
 */
public class StackDemo {
	public static void main(String[] args) {
		ObjectStack os = new ObjectStack();
		
		// removing blanks
		String split[] = args[0].split(" ");
		
		for(String element : split) {
			boolean isNumber = false;
			
			try {
				int number = Integer.parseInt(element);
				isNumber = true;
				os.push(number);
			} catch(NumberFormatException e) {}
			
			if(!isNumber) {
				int secondNumber = (int)os.pop();
				int firstNumber = (int)os.pop();
				String operation = element;
				
				if(operation.equals(String.valueOf('+')))
					firstNumber = firstNumber + secondNumber;
				else if(operation.equals(String.valueOf('-')))
					firstNumber = firstNumber - secondNumber;
				else if(operation.equals(String.valueOf('*')))
					firstNumber = firstNumber * secondNumber;
				else if(operation.equals(String.valueOf('/')))
					try {
						firstNumber = firstNumber / secondNumber;
					} catch(Exception e) {
						System.out.println("Dividing by zero is not allowed!");
					}
				else if(operation.equals(String.valueOf('%')))
					try {
						firstNumber = firstNumber % secondNumber;
					} catch(Exception e) {
						System.out.println("Dividing by zero is not allowed!");
					}
				else {
					System.out.println("The expression" + operation + " is invalid!");
					break; // warn the user and break
				}
				
				os.push(firstNumber);
			}
		}
		if(os.size() != 1) System.out.println("An error occured!");
		else System.out.println("Expression evaluates to " + os.pop() + ".");
	}
}
