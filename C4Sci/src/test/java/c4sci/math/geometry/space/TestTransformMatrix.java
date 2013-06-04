package c4sci.math.geometry.space;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTransformMatrix {

	@Test
	public void testFactoryMethods() {
		TransformMatrix _mat_1 = TransformMatrix.createIdentityMatrix();
		SpaceVector		_vec_1 = new SpaceVector(1.0f, 2.0f, 3.0f, 1.0f);
		assertTrue(_vec_1.isEqualTo(_mat_1.opMul(_vec_1)));
		
		TransformMatrix _mat_2 = TransformMatrix.createScaleMatrix(2.0f, 3.0f, 4.0f, 2.0f);
		SpaceVector		_vec_2 = _mat_1.opMul(_mat_2).opMul(_vec_1);
		assertTrue(_vec_2.isEqualTo(new SpaceVector(new SpaceVector(4.0f, 12.0f, 24.0f))));
		
		TransformMatrix _mat_31 = TransformMatrix.createRotationMatrix((float)(Math.PI/2.0), new SpaceVector(1.0f, 0.0f, 0.0f));
		TransformMatrix _mat_32 = TransformMatrix.createRotationMatrix((float)(Math.PI/2.0), new SpaceVector(0.0f, 1.0f, 0.0f));

		SpaceVector[] 	_arg_vec_tab ={
				new SpaceVector(1.0f, 0.0f, 0.0f),
				new SpaceVector(0.0f, 1.0f, 0.0f),
				new SpaceVector(0.0f, 0.0f, 1.0f)
		};
		SpaceVector[]	_res_vec_tab_1 ={
				new SpaceVector(1.0f, 	0.0f, 	0.0f),
				new SpaceVector(0.0f,	0.0f, 	1.0f),
				new SpaceVector(0.0f, 	-1.0f, 	0.0f)
		};
		SpaceVector[]	_res_vec_tab_2 ={
				new SpaceVector(0.0f, 0.0f, -1.0f),
				new SpaceVector(0.0f, 1.0f, 0.0f),
				new SpaceVector(1.0f, 0.0f, 0.0f)
		};
		for (int _i=0; _i<_arg_vec_tab.length; _i++){
			SpaceVector _res_vec_1 =  _mat_31.opMul(_arg_vec_tab[_i]);
			SpaceVector _res_vec_2 =  _mat_32.opMul(_arg_vec_tab[_i]);
			if(!_res_vec_1.isEqualTo(_res_vec_tab_1[_i])){
				fail("err on "+_i+" nth vector : should get " + _res_vec_tab_1[_i].toString() + " instead of " + _res_vec_1);
			}
			if(!_res_vec_2.isEqualTo(_res_vec_tab_2[_i])){
				fail("err on "+_i+" nth vector : should get " + _res_vec_tab_2[_i].toString() + " instead of " + _res_vec_2);
			}
		}

	}
	
	@Test
	public void testOps(){
		TransformMatrix _mat_1 = TransformMatrix.createIdentityMatrix();
		SpaceVector		_vec_1 = new SpaceVector(1.0f, 2.0f, 3.0f, 1.0f);
		TransformMatrix _mat_2 = _mat_1.opMul(TransformMatrix.createIdentityMatrix());
		assertTrue(_vec_1.isEqualTo(_mat_2.opMul(_vec_1)));
	}

	
}

