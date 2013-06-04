package c4sci.data.dataParameters;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.DataParameter;
import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.basicModifiables.NoWhiteSpaceStringModifiable;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestStringModifiableGenericParameter {

	@Test
	public void testGetSetParameterValue() {
		GenericDataParameter<NoWhiteSpaceStringModifiable> _param_1 = new GenericDataParameter<NoWhiteSpaceStringModifiable>(
				new NoWhiteSpaceStringModifiable(),
				"param1",
				new InternationalizableTerm("param one"),
				new InternationalizableTerm("param one to test"));
		try {
			_param_1.setValue("param1 value");
			assertTrue(true);
			assertTrue("param1_value".compareTo(_param_1.getValue())==0);
		} catch (DataValueParsingException e) {
			fail("should not have raised an exception");
		}
	}

	@Test
	public void testGetSetStringValue() {
		GenericDataParameter<NoWhiteSpaceStringModifiable> _param_1 = new GenericDataParameter<NoWhiteSpaceStringModifiable>(
				new NoWhiteSpaceStringModifiable(),
				"param1",
				new InternationalizableTerm("param one"),
				new InternationalizableTerm("param one to test"));
		_param_1.accesValue().setStringValue("param1 value");

		assertTrue("param1 value".compareTo(_param_1.accesValue().getStringValue())==0);

	}
	
	@Test
	public void testClone() throws InstantiationException, IllegalAccessException{
		GenericDataParameter<NoWhiteSpaceStringModifiable> _param = new GenericDataParameter<NoWhiteSpaceStringModifiable>(
				new NoWhiteSpaceStringModifiable(),
				"test1", 
				new InternationalizableTerm("test param 1"), 
				new InternationalizableTerm("test param 1 descr"));
		DataParameter _clone;
		try {
			_clone = _param.getClone();
			assertTrue(_clone.getClass() == _param.getClass());
			assertTrue(_clone.getParameterToken().compareTo(_param.getParameterToken())==0);
			assertTrue(_clone.getParameterName().getDefaultValue().compareTo(_param.getParameterName().getDefaultValue())==0);
			assertTrue(_clone.getParameterDescription().getDefaultValue().compareTo(_param.getParameterDescription().getDefaultValue())==0);
		} catch (CannotInstantiateParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertFalse(_param.validatesRegularExpression(null));
		String[] _tab_good_str ={"534",".",":lk","564klj","a_b", ""};
		for (String _exp : _tab_good_str){
			assertTrue(_exp,_param.validatesRegularExpression(_exp));
		}
		String[] _tab_bad_str = {null," ", "a b"," add","lk "};
		for (String _exp : _tab_bad_str){
			assertFalse("error on :"+_exp, _param.validatesRegularExpression(_exp));
		}
	}

}

