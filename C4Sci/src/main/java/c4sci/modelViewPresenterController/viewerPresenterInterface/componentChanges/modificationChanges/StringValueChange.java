package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.StringChange;

public class StringValueChange extends StringChange {

	public StringValueChange(Component comp_, String str_val, Command parent_cmd) {
		super(comp_, str_val, parent_cmd);
	}

}
