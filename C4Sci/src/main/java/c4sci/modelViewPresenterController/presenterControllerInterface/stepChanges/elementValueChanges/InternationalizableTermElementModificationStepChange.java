package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges;

import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueStepChange;

public class InternationalizableTermElementModificationStepChange extends
		ElementValueStepChange {

	private InternationalizableTerm valueChange;
	
	public InternationalizableTermElementModificationStepChange(
			Command parent_command, StepElement step_element, InternationalizableTerm value_chg) {
		super(parent_command, step_element);
		valueChange = value_chg;
	}

	public InternationalizableTerm getChange(){
		return valueChange;
	}
}
