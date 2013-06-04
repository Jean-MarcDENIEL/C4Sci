package c4sci.data.dataParameters;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.DataParameter;
import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.basicModifiables.BooleanModifiable;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestBooleanModifiableGenericParameter {

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		GenericDataParameter<BooleanModifiable> _param = new GenericDataParameter<BooleanModifiable>(new BooleanModifiable(), 
				"param", 
				new InternationalizableTerm("boolean param"), 
				new InternationalizableTerm("param descr"));
		
		try {
			_param.setValue("true");
		} catch (DataValueParsingException e) {
			fail();
		}
		assertTrue(_param.accesValue().getBooleanValue());
		
		try {
			_param.setValue("kj");
		} catch (DataValueParsingException e) {
			fail();
		}
		assertFalse(_param.accesValue().getBooleanValue());
		
		_param.accesValue().setBooleanValue(true);
		
		try {
			_param.setValue(null);
			fail();
		} catch (DataValueParsingException e) {
			assertTrue(true);
		}
		assertTrue(_param.accesValue().getBooleanValue());
		
		assertTrue(BooleanModifiable.TRUE_STR.compareTo(_param.getValue())==0);

		DataParameter _clone;
		try {
			_clone = _param.getClone();
			assertTrue(_clone.getClass() == _param.getClass());
			assertTrue(_clone.getParameterToken().compareTo(_param.getParameterToken())==0);
			assertTrue(_clone.getParameterName().getDefaultValue().compareTo(_param.getParameterName().getDefaultValue())==0);
			assertTrue(_clone.getParameterDescription().getDefaultValue().compareTo(_param.getParameterDescription().getDefaultValue())==0);
		} catch (CannotInstantiateParameterException e) {
			fail("should clone");
		}
		
		assertFalse(_param.validatesRegularExpression(null));
		String[] _tab_good_regexp = {BooleanModifiable.TRUE_STR,BooleanModifiable.FALSE_STR};
		for (String _regexp : _tab_good_regexp){
			assertTrue(_regexp,_param.validatesRegularExpression(_regexp));
		}
		String[] _tab_bad_regexp = {"jkhjkh", null, "kk009"};
		for (String _regexp : _tab_bad_regexp){
			assertFalse(_regexp, _param.validatesRegularExpression(_regexp));
		}
	}

}
