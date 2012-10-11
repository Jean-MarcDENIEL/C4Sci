package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.StringChange;

public class FontStyleChange extends StringChange {

	public FontStyleChange(Component comp_, String font_style, Command parent_cmd) {
		super(comp_, font_style, parent_cmd);
	}

}
