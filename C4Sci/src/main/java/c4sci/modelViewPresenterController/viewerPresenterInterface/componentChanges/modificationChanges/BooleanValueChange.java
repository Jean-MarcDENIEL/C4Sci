package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.BooleanChange;

public class BooleanValueChange extends BooleanChange {

	public BooleanValueChange(Component comp_, boolean bool_val,
			Command parent_cmd) {
		super(comp_, bool_val, parent_cmd);
	}

}
