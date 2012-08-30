package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.basicDataParameters.FloatDataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestFloatDataParameter {

	@Test
	public void testGetParameterValue() {
		DataParameter _param = new FloatDataParameter("test1", 
				new InternationalizableTerm("test param 1"), 
				new InternationalizableTerm("test param 1 descr"));
		try {
			_param.setParameterValue("0.5");
			_param.setParameterValue("10");
			assertTrue(true);
		} catch (DataValueParsingException e) {
			fail();
		}
		
		try {
			_param.setParameterValue("ae");
			fail();
		} catch (DataValueParsingException e) {
			assertTrue(true);
		}
		
		try {
			_param.setParameterValue(null);
			fail();
		} catch (DataValueParsingException e) {
			assertTrue(true);
		}
		
		assertTrue("10.0".compareTo(_param.getParameterValue())==0);
	}

	@Test
	public void testFloatSetGet(){
		FloatDataParameter _param = new FloatDataParameter("test1", 
				new InternationalizableTerm("test param 1"), 
				new InternationalizableTerm("test param 1 descr"));
		
		try {
			_param.setParameterValue("10.5");
			assertTrue(true);
		} catch (DataValueParsingException e) {
			fail();
		}
		
		assertEquals(_param.getFloatValue(), 10.5, .01);
		
		_param.setFloatValue(2.5f);
		assertEquals(_param.getFloatValue(), 2.5, .01);
	}
	@Test
	public void testClone(){
		FloatDataParameter _param = new FloatDataParameter("test1", 
				new InternationalizableTerm("test param 1"), 
				new InternationalizableTerm("test param 1 descr"));
		DataParameter _clone = _param.getClone();
		assertTrue(_clone.getClass() == _param.getClass());
		assertTrue(_clone.getParameterToken().compareTo(_param.getParameterToken())==0);
		assertTrue(_clone.getParameterName().getDefaultValue().compareTo(_param.getParameterName().getDefaultValue())==0);
		assertTrue(_clone.getParameterDescription().getDefaultValue().compareTo(_param.getParameterDescription().getDefaultValue())==0);

	}

}
