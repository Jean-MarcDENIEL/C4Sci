package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.StringChange;

public class FontStyleChange extends StringChange {

	public FontStyleChange(DataIdentity comp_id, String font_style, Command parent_cmd) {
		super(comp_id, font_style, parent_cmd);
	}

}
