package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

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
		
	}

}
