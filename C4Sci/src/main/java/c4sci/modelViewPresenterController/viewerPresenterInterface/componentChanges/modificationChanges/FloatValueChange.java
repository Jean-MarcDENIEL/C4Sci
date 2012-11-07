package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.FloatChange;

public class FloatValueChange extends FloatChange {

	public FloatValueChange(Component comp_, float new_value,
			Command parent_command) {
		super(comp_, new_value, parent_command);
	}

}
