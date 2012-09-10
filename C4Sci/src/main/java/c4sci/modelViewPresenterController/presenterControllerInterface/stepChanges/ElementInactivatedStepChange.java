package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;

public class ElementInactivatedStepChange extends StepChange {

	public ElementInactivatedStepChange(Command parent_command) {
		super(parent_command);
	}

	@Override
	public ChangeID getChangeID() {
		return ChangeID.ELEMENT_INACTIVATED;
	}

}
