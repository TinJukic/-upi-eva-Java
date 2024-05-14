package hr.fer.zemris.math;

/**
 * Creates a polynomial using given parameters.
 * @author Tin Jukić
 *
 */
public class ComplexRootedPolynomial {

	private Complex constant;
	private Complex[] roots;

	 /**
	  * Public constructor.
	  * @param constant z0
	  * @param roots z1,...zn
	  */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Computes polynomial value at given point z.
	 * @param z some concrete z
	 * @return calculated value
	 */
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex result = this.constant;
		
		for(int i = 0; i < this.roots.length; i++)
			result = result.multiply(z.sub(roots[i]));
		
		return result;
	}
	
	/**
	 * Converts representation of the current ComplexRootedPolynomial type to ComplexPolynomial type
	 * @return ComplexPolynomial
	 */
	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[this.roots.length + 1];
		Complex[] tmp = new Complex[roots.length + 1];
		
		for(int i = 0; i < this.roots.length + 1; i++)
			factors[i] = new Complex(0, 0);
		
		// last element will always be equal to constant
		factors[factors.length - 1] = constant;
		
		for(int i = 0; i < roots.length; i++) {
			Complex root = roots[i];
			
			for(int j = 0; j < tmp.length; j++)
				tmp[j] = new Complex(0, 0);
			
			for(int j = tmp.length - 1; j >= 0; j--)
				if(j - 1 >= 0)
					tmp[j - 1] = factors[j].multiply(root.negate());
			
			for(int j = 0; j < factors.length; j++)
				factors[j] = factors[j].add(tmp[j]);
		}
		
		return new ComplexPolynomial(factors);
	}
	
	/**
	 * @return String representation of the ComplexRootedPolynomial type
	 */
	@Override
	public String toString() {
		String s = constant.toString();
		
		for(Complex c : roots)
			s += "*(z-" + c.toString() +")";
		
		return s;
	}
	
	/**
	 * Finds index of closest root for given complex number z that is within treshold.
	 * @param z passed complex number
	 * @param treshold max distance between two complex numbers
	 * @return index of the closest root, -1 otherwise
	 */
	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int i = 0;
		int bestIndex = -1;
//		double difference = z.module() - this.roots[i].module();
		double difference = this.roots[i].sub(z).module();
		
		if(difference <= treshold)
			bestIndex = i;
		
		for(i = 1; i < this.roots.length; i++) {
//			double newDifference = z.module() - this.roots[i].module();
			double newDifference = this.roots[i].sub(z).module();
			if(newDifference < difference && newDifference <= treshold) {
				difference = newDifference;
				bestIndex = i;
			}
		}
		
		return bestIndex;
	}
}
