package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.FloatChange;

public class FloatValueChange extends FloatChange {

	public FloatValueChange(DataIdentity comp_id, float new_value,
			Command parent_command) {
		super(comp_id, new_value, parent_command);
	}

	@Override
	public ChangeID getChangeID() {
		return ChangeID.SET_FLOAT_VALUE;
	}

}
