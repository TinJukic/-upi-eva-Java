package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexPolynomialTest {

	ComplexPolynomial cp = new ComplexPolynomial(new Complex(2, 0), new Complex(0, 0), new Complex(0, 0), new Complex(0, 0), new Complex(-2, 0));
	
	@Test
	void cpTest() {
		assertEquals(ComplexPolynomial.class, cp.getClass());
	}
	
	@Test
	void order1Test() {
		assertEquals((short) 4, cp.order());
	}
	
	@Test
	void order2Test() {
		assertEquals(3, new ComplexPolynomial(new Complex(7, 2), new Complex(2, 0), new Complex(5, 0), new Complex(1, 0)).order());
	}
	
	@Test
	void multiplyTest() {
		assertEquals(new ComplexPolynomial(
				new Complex(16, -6), new Complex(2, 2), new Complex(6, 8),
				new Complex(0, 0), new Complex(-16, 6), new Complex(-2, -2),
				new Complex(-6, -8)).toString(), cp.multiply(new ComplexPolynomial(
						new Complex(8, -3), new Complex(1, 1), new Complex(3, 4)
				)).toString());
	}
	
	@Test
	void deriveTest() {
		assertEquals(new ComplexPolynomial(new Complex(5, 0), new Complex(4, 0), new Complex(21, 6)).toString(), 
				new ComplexPolynomial(new Complex(1, 0), new Complex(5, 0), new Complex(2, 0), new Complex(7, 2)).derive().toString());
	}
	
	@Test
	void applyTest() {
		assertEquals(new Complex(-17, 9), new ComplexPolynomial(new Complex(-10, 0), new Complex(-1, 0), new Complex(2, 0), new Complex(3, 0))
				.apply(new Complex(1, 1)));
	}

}
