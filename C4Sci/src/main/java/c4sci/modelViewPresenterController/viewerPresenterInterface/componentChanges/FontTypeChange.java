package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

public class FontTypeChange extends StringChange {

	public FontTypeChange(DataIdentity comp_id, String font_name) {
		super(comp_id, font_name);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_FONT_TYPE;
	}

}
