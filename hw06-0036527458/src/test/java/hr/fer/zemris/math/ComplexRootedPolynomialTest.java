package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexRootedPolynomialTest {
	ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
	
	@Test
	void crpTest() {
		assertEquals(ComplexRootedPolynomial.class, crp.getClass());
	}
	
	@Test
	void toStringTest() {
		assertEquals("(2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))", crp.toString());
	}
	
	@Test
	void applyTest() {
		assertEquals(new Complex(-10.0, 0.0), crp.apply(new Complex(1.0, 1.0)));
	}
	
	@Test
	void treshold1Test() {
		assertEquals(-1, crp.indexOfClosestRootFor(new Complex(1, 1), 0));
	}
	
	@Test
	void treshold2Test() {
		assertEquals(-1, crp.indexOfClosestRootFor(new Complex(1, 1), Math.sqrt(2) - 1));
	}
	
	@Test
	void treshold3Test() {
		assertEquals(0, crp.indexOfClosestRootFor(new Complex(1, 1), 1));
	}
	
	@Test
	void toComplexPolynomial1Test() {
		assertEquals("(2.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-2.0+i0.0)", crp.toComplexPolynom().toString());
	}
	
	@Test
	void toComplexPolynomial2Test() {
		assertEquals("(1.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-1.0+i0.0)", new ComplexRootedPolynomial(new Complex(1, 0),
				new Complex(1, 0), new Complex(-1, 0), new Complex(0, 1), new Complex(0, -1)).toComplexPolynom().toString());
	}

}
