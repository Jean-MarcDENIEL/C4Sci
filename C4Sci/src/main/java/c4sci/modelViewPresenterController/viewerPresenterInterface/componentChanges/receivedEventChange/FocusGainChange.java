package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class indicates that the focus has been gained by the Component.
 * @author jeanmarc.deniel
 *
 */
public class FocusGainChange extends ComponentChange {

	public FocusGainChange(Component comp_, Command parent_cmd) {
		super(comp_, parent_cmd);
	}

}
