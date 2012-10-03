package c4sci.modelViewPresenterController.presenterControllerInterface;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.basicDataParameters.IntegerDataParameter;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.IntegerDataElement;

public class TestApplicationStep {

	@Test
	public void testSteps() {
		int nb_steps = 10;
		ApplicationStep[] _steps = new ApplicationStep[nb_steps];
		for (int _i=0; _i<nb_steps; _i++)
			_steps[_i] = new ApplicationStep(
					new InternationalizableTerm("step "+_i), 
					new InternationalizableTerm("step "+_i+" descr"));
		_steps[1].setParentStep(_steps[0]);
		assertTrue(_steps[1].getParentStep() == _steps[0]);
		_steps[0].addSubStep(_steps[1]);
		_steps[0].appendSubStep(_steps[2]);
		_steps[0].appendSubStep(_steps[3]);
		_steps[0].addSubStep(_steps[4]);
		_steps[0].appendSubStep(_steps[5]);
		assertTrue(_steps[1].getPreviousStep()==null);
		assertTrue(_steps[1].getNextStep() == _steps[2]);
		assertTrue(_steps[2].getPreviousStep() == _steps[1]);
		assertTrue(_steps[3].getNextStep() == null);
		assertTrue(_steps[4].getPreviousStep() == null);
		for (int _i=1; _i<6; _i++){
			assertTrue(_steps[_i].getParentStep() == _steps[0]);
			assertTrue(("step "+_i).compareTo(_steps[_i].getStepName().getDefaultValue()) == 0);
			assertTrue(("step "+_i+" descr").compareTo(_steps[_i].getStepDescription().getDefaultValue())==0);
		}
	}
	
	@Test
	public void testElements(){
		ApplicationStep _step = new ApplicationStep(new InternationalizableTerm("step"), new InternationalizableTerm("step descr"));
		for (int _i=0; _i<10; _i++){
			IntegerDataParameter _param = new IntegerDataParameter("int", 
					new InternationalizableTerm("int param"), 
					new InternationalizableTerm("int param descr")); 
			_param.setIntegerValue(_i);
			_step.putElement(_i, new IntegerDataElement(_param));
		}
		for (int _i=0; _i<10; _i++){
			String _value = _step.getElement(_i).getBindings().iterator().next().getBoundData().getValue();
			assertTrue(""+_value+" instead of "+_i, (""+_i).compareTo(_value)==0);
		}
	}

}
