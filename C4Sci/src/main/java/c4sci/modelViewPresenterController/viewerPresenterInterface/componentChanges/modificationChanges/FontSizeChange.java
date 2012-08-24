package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange.ChangeID;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.OneDimensionalChange;

public class FontSizeChange extends OneDimensionalChange {

	/**
	 * 
	 * @param comp_id The modified component identity.
	 * @param font_size The font size in point.
	 */
	public FontSizeChange(DataIdentity comp_id, float font_size) {
		super(comp_id, font_size);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_FONT_SIZE;
	}

}
