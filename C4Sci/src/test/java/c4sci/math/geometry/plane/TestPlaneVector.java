package c4sci.math.geometry.plane;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.math.algebra.Floatings;

public class TestPlaneVector {

	@Test
	public void test() {
		PlaneVector _v1 = new PlaneVector(1.0f, 0.0f);
		PlaneVector _v0 = new PlaneVector();
		PlaneVector _v2 = new PlaneVector(0.0f, 1.0f);
		
		assertTrue(_v0.isEqualOp(_v0));
		assertTrue(_v1.isEqualOp(_v1.plusOp(_v0)));
		assertTrue(_v0.isEqualOp(_v1.minusOp(_v1)));
		assertTrue(Floatings.isEqual(0.0f, _v1.dotOp(_v2)));
		assertTrue(Floatings.isEqual(1.0f, _v1.dotOp(_v1)));
		
		_v0.equalOp(_v1);
		assertTrue(_v0.isEqualOp(_v1));
		assertFalse(_v0.isEqualOp(_v2));
		
		String _v1_str = _v1.toString();
		assertTrue("1.0 0.0".compareTo(_v1_str) == 0);

		try{
			_v1.equalOp(PlaneVector.parseVector(_v2.toString()));
			assertTrue(_v1.isEqualOp(_v2));
		}
		catch(NumberFormatException _e){
			fail("should not fail here");
		}
		
		String[] _tab_good_exp ={"0.0 0.0", "1.0 2", "1 2", "1 2.00000"};
		for (String _tab_str : _tab_good_exp){
			try{
				_v1.equalOp(PlaneVector.parseVector(_tab_str));
			}
			catch(NumberFormatException _e){
				fail("should not fail here");
			}
		}
		
		String[] _tab_bad_exp = {"1.a 2", "0..0 5", "1", null, "1.0 ..2", "1 b"};
		for (String _tab_str : _tab_bad_exp){
			try{
				_v1.equalOp(PlaneVector.parseVector(_tab_str));
			}
			catch(NumberFormatException _e){
				assertTrue(true);
			}
		}
	}

}
