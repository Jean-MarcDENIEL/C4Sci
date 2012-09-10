package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;

public class ElementFeedbackModificationStepChange extends StepChange {

	public ElementFeedbackModificationStepChange(Command parent_command) {
		super(parent_command);
	}

	@Override
	public ChangeID getChangeID() {
		return ChangeID.ELEMENT_FEEDBACK_MODIFICATION;
	}

}
