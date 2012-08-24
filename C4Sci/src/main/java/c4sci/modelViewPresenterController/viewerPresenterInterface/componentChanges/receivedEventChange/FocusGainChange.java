package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class indicates that the focus has been gained by the Component.
 * @author jeanmarc.deniel
 *
 */
public class FocusGainChange extends ComponentChange {

	public FocusGainChange(DataIdentity comp_id) {
		super(comp_id);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.RECEIVED_FOCUS_GAIN;
	}

}
