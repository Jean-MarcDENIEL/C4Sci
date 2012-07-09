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
		
		
	}
	
	@Test
	public void testOps(){
		TransformMatrix _mat_1 = TransformMatrix.createIdentityMatrix();
		SpaceVector		_vec_1 = new SpaceVector(1.0f, 2.0f, 3.0f, 1.0f);
		TransformMatrix _mat_2 = _mat_1.opMul(TransformMatrix.createIdentityMatrix());
		assertTrue(_vec_1.isEqualTo(_mat_2.opMul(_vec_1)));
	}

}

