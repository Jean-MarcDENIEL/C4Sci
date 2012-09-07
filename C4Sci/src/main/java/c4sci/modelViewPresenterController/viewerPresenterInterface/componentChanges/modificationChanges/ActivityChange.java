package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.BooleanChange;
/**
 * This class indicates whether a Component is active (i.e it can received user interactions) or not.
 * @author jeanmarc.deniel
 *
 */
public class ActivityChange extends BooleanChange {

	public ActivityChange(DataIdentity comp_id, boolean activity_val, Command parent_cmd) {
		super(comp_id, activity_val, parent_cmd);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_ACTIVITY;
	}

}
