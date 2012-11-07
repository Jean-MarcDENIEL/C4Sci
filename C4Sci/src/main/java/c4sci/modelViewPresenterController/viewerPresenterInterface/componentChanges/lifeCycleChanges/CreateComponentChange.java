package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class encapsulates a message asking for Component creation.
 * @author jeanmarc.deniel
 *
 */
public abstract class CreateComponentChange extends ComponentChange {
	
	/**
	 * Ask for creating a standard component whose identity will be set to the passed argument.<br>
	 *
	 * @param comp_id Identity of the Component to create.
	 * @param parent_id Identity of the parent Component. May be null.
	 */
	public CreateComponentChange(Component comp_, Command parent_cmd) {
		super(comp_, parent_cmd);
	}

}
