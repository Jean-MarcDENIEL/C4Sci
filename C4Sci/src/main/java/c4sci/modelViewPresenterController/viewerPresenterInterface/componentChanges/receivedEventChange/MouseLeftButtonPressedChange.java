package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * Notifies that the mouse left button has been pressed.<br>
 * @see MouseChange mouse coordinates system.
 * @author jeanmarc.deniel
 *
 */
public class MouseLeftButtonPressedChange extends MouseChange {

	public MouseLeftButtonPressedChange(DataIdentity comp_id, PlaneVector mouse_coor) {
		super(comp_id, mouse_coor);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.RECEIVED_MOUSE_LEFT_BUTTON_PRESSED;
	}

}
