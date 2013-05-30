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
			_param.setValue("0.5");
			_param.setValue("10");
			assertTrue(true);
		} catch (DataValueParsingException e) {
			fail();
		}
		
		try {
			_param.setValue("ae");
			fail();
		} catch (DataValueParsingException e) {
			assertTrue(true);
		}
		
		try {
			_param.setValue(null);
			fail();
		} catch (DataValueParsingException e) {
			assertTrue(true);
		}
		
		assertTrue("10.0".compareTo(_param.getValue())==0);
	}

	@Test
	public void testFloatSetGet(){
		FloatDataParameter _param = new FloatDataParameter("test1", 
				new InternationalizableTerm("test param 1"), 
				new InternationalizableTerm("test param 1 descr"));
		
		try {
			_param.setValue("10.5");
			assertTrue(true);
		} catch (DataValueParsingException e) {
			fail();
		}
		
		assertEquals(_param.getFloatValue(), 10.5, .01);
		
		_param.setFloatValue(2.5f);
		assertEquals(_param.getFloatValue(), 2.5, .01);
	}
	@Test
	public void testClone() throws InstantiationException, IllegalAccessException{
		FloatDataParameter _param = new FloatDataParameter("test1", 
				new InternationalizableTerm("test param 1"), 
				new InternationalizableTerm("test param 1 descr"));
		DataParameter _clone = _param.getClone();
		assertTrue(_clone.getClass() == _param.getClass());
		assertTrue(_clone.getParameterToken().compareTo(_param.getParameterToken())==0);
		assertTrue(_clone.getParameterName().getDefaultValue().compareTo(_param.getParameterName().getDefaultValue())==0);
		assertTrue(_clone.getParameterDescription().getDefaultValue().compareTo(_param.getParameterDescription().getDefaultValue())==0);

	}
	
	@Test
	public void testRegexp(){
		FloatDataParameter _param = new FloatDataParameter("test1", 
				new InternationalizableTerm("test param 1"), 
				new InternationalizableTerm("test param 1 descr"));
		String[] _tab_good_strings = {"0", "0.0", "1000", "100.00","0.0001",".0025","+2.3","-2.3"};
		for (String _regexp : _tab_good_strings){
			assertTrue(_param.validatesRegularExpression(_regexp));
		}
		String[] _tab_bad_strings = {"","e","1.a","1..2","1.2.3","--25","+-25"};
		for (String _regexp : _tab_bad_strings){
			assertFalse(_param.validatesRegularExpression(_regexp));
		}
	}

}
