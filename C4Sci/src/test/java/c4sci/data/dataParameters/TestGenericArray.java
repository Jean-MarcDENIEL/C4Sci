package c4sci.data.dataParameters;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.dataParameters.GenericArrayDataParameter;
import c4sci.data.dataParameters.basicModifiables.BooleanModifiable;
import c4sci.data.dataParameters.basicModifiables.DoubleModifiable;
import c4sci.data.dataParameters.basicModifiables.FloatModifiable;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.internationalization.InternationalizableTerm;

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
			
			for (int _i=0; _i<_size; _i++){
				_bool_array.accessElement(_i).setBooleanValue((_i+1)%2 == 0);
				_float_array.accessElement(_i).setFloatValue((float) Math.pow(0.5, _i));
				_double_array.accessElement(_i).setDoubleValue(Math.pow(0.5, _i)*0.002);
			}
						
			GenericArrayDataParameter<BooleanModifiable> 	_bool_clone 	= (GenericArrayDataParameter<BooleanModifiable>) _bool_array.getClone();
			GenericArrayDataParameter<FloatModifiable> 		_float_clone	= (GenericArrayDataParameter<FloatModifiable>) _float_array.getClone();
			GenericArrayDataParameter<DoubleModifiable> 	_double_clone	= (GenericArrayDataParameter<DoubleModifiable>) _double_array.getClone();
			
			for (int _i=0; _i<_size; _i++){
				assertTrue("Bools should be equal but " + _bool_clone.accessElement(_i).getBooleanValue() + "instead of " + _bool_array.accessElement(_i).getBooleanValue(), 
						_bool_clone.accessElement(_i).getBooleanValue() == _bool_array.accessElement(_i).getBooleanValue());
				assertTrue("Float should be equal but " + _float_clone.accessElement(_i).getFloatValue() + "instead of " + _float_clone.accessElement(_i).getFloatValue(), 
						_float_clone.accessElement(_i).getFloatValue() == _float_array.accessElement(_i).getFloatValue());
				assertTrue("double should be equal but " + _double_clone.accessElement(_i).getDoubleValue() + "instead of " + _double_clone.accessElement(_i).getDoubleValue(), 
						_double_clone.accessElement(_i).getDoubleValue() == _double_array.accessElement(_i).getDoubleValue());
			}
		} catch (CannotInstantiateParameterException e) {
			fail("should not throw");
		}
		
		
		
	}


}
