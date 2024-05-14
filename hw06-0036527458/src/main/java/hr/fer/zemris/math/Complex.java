package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;

/**
 * Class which is used for the representation of the complex number.
 * @author Tin Jukić
 *
 */
public class Complex {
	private double re;
	private double im;
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Default constructor.
	 */
	public Complex() {
		
	}
	
	/**
	 * Makes a new complex number object using given attributes.
	 * @param re real part of the number
	 * @param im imaginary part of the number
	 */
	public Complex(double re, double im) {
		if(re == -0.0)
			re = 0.0;
		
		if(im == -0.0)
			im = 0.0;
		
		this.re = re;
		this.im = im;
	}
	
	/**
	 * 
	 * @return module of complex number
	 */
	// returns module of complex number
	public double module() {
		return Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
	}
	
	/**
	 * 
	 * @param c passed complex number
	 * @return current complex number * c
	 */
	// returns this*c
	public Complex multiply(Complex c) {
		double re = this.re*c.re - this.im*c.im;
		double im = this.re*c.im + this.im*c.re;
		
		return new Complex(re, im);
	}
	
	/**
	 * 
	 * @param c passed complex number
	 * @return current complex number / c
	 */
	// returns this/c
	public Complex divide(Complex c) {
		double divider = Math.pow(c.re, 2) + Math.pow(c.im, 2);
		Complex multiplied = this.multiply(new Complex(c.re, -c.im));
		
		double re = (double) multiplied.re/divider;
		double im = (double) multiplied.im/divider;
		
		return new Complex(re, im);
	}
	
	/**
	 * 
	 * @param c passed complex number
	 * @return current complex number + c
	 */
	// returns this+c
	public Complex add(Complex c) {
		double re = this.re + c.re;
		double im = this.im + c.im;
		
		return new Complex(re, im);
	}
	
	/**
	 * 
	 * @param c passed complex number
	 * @return current complex number - c
	 */
	// returns this-c
	public Complex sub(Complex c) {
		double re = this.re - c.re;
		double im = this.im - c.im;
		
		return new Complex(re, im);
	}
	
	/**
	 * 
	 * @return negative complex number
	 */
	// returns -this
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/**
	 * 
	 * @param n power, non-negative integer
	 * @return current complex number to the power of n
	 */
	// returns this^n, n is non-negative integer
	public Complex power(int n) {
		Complex tmp = this;
		
		if(n == 0)
			tmp = new Complex(1, 0);
		else
			for(int i = 0; i < n - 1; i++) {
				tmp = multiply(tmp);
			}
		
		return tmp;
	}
	
	/**
	 * 
	 * @param n root, positive integer
	 * @return n-th root of the current complex number
	 */
	// returns n-th root of this, n is positive integer
	public List<Complex> root(int n) {
		List<Complex> nThRootList = new LinkedList<>();
		
		double r = this.module();
		double fi = Math.atan(this.im/this.re);
		
		for(int i = 0; i < n; i++) {
			double re = Math.pow(r, 1.0/n) * Math.cos((fi + 2*i*Math.PI)/n);
			double im = Math.pow(r, 1.0/n) * Math.sin((fi + 2*i*Math.PI)/n);
			
			nThRootList.add(new Complex(re, im));
		}
		
		return nThRootList;
	}
	
	/**
	 * @return String representation of the current complex number
	 */
	@Override
	public String toString() {
		if(this.im >= 0)
			return "(" + this.re + "+i" + this.im + ")";
		else
			return "(" + this.re + "-i" + (-this.im) + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!obj.getClass().equals(Complex.class))
			return false;
		
		Complex other = (Complex) obj;
		
		// because of the double precision
		return (Math.abs(this.re - other.re) <= 0.00000001) && (Math.abs(this.im - other.im) <= 0.00000001);
	}
}
