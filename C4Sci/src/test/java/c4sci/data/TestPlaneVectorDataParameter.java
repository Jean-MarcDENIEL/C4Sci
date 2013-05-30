package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.basicDataParameters.PlaneVectorDataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.geometry.plane.PlaneVector;

public class TestPlaneVectorDataParameter {

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		PlaneVectorDataParameter _param = new PlaneVectorDataParameter("planeVector", new InternationalizableTerm("plane vector"), new InternationalizableTerm("3D vector with 4 components"));
		
		assertTrue("got "+_param.getValue(), _param.getValue().compareTo("0.0 0.0") == 0);
		assertTrue("planeVector".compareTo(_param.getParameterToken())==0);
		assertTrue("plane vector".compareTo(_param.getParameterName().getDefaultValue())==0);
		assertTrue("3D vector with 4 components".compareTo(_param.getParameterDescription().getDefaultValue())==0);
		
		String[] _tab_good_str={"0 0", "0.0 0", "0 0.0","-1 2","-1.0000 2.00"};
		for (String _good_dtr : _tab_good_str){
			try {
				_param.setValue(_good_dtr);
			} catch (DataValueParsingException _e) {
				fail("should not throw here");
			}
		}
		String[] _tab_bad_str={null, "", " ","0 0 0","a","a e","1 a","1.0 2a" };
		for (String _bad_str : _tab_bad_str){
			try {
				_param.setValue(_bad_str);
				fail("should have thrown");
			} catch (DataValueParsingException _e) {
				assertTrue(true);
			}
		}
		
		assertTrue("-1.0 2.0".compareTo(_param.getValue()) == 0);
		assertTrue(new PlaneVector(-1f, 2f).isEqualTo(_param.getPlaneVectorValue()));
		
		_param.setPlaneVectorValue(new PlaneVector(1f, 3f));
		assertTrue(new PlaneVector(1f, 3f).isEqualTo(_param.getPlaneVectorValue()));
		
		DataParameter _param_bis = _param.getClone();
		assertTrue(_param_bis.getClass() == _param.getClass());
		assertTrue(_param_bis.getParameterDescription().getDefaultValue().compareTo(_param.getParameterDescription().getDefaultValue())==0);
		assertTrue(_param_bis.getParameterName().getDefaultValue().compareTo(_param.getParameterName().getDefaultValue())==0);
		assertTrue(_param_bis.getParameterToken().compareTo(_param.getParameterToken())==0);
		assertFalse(_param_bis.getParameterToken().compareTo(_param_bis.getParameterName().getDefaultValue())==0);
		assertFalse(_param_bis.getParameterToken().compareTo(_param_bis.getParameterDescription().getDefaultValue())==0);
		
		String[] _good_str_tab = {"1 2","1.2 3.25",".002 .2","-.225 25.","0.25 -25"};
		for(String _str : _good_str_tab){
			assertTrue(_str, _param.validatesRegularExpression(_str));
		}
		String[] _bad_str_tab = {"1","0.2","1.2 .","--2 25","3 2 5"};
		for(String _str : _bad_str_tab){
			assertFalse(_str, _param.validatesRegularExpression(_str));
		}
	}

}
