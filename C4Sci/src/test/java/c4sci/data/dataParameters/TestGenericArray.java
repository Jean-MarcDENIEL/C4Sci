package c4sci.data.dataParameters;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.dataParameters.GenericArrayDataParameter;
import c4sci.data.dataParameters.composedModifiables.PlaneVectorDataParameter;
import c4sci.data.dataParameters.composedModifiables.SpaceVectorDataParameter;
import c4sci.data.dataParameters.singleValueModifiables.BooleanModifiable;
import c4sci.data.dataParameters.singleValueModifiables.DoubleModifiable;
import c4sci.data.dataParameters.singleValueModifiables.FloatModifiable;
import c4sci.data.dataParameters.singleValueModifiables.IntegerModifiable;
import c4sci.data.dataParameters.singleValueModifiables.NoWhiteSpaceStringModifiable;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.math.geometry.space.SpaceVector;

public class TestGenericArray {

	@SuppressWarnings("unchecked")
	@Test
	public void testArraysOfSimpleModifiables() {
		
		int _size = 4;
		
		try {
			
			GenericArrayDataParameter<BooleanModifiable> _bool_array = new GenericArrayDataParameter<BooleanModifiable>(
					new BooleanModifiable[_size],
					"boolArray", 
					new InternationalizableTerm("boolean array"), 
					new InternationalizableTerm("boolean array descr"));
			GenericArrayDataParameter<FloatModifiable> _float_array = new GenericArrayDataParameter<FloatModifiable>(
					new FloatModifiable[_size], 
					"floatArray", 
					new InternationalizableTerm("float array"), 
					new InternationalizableTerm("float array descr"));
			GenericArrayDataParameter<DoubleModifiable> _double_array = new GenericArrayDataParameter<DoubleModifiable>(
					new DoubleModifiable[_size], 
					"doubleArray", 
					new InternationalizableTerm("double array"), 
					new InternationalizableTerm("double array descr"));
			GenericArrayDataParameter<IntegerModifiable> _int_array = new GenericArrayDataParameter<IntegerModifiable>(new IntegerModifiable[_size], 
					"intArray", new InternationalizableTerm("double array"), 
					new InternationalizableTerm("double array descr"));
			GenericArrayDataParameter<NoWhiteSpaceStringModifiable> _str_array = new GenericArrayDataParameter<NoWhiteSpaceStringModifiable>(
					new NoWhiteSpaceStringModifiable[_size], 
					"strArray", 
					new InternationalizableTerm("string array"), 
					new InternationalizableTerm("string array descr"));
			GenericArrayDataParameter<PlaneVectorDataParameter> _plane_vec_array = new GenericArrayDataParameter<PlaneVectorDataParameter>(
					new PlaneVectorDataParameter[_size], 
					"planeVecArray", 
					new InternationalizableTerm("plane vector array"), 
					new InternationalizableTerm("plane vector array descr"));
			GenericArrayDataParameter<SpaceVectorDataParameter> _space_vec_array = new GenericArrayDataParameter<SpaceVectorDataParameter>(
					new SpaceVectorDataParameter[_size], 
					"spaceArray", new InternationalizableTerm("space vector array"), 
					new InternationalizableTerm("space vector array descr"));
			
			int _str_tab_size = 6;
			String _str_values[] = new String[] {"jh jkk", " ku ", "popo", "huhu", "a dde kj", " kjkj k kkk k "};			
			for (int _i=0; _i<_size; _i++){
				_bool_array.accessElement(_i).setBooleanValue((_i+1)%2 == 0);
				_float_array.accessElement(_i).setFloatValue((float) Math.pow(0.5, _i));
				_double_array.accessElement(_i).setDoubleValue(Math.pow(0.5, _i)*0.002);
				_int_array.accessElement(_i).setIntegerValue(_i*4);
				_str_array.accessElement(_i).setStringValue(_str_values[_i%_str_tab_size]);
				_plane_vec_array.accessElement(_i).setPlaneVectorValue(new PlaneVector((float)_i*1.5f, (float) _i * 10.2f));
				_space_vec_array.accessElement(_i).setSpaceVectorValue(new SpaceVector((float)_i*2.3f, (float)_i*-3.6f, (float)_i*(float)_i));
			}
						
			GenericArrayDataParameter<BooleanModifiable> 	_bool_clone 	= (GenericArrayDataParameter<BooleanModifiable>) 	_bool_array.getClone();
			GenericArrayDataParameter<FloatModifiable> 		_float_clone	= (GenericArrayDataParameter<FloatModifiable>) 		_float_array.getClone();
			GenericArrayDataParameter<DoubleModifiable> 	_double_clone	= (GenericArrayDataParameter<DoubleModifiable>) 	_double_array.getClone();
			GenericArrayDataParameter<IntegerModifiable> 	_int_clone		= (GenericArrayDataParameter<IntegerModifiable>) 	_int_array.getClone();
			GenericArrayDataParameter<NoWhiteSpaceStringModifiable> _str_clone = (GenericArrayDataParameter<NoWhiteSpaceStringModifiable>) _str_array.getClone();
			GenericArrayDataParameter<PlaneVectorDataParameter> 	_plane_vec_clone = (GenericArrayDataParameter<PlaneVectorDataParameter>) _plane_vec_array.getClone();
			GenericArrayDataParameter<SpaceVectorDataParameter>		_space_vec_clone = (GenericArrayDataParameter<SpaceVectorDataParameter>) _space_vec_array.getClone();
			
			for (int _i=0; _i<_size; _i++){
				assertTrue("Bools should be equal but " + _bool_clone.accessElement(_i).getBooleanValue() + "instead of " 	+ _bool_array.accessElement(_i).getBooleanValue(), 
						_bool_clone.accessElement(_i).getBooleanValue() == _bool_array.accessElement(_i).getBooleanValue());
				assertTrue("Float should be equal but " + _float_clone.accessElement(_i).getFloatValue() + "instead of " 	+ _float_array.accessElement(_i).getFloatValue(), 
						_float_clone.accessElement(_i).getFloatValue() == _float_array.accessElement(_i).getFloatValue());
				assertTrue("double should be equal but " + _double_clone.accessElement(_i).getDoubleValue() + "instead of " + _double_array.accessElement(_i).getDoubleValue(), 
						_double_clone.accessElement(_i).getDoubleValue() == _double_array.accessElement(_i).getDoubleValue());
				assertTrue("int should be equal but " + _int_clone.accessElement(_i).getIntegerValue() + "instead of " 		+ _int_array.accessElement(_i).getIntegerValue(), 
						_int_clone.accessElement(_i).getIntegerValue() == _int_array.accessElement(_i).getIntegerValue());
				assertTrue("string should be equal but " + _str_clone.accessElement(_i).getStringValue() + "instead of " 	+ _str_array.accessElement(_i).getStringValue(), 
						_str_clone.accessElement(_i).getStringValue().compareTo(_str_array.accessElement(_i).getStringValue()) == 0);
				assertTrue("plane vector should be equal but " + _plane_vec_clone.accessElement(_i).getPlaneVectorValue() + "instead of " 		+ _plane_vec_array.accessElement(_i).getPlaneVectorValue(), 
						_plane_vec_array.accessElement(_i).getPlaneVectorValue().isEqualTo(_plane_vec_clone.accessElement(_i).getPlaneVectorValue()));
				assertTrue("space vector should be equal but " + _space_vec_clone.accessElement(_i).getSpaceVectorValue() + "instead of " 		+ _space_vec_array.accessElement(_i).getSpaceVectorValue(), 
						_space_vec_array.accessElement(_i).getSpaceVectorValue().isEqualTo(_space_vec_clone.accessElement(_i).getSpaceVectorValue()));
			}
			
			for (int _i=0; _i<_size; _i++){
				assertTrue("bools", _bool_array.accessElement(_i).validatesRegularExpression(_bool_clone.accessElement(_i).getValue()));
				assertTrue("float", _float_array.accessElement(_i).validatesRegularExpression(_float_clone.accessElement(_i).getValue()));
				assertTrue("double", _double_array.accessElement(_i).validatesRegularExpression(_double_clone.accessElement(_i).getValue()));
				assertTrue("int", _int_array.accessElement(_i).validatesRegularExpression(_int_clone.accessElement(_i).getValue()));
				assertTrue("string", _str_array.accessElement(_i).validatesRegularExpression(_str_clone.accessElement(_i).getValue()));
				assertTrue("planevector", _plane_vec_array.accessElement(_i).validatesRegularExpression(_plane_vec_clone.accessElement(_i).getValue()));
				assertTrue("spacevector", _space_vec_array.accessElement(_i).validatesRegularExpression(_space_vec_clone.accessElement(_i).getValue()));
			}
			assertTrue("bools : " + _bool_array.getRegExp() + " for : " + _bool_clone.getValue(), _bool_array.validatesRegularExpression(_bool_clone.getValue()));
			assertTrue("float : " + _float_array.getRegExp() + " for : " + _float_clone.getValue(), _float_array.validatesRegularExpression(_float_clone.getValue()));
			assertTrue("double : " + _double_array.getRegExp() + " for : " + _double_clone.getValue(), _double_array.validatesRegularExpression(_double_clone.getValue()));
			assertTrue("int : " + _int_array.getRegExp() + " for : " + _int_clone.getValue(), _int_array.validatesRegularExpression(_int_clone.getValue()));
			assertTrue("string " + _str_array.getRegExp() + " for : " + _str_clone.getValue(), _str_array.validatesRegularExpression(_str_clone.getValue()));
			assertTrue("planevector " + _plane_vec_array.getRegExp() + " for : " + _plane_vec_clone.getValue(), _plane_vec_array.validatesRegularExpression(_plane_vec_clone.getValue()));
			assertTrue("spacevector " + _space_vec_array.getRegExp() + " for : " + _space_vec_clone.getValue(), _space_vec_array.validatesRegularExpression(_space_vec_clone.getValue()));
			
			for (int _i=0; _i<_size; _i++){
				assertFalse("bools", _bool_array.accessElement(_i).validatesRegularExpression(_bool_clone.accessElement(_i).getValue()+"a"));
				assertFalse("float", _float_array.accessElement(_i).validatesRegularExpression(_float_clone.accessElement(_i).getValue()+"a"));
				assertFalse("double", _double_array.accessElement(_i).validatesRegularExpression(_double_clone.accessElement(_i).getValue()+"a"));
				assertFalse("int", _int_array.accessElement(_i).validatesRegularExpression(_int_clone.accessElement(_i).getValue()+"a"));
				assertFalse("string", _str_array.accessElement(_i).validatesRegularExpression(_str_clone.accessElement(_i).getValue()+" a"));
				assertFalse("planevector", _plane_vec_array.accessElement(_i).validatesRegularExpression(_plane_vec_clone.accessElement(_i).getValue()+"a"));
				assertFalse("spacevector", _space_vec_array.accessElement(_i).validatesRegularExpression(_plane_vec_clone.accessElement(_i).getValue()));
				assertFalse("spacevector", _space_vec_array.accessElement(_i).validatesRegularExpression(_space_vec_clone.accessElement(_i).getValue()+"a"));
			}
			
			
		} catch (CannotInstantiateParameterException e) {
			fail("should not throw");
		}
		
		
		
	}


}
