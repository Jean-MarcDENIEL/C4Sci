package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;

public class ElementValueStepChange extends StepChange {

	public ElementValueStepChange(Command parent_command, StepElement step_element) {
		super(parent_command, step_element);
	}

}
