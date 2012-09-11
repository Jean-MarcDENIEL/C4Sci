package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.BooleanChange;

public class BooleanValueChange extends BooleanChange {

	public BooleanValueChange(DataIdentity comp_id, boolean bool_val,
			Command parent_cmd) {
		super(comp_id, bool_val, parent_cmd);
	}

	@Override
	public ChangeID getChangeID() {
		return ChangeID.SET_BOOLEAN_VALUE;
	}

}
