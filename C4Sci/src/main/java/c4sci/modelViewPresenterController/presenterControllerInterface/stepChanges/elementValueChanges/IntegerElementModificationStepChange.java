package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueStepChange;

public class IntegerElementModificationStepChange extends
		ElementValueStepChange {

	private int	changeValue;
	
	public IntegerElementModificationStepChange(Command parent_command, StepElement step_element, int chg_value) {
		super(parent_command, step_element);
		changeValue = chg_value;
	}

	public int getChange(){
		return changeValue;
	}
}
