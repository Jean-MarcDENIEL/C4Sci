package c4sci.math.geometry.space;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.math.geometry.space.SpaceVector.CoorName;

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
}
