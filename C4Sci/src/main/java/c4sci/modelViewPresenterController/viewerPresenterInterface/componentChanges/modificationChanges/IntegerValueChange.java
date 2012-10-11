package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.IntegerChange;

public class IntegerValueChange extends IntegerChange {
	
	public IntegerValueChange(Component comp_, int int_val,
			Command parent_cmd) {
		super(comp_, int_val, parent_cmd);
	}

}
