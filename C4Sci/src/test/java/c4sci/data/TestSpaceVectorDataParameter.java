package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.dataParameters.composedModifiables.SpaceVectorDataParameter;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.geometry.space.SpaceVector;

public class TestSpaceVectorDataParameter {

	@Test
	public void test() throws CannotInstantiateParameterException {
		SpaceVectorDataParameter _param = new SpaceVectorDataParameter("spaceVector", new InternationalizableTerm("space vector"), new InternationalizableTerm("space vector data parameter"));
		
		String[] _good_str_tab = {"0 0 0 1","0.0 0.0 0.0 1.0", "1.0 2.0 -2.0 2.0","1 2.0 3 1", "-1 -2.0 2.52 1"};
		for (String _good_str : _good_str_tab){
			try {
				assertTrue("fail on : " + _good_str, _param.validatesRegularExpression(_good_str));
				_param.setValue(_good_str);
			} catch (DataValueParsingException _e) {
				fail("should not throw here : " + _e.getMessage() +" pour : " +_good_str);
			}
		}
		
		String[] _bad_str_tab = {" 0 0 0 1", "a 0 0 1"};
		for (String _bad_str : _bad_str_tab){
			try {
				assertFalse("fail on : "+ _bad_str, _param.validatesRegularExpression(_bad_str));
				_param.setValue(_bad_str);
				fail("should have thrown on " + _bad_str);
			} catch (DataValueParsingException _e) {
				assertTrue(true);
			}
		}
		
		try {
			_param.setValue("1 2 3 0");
		} catch (DataValueParsingException _e) {
			assertTrue("should have throw", true);
		}	
		
		
		try {
			_param.setValue("1.0 2.0 3.0 2.0");
		} catch (DataValueParsingException _e) {
			fail("should not throw");
		}
		assertTrue(new SpaceVector(2f, 4f, 6f).isEqualTo(_param.getSpaceVectorValue()));
		
		_param.setSpaceVectorValue(new SpaceVector(5f, 4f, 3f));
		assertTrue(_param.getSpaceVectorValue().isEqualTo(new SpaceVector(5f, 4f, 3f)));
		
			
		String[] _good_exp_tab = {"1 2 3 4","1 2 5 6","2.3 2.3 1.6 1.0","1. .02 .0 1"};
		for (String _exp : _good_exp_tab){
			assertTrue(_exp, _param.validatesRegularExpression(_exp));
		}
		
		String[] _bad_exp_tab = {"",null,"1 2 3","1","1 2 3 4 5","..02 2 3 4","1 --2 3 4"};
		for (String _exp : _bad_exp_tab){
			assertFalse(_exp, _param.validatesRegularExpression(_exp));
		}
	}

	@Test
	public void testClone() throws InstantiationException, IllegalAccessException, CannotInstantiateParameterException{
		SpaceVectorDataParameter _param = new SpaceVectorDataParameter("test1", 
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

	}

}
