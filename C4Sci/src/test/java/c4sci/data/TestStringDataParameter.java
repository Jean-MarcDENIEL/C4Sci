package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.basicDataParameters.StringDataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestStringDataParameter {

	@Test
	public void testGetSetParameterValue() {
		StringDataParameter _param_1 = new StringDataParameter("param1",
				new InternationalizableTerm("param one"),
				new InternationalizableTerm("param one to test"));
		try {
			_param_1.setParameterValue("param1 value");
			assertTrue(true);
			assertTrue("param1 value".compareTo(_param_1.getParameterValue())==0);
		} catch (DataValueParsingException e) {
			fail("should not have raised an exception");
		}
	}

	@Test
	public void testGetSetStringValue() {
		StringDataParameter _param_1 = new StringDataParameter("param1",
				new InternationalizableTerm("param one"),
				new InternationalizableTerm("param one to test"));
		_param_1.setStringValue("param1 value");

		assertTrue("param1 value".compareTo(_param_1.getStringValue())==0);

	}
}

