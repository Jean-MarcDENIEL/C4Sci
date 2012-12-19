package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;

/**
 * this class describes a change in a property, that can be expressed through a String value.
 * @author jeanmarc.deniel
 *
 */
public class ElementReactiveModificationStepChange extends StepChange {

	private String		modificationValue;
	
	public ElementReactiveModificationStepChange(Command parent_command, StepElement step_elt, String modification_value) {
		super(parent_command, step_elt);
		setModificationValue(modification_value);
	}

	public final String getModificationValue() {
		return modificationValue;
	}

	public final void setModificationValue(String modification_value) {
		this.modificationValue = modification_value;
	}

}
