package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.ThreeDimensionalChange;
/**
 * Colors are expressed in the [0-1] range :<ul><li>X means Red</li><li>Y means Green</li><li>Z means Blue.</li>
 * @author jeanmarc.deniel
 *
 */
public class FontColorChange extends ThreeDimensionalChange {

	public FontColorChange(DataIdentity comp_id, SpaceVector font_color) {
		super(comp_id, font_color);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_FONT_COLOR;
	}

}
