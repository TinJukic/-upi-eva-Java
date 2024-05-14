package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class ComplexTest {
	
	Complex z1 = new Complex(1, 2);
	Complex z2 = new Complex(3, -4);

	@Test
	void moduleZ1Test() {
		assertEquals(Math.sqrt(5), z1.module());
	}
	
	@Test
	void moduleZ2Test() {
		assertEquals(Math.sqrt(25), z2.module());
	}
	
	@Test
	void multipliTest() {
		Complex result = new Complex(11, 2);
		assertEquals(result, z1.multiply(z2));
	}
	
	@Test
	void divideTest() {
		Complex result = new Complex((double) -1/5, (double) 2/5);
		assertEquals(result, z1.divide(z2));
	}
	
	@Test
	void addTest() {
		Complex result = new Complex(4, -2);
		assertEquals(result, z1.add(z2));
	}
	
	@Test
	void subTest() {
		Complex result = new Complex(-2, 6);
		assertEquals(result, z1.sub(z2));
	}
	
	@Test
	void negateZ1Test() {
		Complex result = new Complex(-1, -2);
		assertEquals(result, z1.negate());
	}
	
	@Test
	void negateZ2Test() {
		Complex result = new Complex(-3, 4);
		assertEquals(result, z2.negate());
	}
	
	@Test
	void power5Z1Test() {
		Complex result = new Complex(41, -38);
		assertEquals(result, z1.power(5));
	}
	
	@Test
	void power8Z2Test() {
		Complex result = new Complex(164833, -354144);
		assertEquals(result, z2.power(8));
	}
	
	@Test
	void root3Z1Test() {
		List<Complex> list = z1.root(3);
		
		double fi = Math.atan(2.0);
		double r = Math.pow(5.0, 1.0/6.0);
		
		assertEquals(3, list.size());
		assertEquals(list.get(0), new Complex(r * Math.cos((fi)/3.0), r * Math.sin((fi)/3.0)));
		assertEquals(list.get(1), new Complex(r * Math.cos((fi+2*Math.PI)/3.0), r * Math.sin((fi+2*Math.PI)/3.0)));
		assertEquals(list.get(2), new Complex(r * Math.cos((fi+4*Math.PI)/3.0), r * Math.sin((fi+4*Math.PI)/3.0)));
	}
	
	@Test
	void root4Z2Test() {
		List<Complex> list = z2.root(4);
		
		double fi = Math.atan(-4.0/3.0);
		double r = Math.pow(5.0, 1.0/4.0);
		
		assertEquals(4, list.size());
		assertEquals(list.get(0), new Complex(r * Math.cos((fi)/4.0), r * Math.sin((fi)/4.0)));
		assertEquals(list.get(1), new Complex(r * Math.cos((fi+2*Math.PI)/4), r * Math.sin((fi+2*Math.PI)/4)));
		assertEquals(list.get(2), new Complex(r * Math.cos((fi+4*Math.PI)/4), r * Math.sin((fi+4*Math.PI)/4)));
		assertEquals(list.get(3), new Complex(r * Math.cos((fi+6*Math.PI)/4), r * Math.sin((fi+6*Math.PI)/4)));
	}
	
	@Test
	void toStringZ1Test() {
		assertEquals("(1.0+i2.0)", z1.toString());
	}
	
	@Test
	void toStringZ2Test() {
		assertEquals("(3.0-i4.0)", z2.toString());
	}

}
