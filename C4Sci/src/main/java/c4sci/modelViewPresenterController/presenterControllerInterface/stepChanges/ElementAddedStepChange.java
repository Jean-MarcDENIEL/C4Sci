package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;

public class ElementAddedStepChange extends StepChange {

	public ElementAddedStepChange(Command parent_command) {
		super(parent_command);
	}

	@Override
	public ChangeID getChangeID() {
		return ChangeID.ELEMENT_ADDED;
	}

}
