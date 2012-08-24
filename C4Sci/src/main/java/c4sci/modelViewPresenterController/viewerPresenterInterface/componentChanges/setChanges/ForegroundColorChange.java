/**
 * 
 */
package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.setChanges;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.ThreeDimensionalChange;

/**
 * 
 * Colors are expressed in the [0-1] range :<ul><li>X means Red</li><li>Y means Green</li><li>Z means Blue.</li>
 * @author jeanmarc.deniel
 *
 */
public class ForegroundColorChange extends ThreeDimensionalChange {


	private ForegroundColorChange() {
		super(null, null);
	}

	
	public ForegroundColorChange(DataIdentity comp_id, SpaceVector fore_color){
		super(comp_id, fore_color);
	}
	

	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_FOREGROUND_COLOR;
	}


}
