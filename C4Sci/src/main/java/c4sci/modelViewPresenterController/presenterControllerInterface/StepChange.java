package c4sci.modelViewPresenterController.presenterControllerInterface;

import c4sci.modelViewPresenterController.jobs.Command;
/**
 * StepChanges relate to {@link ApplicationStep} as well as {@link StepElement} changes.<br>
 * Command ID corresponds to the {@link ChangeID#getChangeValue()} value.
 * 
 * @author jeanmarc.deniel
 *
 */
public abstract class StepChange extends Command {

	private StepElement	stepElement;
	
	public StepChange(Command parent_command, StepElement step_element) {
		super(parent_command);
		setStepElement(step_element);
	}

	public StepElement getStepElement() {
		return stepElement;
	}

	public void setStepElement(StepElement stepElement) {
		this.stepElement = stepElement;
	}
}
