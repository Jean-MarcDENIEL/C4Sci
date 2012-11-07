package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueStepChange;

public class BooleanElementModificationStepChange extends
		ElementValueStepChange {

	private boolean		changeValue;
	
	public BooleanElementModificationStepChange(Command parent_command,
			StepElement step_element, boolean chg_value) {
		super(parent_command, step_element);
		changeValue = chg_value;
	}
	
	public boolean getChange(){
		return changeValue;
	}

}
