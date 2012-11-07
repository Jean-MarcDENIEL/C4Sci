package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueStepChange;

public class FloatElementModificationStepChange extends ElementValueStepChange {

	private float changeValue;
	
	public FloatElementModificationStepChange(Command parent_command, StepElement step_element, float chg_value) {
		super(parent_command, step_element);
		changeValue= chg_value;
	}
	
	public float getChange(){
		return changeValue;
	}

}
