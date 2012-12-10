package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.basicDataParameters.BooleanDataParameter;
import c4sci.data.basicDataParameters.IntegerDataParameter;
import c4sci.data.exceptions.CannotInstantiateParameterException;

public class TestDataParameterFactory {

	@SuppressWarnings("unused")
	@Test
	public void testAddDataParameterPrototype() {
		DataParameterFactory _factory = new DataParameterFactory();

		_factory.addFactoringAbility("firstParam", IntegerDataParameter.class);
		_factory.addFactoringAbility("secondParam", BooleanDataParameter.class); 
		
		_factory.addFactoringAbility(null, null);
		_factory.addFactoringAbility("2ndData", null);

		try{
			DataParameter _param_1 = _factory.createDataParameter("firstParam");
			assertTrue(_param_1.getClass() == IntegerDataParameter.class);
			assertTrue(_param_1.getParameterToken().compareTo("firstParam")==0);
		}
		catch(CannotInstantiateParameterException _e){
			_e.printStackTrace();
			fail("Should not fail here");
		}

		try {
			DataParameter _param_1 = _factory.createDataParameter("otherParam");
			fail("should have rised an exception");
		} catch (CannotInstantiateParameterException _e) {
			assertTrue(true);
		}

	}


}
