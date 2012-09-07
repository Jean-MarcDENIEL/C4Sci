package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class indicates that the Component has lost focus.
 * @author jeanmarc.deniel
 *
 */
public class FocusLossChange extends ComponentChange {

	public FocusLossChange(DataIdentity comp_id, Command parent_cmd) {
		super(comp_id, parent_cmd);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.RECEIVED_FOCUS_LOSS;
	}

}
