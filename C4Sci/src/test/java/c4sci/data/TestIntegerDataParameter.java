package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestIntegerDataParameter {

	@Test
	public void test() {
		IntegerDataParameter _param = new IntegerDataParameter("testParam", 
				new InternationalizableTerm("testing parameter"), 
				new InternationalizableTerm("integer testing parameter"));
		
		assertTrue("0".compareTo(_param.getParameterValue())==0);
		assertTrue("testParam".compareTo(_param.getParameterToken())==0);
		assertTrue("testing parameter".compareTo(_param.getParameterName().getDefaultValue())==0);
		assertTrue("integer testing parameter".compareTo(_param.getParameterDescription().getDefaultValue())==0);
		
		try {
			_param.setParameterValue("20");
			assertTrue(true);
		} catch (DataValueParsingException _e) {
			fail(_e.getStringToParse());
		}
		
		try{
			_param.setParameterValue("a25");
			fail("should have thrown an exception");
		}
		catch(DataValueParsingException _e){
			assertTrue(true);
		}
	
		try{
			_param.setParameterValue(null);
			fail("should have thrown an exception");
		}
		catch(DataValueParsingException _e){
			assertTrue(true);
		}
		assertTrue("20".compareTo(_param.getParameterValue())==0);
		
		assertEquals(_param.getIntegerValue(), 20);
		
		_param.setIntegerValue(3);
		assertEquals(_param.getIntegerValue(), 3);
	}

}
