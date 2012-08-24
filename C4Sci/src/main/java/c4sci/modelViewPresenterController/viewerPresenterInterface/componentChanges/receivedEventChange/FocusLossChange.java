package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class indicates that the Component has lost focus.
 * @author jeanmarc.deniel
 *
 */
public class FocusLossChange extends ComponentChange {

	public FocusLossChange(DataIdentity comp_id) {
		super(comp_id);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.RECEIVED_FOCUS_LOSS;
	}

}
