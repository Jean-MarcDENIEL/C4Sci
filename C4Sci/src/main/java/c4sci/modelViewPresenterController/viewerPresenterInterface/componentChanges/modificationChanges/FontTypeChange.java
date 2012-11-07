package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.StringChange;

public class FontTypeChange extends StringChange {

	public FontTypeChange(Component comp_, String font_name, Command parent_cmd) {
		super(comp_, font_name, parent_cmd);
	}

}
