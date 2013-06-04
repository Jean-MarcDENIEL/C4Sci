package c4sci.math.geometry.space;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSpaceVector {

	@Test
	public void testGetSetNorm() {
		for (int _i=1; _i<4; _i++ ){
			SpaceVector _test_vec = new SpaceVector();
			SpaceVector _test_vec_2 = new SpaceVector();
			
			_test_vec.setW((float)_i);

			_test_vec.setX(1.0f);
			assertEquals(_test_vec.getX(), 1.0f, 0.01f);

			_test_vec.setY(1.0f);
			assertEquals(_test_vec.getY(), 1.0f, 0.01f);

			_test_vec.setZ(1.0f);
			assertEquals(_test_vec.getZ(), 1.0f, 0.01f);
			
			for (int _j=0; _j<3; _j++){
				_test_vec_2.setCoor(_j, (float)(_j));
			}
			for (int _j=0; _j<3; _j++){
				assertEquals(_test_vec_2.getCoor(_j), (float)_j, 0.01);
			}
			
			float _norm = _test_vec_2.getNorm();
			_test_vec_2.normalizeVector();
			assertEquals(_test_vec_2.getNorm(), 1.0, 0.01);
			for (int _j=0; _j<3; _j++){
				assertEquals(_test_vec_2.getCoor(_j), (float)_j/_norm, 0.01);
			}
			
			SpaceVector _test_vec_3 = new SpaceVector(1.0f, 1.0f, 1.0f, 2.0f);
			_test_vec_3.normalizeW();
			assertEquals(_test_vec_3.getW(), 1.0, .01);
			for (Commons.CoorName _coor : Commons.CoorName.XYZ_TAB)
				assertEquals(_test_vec_3.getCoor(_coor), 2.0, .01);
		}
		
		SpaceVector _test_vec = new SpaceVector(1.0f, 1.0f, 1.0f);
		assertEquals(_test_vec.getNorm2(), 3.0f, 0.01f);
		assertEquals(_test_vec.getNorm(), Math.sqrt(3.0), 0.01f);
		
		_test_vec = new SpaceVector(1.0f, 1.0f, 1.0f, 2.0f);
		assertEquals(_test_vec.getX(), 2.0, 0.01);
		assertEquals(_test_vec.getNorm2(), 12.0, 0.01);
	}

	@Test
	public void testDotCross(){
		SpaceVector _vec_X = new SpaceVector(1,0,0);
		SpaceVector _vec_Y = new SpaceVector(0,1,0);
		SpaceVector _vec_Z = new SpaceVector(0,0,1);
		SpaceVector _tab_vec[] = {_vec_X, _vec_Y, _vec_Z};
		
		for (int _i = 0; _i<3; _i++){
			for (int _j = 0; _j<3; _j++){
				float _dot = _tab_vec[_i].dotProduct(_tab_vec[_j]);
				SpaceVector _cross = _tab_vec[_i].crossProduct(_tab_vec[_j]);
				if (_i == _j){
					assertEquals(_dot, 1.0, 0.01);
					assertEquals(_cross.getNorm(), 0.0, 0.01);
				}
				else{
					assertEquals(_dot, 0.0, 0.01);
					assertEquals(_cross.getNorm(), 1.0, 0.01);
				}
			}
		}
		assertEquals(_vec_X.crossProduct(_vec_Y).getZ(), 1.0, 0.01);
	}
	
	@Test
	public void testMinMax(){
		SpaceVector _vec_a = new SpaceVector(1.0f, 2.0f, 3.0f);
		SpaceVector _vec_b = new SpaceVector(0.0f, -2.0f, 5.0f);
		SpaceVector _vec_c = new SpaceVector(4.0f, 5.0f, 2.0f);
		
		SpaceVector _vec_inf = _vec_a.minVector(_vec_b);
		SpaceVector _vec_sup = _vec_b.maxVector(_vec_c);
		
		assertEquals(_vec_inf.getX(), 0.0, 0.01);
		assertEquals(_vec_inf.getY(), -2.0, 0.01);
		assertEquals(_vec_inf.getZ(), 3.0, 0.01);
		
		assertEquals(_vec_sup.getX(), 4.0, .01);
		assertEquals(_vec_sup.getY(), 5.0, .01);
		assertEquals(_vec_sup.getZ(), 5.0, .01);
	}
	
	@Test
	public void testComparisons(){
		SpaceVector _vec_a = new SpaceVector(1.0f, 2.0f, 3.0f);
		SpaceVector _vec_b = new SpaceVector(0.0f, -2.0f, 5.0f);
		SpaceVector _vec_c = new SpaceVector(4.0f, 5.0f, 6.0f);
		
		assertTrue(_vec_a.isLessThan(_vec_c));
		assertTrue(_vec_a.isLessOrEqualTo(_vec_a));
		
		assertFalse(_vec_b.isLessThan(_vec_a));
		
		assertTrue(_vec_c.isGreaterThan(_vec_b));
		assertTrue(_vec_c.isGreaterOrEqualTo(_vec_c));
		
		assertTrue(_vec_a.isEqualTo(_vec_a));
		assertFalse(_vec_a.isEqualTo(_vec_b));
	}
	
	@Test
	public void testAffOperators(){
		SpaceVector _vec_a = new SpaceVector(1.0f, 2.0f, 3.0f);
		SpaceVector _vec_b = new SpaceVector(0.0f, -2.0f, 5.0f);
		SpaceVector _vec_c = new SpaceVector(1.0f, 0.0f, 8.0f);
		SpaceVector _vec_d = new SpaceVector(1.0f, 4.0f, -2.0f);
		SpaceVector _vec_e = new SpaceVector(2.0f, 8.0f, -4.0f);
		
		
		SpaceVector _vec_res = new SpaceVector();
		_vec_res.opEquals(_vec_a);
		assertTrue(_vec_res.isEqualTo(_vec_a));
		
		_vec_res.opPlusEquals(_vec_b);
		assertTrue(_vec_res.isEqualTo(_vec_c));
		
		_vec_res.opEquals(_vec_a);
		_vec_res.opMinusEquals(_vec_b);
		assertTrue(_vec_res.isEqualTo(_vec_d));
		
		_vec_res.opMulEquals(2.0f);
		assertTrue(_vec_res.isEqualTo(_vec_e));
		
		_vec_res.opDivEquals(2.0f);
		assertTrue(_vec_res.isEqualTo(_vec_d));
	}
	@Test
	public void testOperators(){
		SpaceVector _vec_a = new SpaceVector(1.0f, 2.0f, 3.0f);
		SpaceVector _vec_b = new SpaceVector(0.0f, -2.0f, 5.0f);
		SpaceVector _vec_c = new SpaceVector(1.0f, 0.0f, 8.0f);
		SpaceVector _vec_d = new SpaceVector(1.0f, 4.0f, -2.0f);
		SpaceVector _vec_e = new SpaceVector(2.0f, 8.0f, -4.0f);

		SpaceVector _res1 = _vec_a.opPlus(_vec_b);
		assertTrue(_res1.isEqualTo(_vec_c));

		_res1 = _vec_a.opMinus(_vec_b);
		assertTrue(_res1.isEqualTo(_vec_d));
		
		_res1 = _vec_d.opMul(2.0f);
		assertTrue(_res1.isEqualTo(_vec_e));
		
		SpaceVector _res2 = _res1.opDiv(2.0f);
		assertTrue(_res2.isEqualTo(_vec_d));
	}
	
	@Test
	public void testStringOps(){
		SpaceVector _vec_a = new SpaceVector(1.0f, 2.0f, 3.0f);
		SpaceVector _vec_b = new SpaceVector(0.0f, -2.0f, 5.0f);

		assertTrue(_vec_b.isEqualTo(SpaceVector.parseVector(_vec_b.toString())));
		assertFalse(_vec_b.isEqualTo(SpaceVector.parseVector(_vec_a.toString())));
		
		String[] _tab_good_exp ={"0.0 0.0 0.0 1.0", "1.0 2 -02.3 1", "1 2 .0 2.0", "1 2.00000 5 2","0.05 -2.3 2.65 2", "00001 0000. 0002 5"};
		for (String _tab_str : _tab_good_exp){
			try{
				_vec_a.opEquals(SpaceVector.parseVector(_tab_str));
			}
			catch(NumberFormatException _e){
				fail("should not fail here :" +_e.getMessage());
			}
		}
		
		String[] _tab_bad_exp = {"1.a 2 4 1", "0..0 5 2 1", "1", null, "1.0 ..2 10 1", "1 b 4 1","2 3 1", " 1 2 2 1","1  2 3 1","1 1 1 0", "1 2 3 0.00000001"};
		for (String _tab_str : _tab_bad_exp){
			try{
				_vec_a.opEquals(SpaceVector.parseVector(_tab_str));
				fail("should have throw here :"+_tab_str);
			}
			catch(NumberFormatException _e){
				assertTrue(true);
			}
		}
		
		SpaceVector _vec_c = new SpaceVector(1.0f, 2.0f, 3.0f, 2.0f);
		assertTrue(_vec_c.isEqualTo(SpaceVector.parseVector(_vec_c.toString())));
	}
}
