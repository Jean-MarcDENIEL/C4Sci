package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class describes the fact of removing a component from the GUI.<br>
 * This component will never be used anymore.
 * 
 * @author jeanmarc.deniel
 *
 */
public class SuppressComponentChange extends ComponentChange {

	public SuppressComponentChange(Component bound_comp, Command parent_command) {
		super(bound_comp, parent_command);
	}

}
