package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void correctByteArrayTest() {
		try {
			byte[] b = Util.hextobyte("01aE22");
			assertEquals(3, b.length);
			assertEquals(Integer.valueOf(1).byteValue(), b[0]);
			assertEquals(Integer.valueOf(-82).byteValue(),b[1]);
			assertEquals(Integer.valueOf(34).byteValue(),b[2]);
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	void zeroLengthStringTest() {
		try {
			byte[] b = Util.hextobyte("");
			assertEquals(0, b.length);
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	void wrongCharTest() {
		try {
			Util.hextobyte("Z");
			fail();
		} catch(IllegalArgumentException e) {
			// exception is expected
		} catch(Exception e) {
			// niti jedna druga iznika se ne smije dogoditi
			fail();
		}
	}
	
	@Test
	void oddSizeTest() {
		try {
			Util.hextobyte("01a22");
			fail();
		} catch(IllegalArgumentException e) {
			// exception is expected
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	void byteArrayToStringTest() {
		byte[] b = { 1, -82, 34 };
		String s = Util.bytetohex(b);
		assertEquals(6, s.length());
		assertEquals("01ae22", s);
	}
	
	@Test
	void byteArrayLengthZeroTest() {
		byte[] b = {  };
		String s = Util.bytetohex(b);
		assertEquals(0, s.length());
	}
	
	@Test
	void hwExampleToByteArrayTest() {
		String hex = "2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598";
		String newHex;
		try {
			byte[] b = Util.hextobyte(hex);
			newHex = Util.bytetohex(b);
			assertEquals(hex, newHex);
		} catch(IllegalArgumentException e) {
			fail();
		}
	}

}
