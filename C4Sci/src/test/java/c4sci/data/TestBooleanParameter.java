package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.basicDataParameters.BooleanDataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestBooleanParameter {

	@Test
	public void test() {
		BooleanDataParameter _param = new BooleanDataParameter("param", 
				new InternationalizableTerm("boolean param"), 
				new InternationalizableTerm("param descr"));
		
		try {
			_param.setParameterValue("true");
		} catch (DataValueParsingException e) {
			fail();
		}
		assertTrue(_param.getBooleanValue());
		
		try {
			_param.setParameterValue("kj");
		} catch (DataValueParsingException e) {
			fail();
		}
		assertFalse(_param.getBooleanValue());
		
		_param.setBooleanValue(true);
		
		try {
			_param.setParameterValue(null);
			fail();
		} catch (DataValueParsingException e) {
			assertTrue(true);
		}
		assertTrue(_param.getBooleanValue());
		
		assertTrue("true".compareTo(_param.getParameterValue())==0);
		
		DataParameter _clone = _param.getClone();
		assertTrue(_clone.getClass() == _param.getClass());
		assertTrue(_clone.getParameterToken().compareTo(_param.getParameterToken())==0);
		assertTrue(_clone.getParameterName().getDefaultValue().compareTo(_param.getParameterName().getDefaultValue())==0);
		assertTrue(_clone.getParameterDescription().getDefaultValue().compareTo(_param.getParameterDescription().getDefaultValue())==0);
		
		assertFalse(_param.isValidValue(null));
		String[] _tab_good_regexp = {"true","false","lkjklj","2345jkh",",",":llm"};
		for (String _regexp : _tab_good_regexp){
			assertTrue(_regexp,_param.isValidValue(_regexp));
		}
	}

}
