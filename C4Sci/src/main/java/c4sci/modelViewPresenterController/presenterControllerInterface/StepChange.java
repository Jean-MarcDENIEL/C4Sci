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

	public StepChange(Command parent_command) {
		super(parent_command);
		setCommandID(getChangeID().getChangeValue());
	}

	public enum ChangeID {
		
		STEP_FORWARD(1),
		STEP_BACKWARD(2),
		
		ELEMENT_ADDED(3),
		ELEMENT_SUPPRESSED(4),
		ELEMENT_INACTIVATED(5),
		ELEMENT_ACTIVATED(6),
		
		ELEMENT_REACTIVE_MODIFICATION(7),
		ELEMENT_FEEDBACK_MODIFICATION(8),
		
		;
		
		private long changeValue;
		ChangeID(long change_value){changeValue = change_value;}
		public long getChangeValue(){return changeValue;}
	}
	
	public abstract ChangeID getChangeID();

	
}
