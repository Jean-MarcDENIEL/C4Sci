package c4sci.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFloatings {

	@Test
	public void testIsLess() {
		assertTrue(Floatings.isLess(0.01f,0.02f));
		assertFalse(Floatings.isLess(0.0f, 0.0f));
	}

	@Test
	public void testIsEqual() {
		assertTrue(Floatings.isEqual(0.0f, 0.0f));
		assertFalse(Floatings.isEqual(0.0f, 0.1f));
	}

	@Test
	public void testIsGreater() {
		assertTrue(Floatings.isGreater(1.0f, 0.999f));
		assertFalse(Floatings.isGreater(1.0f, 1.0f));
	}

	@Test
	public void testIsLessEqual() {
		assertTrue(Floatings.isLessEqual(0.01f, 0.02f));
		assertTrue(Floatings.isLessEqual(0.01f, 0.01f));
		assertFalse(Floatings.isLessEqual(0.01f, 0.0f));
	}

	@Test
	public void testIsMoreEqual() {
		assertTrue(Floatings.isGreaterEqual(0.0f, 0.0f));
		assertTrue(Floatings.isGreaterEqual(0.5f, 0.49f));
		assertFalse(Floatings.isGreaterEqual(0.0f, 0.1f));
	}

}
