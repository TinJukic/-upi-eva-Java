package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutExceptionTest {

	@Test
	void exceptionTest1() {
		try {
			RCPosition.parse("");
			fail("Exception was expected!");
		} catch(CalcLayoutException e) { }
	}
	
	@Test
	void exceptionTest2() {
		try {
			RCPosition.parse("-2,0");
			fail("Exception was expected!");
		} catch(CalcLayoutException e) { }
	}
	
	@Test
	void exceptionTest3() {
		try {
			RCPosition.parse("1, 8");
			fail("Exception was expected!");
		} catch(CalcLayoutException e) { }
	}
	
	@Test
	void exceptionTest4() {
		try {
			RCPosition.parse("-1,1");
			fail("Exception was expected!");
		} catch(CalcLayoutException e) { }
	}
	
	@Test
	void exceptionTest5() {
		try {
			RCPosition.parse("1,-1");
			fail("Exception was expected!");
		} catch(CalcLayoutException e) { }
	}
	
	@Test
	void exceptionTest6() {
		try {
			RCPosition.parse("0,0");
			fail("Exception was expected!");
		} catch(CalcLayoutException e) { }
	}
	
	@Test
	void exceptionTest7() {
		try {
			RCPosition.parse("1, 0");
			fail("Exception was expected!");
		} catch(CalcLayoutException e) { }
	}
	
	// testiraj još za dodavanje više komponenti uz isto ograničenje
	@Test
	void exceptionTest8() {
		try {
			JPanel p = new JPanel(new CalcLayout(2));
			JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
			JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
			p.add(l1, new RCPosition(3,3));
			p.add(l2, new RCPosition(3,3));
			fail();
			System.out.println("Exception did not occur!");
		} catch(CalcLayoutException e) { }
	}

}
