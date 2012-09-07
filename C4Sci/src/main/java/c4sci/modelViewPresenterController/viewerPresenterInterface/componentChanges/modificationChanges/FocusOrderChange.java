package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.IntegerChange;

/**
 * This class notifies a change in focus order.
 * @author jeanmarc.deniel
 *
 */
public class FocusOrderChange extends IntegerChange {

	public FocusOrderChange(DataIdentity comp_id, int focus_val, Command parent_cmd) {
		super(comp_id, focus_val, parent_cmd);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_FOCUS_ORDER;
	}

}
