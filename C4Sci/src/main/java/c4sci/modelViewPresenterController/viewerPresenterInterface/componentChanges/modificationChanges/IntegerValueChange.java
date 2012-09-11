package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.IntegerChange;

public class IntegerValueChange extends IntegerChange {
	
	public IntegerValueChange(DataIdentity comp_id, int int_val,
			Command parent_cmd) {
		super(comp_id, int_val, parent_cmd);
	}

}
