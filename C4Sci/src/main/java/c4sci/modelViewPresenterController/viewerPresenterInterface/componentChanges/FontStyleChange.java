package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

public class FontStyleChange extends StringChange {

	public FontStyleChange(DataIdentity comp_id, String font_style) {
		super(comp_id, font_style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_FONT_STYLE;
	}

}
