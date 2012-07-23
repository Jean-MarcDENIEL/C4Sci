package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.basicDataParameters.BooleanDataParameter;
import c4sci.data.basicDataParameters.IntegerDataParameter;
import c4sci.data.exceptions.NoSuchParameterException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestDataParameterFactory {

	@SuppressWarnings("unused")
	@Test
	public void testAddDataParameterPrototype() {
		DataParameterFactory _factory = new DataParameterFactory();

		_factory.addDataParameterPrototype("firstData", new IntegerDataParameter("firstParam", 
				new InternationalizableTerm("First Parameter"), 
				new InternationalizableTerm("First DataFirst Parameter")));
		_factory.addDataParameterPrototype("firstData", new BooleanDataParameter("2ndParam", 
				new InternationalizableTerm("2nd parameter"), 
				new InternationalizableTerm("A boolean as 2nd parameter")));
		
		_factory.addDataParameterPrototype(null, null);
		_factory.addDataParameterPrototype("2ndData", null);

		try{
			DataParameter _param_1 = _factory.createDataParameter("firstData", "firstParam");
			assertTrue(_param_1.getClass() == IntegerDataParameter.class);
			assertTrue(_param_1.getParameterToken().compareTo("firstParam")==0);
			assertTrue(_param_1.getParameterName().getDefaultValue().compareTo("First Parameter")==0);
			assertTrue(_param_1.getParameterDescription().getDefaultValue().compareTo("First DataFirst Parameter")==0);
		}
		catch(NoSuchParameterException _e){
			fail("Should not fail here");
		}

		try {
			DataParameter _param_1 = _factory.createDataParameter("kj", "firstParam");
			fail("should have rised an exception");
		} catch (NoSuchParameterException _e) {
			assertTrue(true);
		}

		try {
			DataParameter _param_1 = _factory.createDataParameter("firstData", "lk");
			fail("should have rised an exception");
		} catch (NoSuchParameterException _e) {
			assertTrue(true);
		}
		try {
			DataParameter _param_1 = _factory.createDataParameter("2ndData", "firstParam");
			fail("should have rised an exception");
		} catch (NoSuchParameterException e) {
			assertTrue(true);
		}
		

	}


}
