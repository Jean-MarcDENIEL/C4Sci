package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;

public class ElementReactiveModificationStepChange extends StepChange {

	private StepElement modifiedElement;
	
	public ElementReactiveModificationStepChange(Command parent_command, StepElement step_elt) {
		super(parent_command);
		setModifiedElement(step_elt);
	}

	public StepElement getModifiedElement() {
		return modifiedElement;
	}

	public void setModifiedElement(StepElement modifiedElement) {
		this.modifiedElement = modifiedElement;
	}

}
