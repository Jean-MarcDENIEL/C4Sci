package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class indicates that the Component has lost focus.
 * @author jeanmarc.deniel
 *
 */
public class FocusLossChange extends ComponentChange {

	public FocusLossChange(Component comp_, Command parent_cmd) {
		super(comp_, parent_cmd);
	}

}
