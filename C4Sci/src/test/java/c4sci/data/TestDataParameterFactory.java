package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.singleValueModifiables.FloatModifiable;
import c4sci.data.dataParameters.singleValueModifiables.IntegerModifiable;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestDataParameterFactory {

	@SuppressWarnings("unused")
	@Test
	public void testAddDataParameterPrototype() throws CannotInstantiateParameterException {
		DataParameterFactory _factory = new DataParameterFactory();

		_factory.addFactoringAbility("firstParam", new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(), 
				"empty", 
				new InternationalizableTerm("empty"), 
				new InternationalizableTerm("empty")));
		_factory.addFactoringAbility("secondParam", new GenericDataParameter<FloatModifiable>(new FloatModifiable(), 
				"empty", 
				new InternationalizableTerm("empty"), 
				new InternationalizableTerm("empty")));
		
		_factory.addFactoringAbility(null, null);
		_factory.addFactoringAbility("2ndData", null);

		try{
			DataParameter _param_1 = _factory.createDataParameter("firstParam");
			assertTrue(_param_1.getClass() == new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(), 
					"empty", 
					new InternationalizableTerm("empty"), 
					new InternationalizableTerm("empty")).getClass());
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
