package hr.fer.zemris.math;

public class ComplexPolynomial {
	private Complex[] factors;
	
	/**
	 * Makes a new ComplexPolynomial Object.
	 * @param factors factors which the polynomial has
	 */
	// constructor
	public ComplexPolynomial(Complex ... factors) {
		this.factors = factors;
	}
	
	/**
	 * 
	 * @return order of the current polynom
	 */
	// returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * 
	 * @param index index of the wanted factor
	 * @return factor at the given index
	 */
	// helper method
	public Complex getFactor(int index) {
		return this.factors[index];
	}
	
	/**
	 * Computes new polynomial using current polynomial and passed polynomial
	 * @param p passed polynomial
	 * @return current polynomial * p
	 */
	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];
		
		for(int i = 0; i < newFactors.length; i++)
			newFactors[i] = new Complex(0, 0);
		
		for(int i = 0; i <= this.order(); i++) {
			for(int j = 0; j <= p.order(); j++) {
				newFactors[i + j] = newFactors[i + j].add(this.getFactor(i).multiply(p.getFactor(j)));
			}
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Computes the first derivative for the current polynomial.
	 * @return computed derivative
	 */
	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[this.order()];
		
		for(int i = this.order(); i >= 1; i--) {
			newFactors[i - 1] = this.getFactor(i).multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Computes polynomial value at the given point z.
	 * @param z passed Complex number
	 * @return computed polynomial
	 */
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex result = new Complex(0, 0);
		
		for(int i = this.order(); i >= 0; i--) {
			result = result.add(z.power(i).multiply(this.getFactor(i)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for(int i = this.order(); i >= 0; i--) {
			if(i >= 1)
				s += this.getFactor(i) + "*z^" + i + "+";
			else
				s += this.getFactor(i);
		}
		
		return s;
	}
}
