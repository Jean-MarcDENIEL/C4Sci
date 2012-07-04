package c4sci.math.geometry.space;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSpaceVector {

	@Test
	public void testGetSet() {
		for (int i=1; i<4; i++ ){
			SpaceVector _test_vec = new SpaceVector();
			_test_vec.setW((float)i);

			_test_vec.setX(1.0f);
			assertEquals(_test_vec.getX(), 1.0f, 0.01f);

			_test_vec.setY(1.0f);
			assertEquals(_test_vec.getY(), 1.0f, 0.01f);

			_test_vec.setZ(1.0f);
			assertEquals(_test_vec.getZ(), 1.0f, 0.01f);
		}
		
		SpaceVector _test_vec = new SpaceVector(1.0f, 1.0f, 1.0f);
		assertEquals(_test_vec.getNorm2(), 3.0f, 0.01f);
		assertEquals(_test_vec.getNorm(), Math.sqrt(3.0), 0.01f);

	}

}
