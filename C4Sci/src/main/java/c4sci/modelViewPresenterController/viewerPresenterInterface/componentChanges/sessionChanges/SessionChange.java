package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.sessionChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class subclasses indicate the beginning or the end of a series of {@link ComponentChange}.<br>
 * This helps the viewer to avoid useless refreshes.<br>
 * @author jeanmarc.deniel
 *
 */
public class SessionChange extends ComponentChange {

	public SessionChange(Component bound_comp, Command parent_command) {
		super(bound_comp, parent_command);
		// TODO Auto-generated constructor stub
	}

}
