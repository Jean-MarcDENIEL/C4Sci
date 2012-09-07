package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * Notifies that the mouse right button has been pressed.
 * @author jeanmarc.deniel
 *
 */
public class MouseRightButtonPressedChange extends MouseChange {

	public MouseRightButtonPressedChange(DataIdentity comp_id, PlaneVector mouse_coor, Command parent_cmd) {
		super(comp_id, mouse_coor, parent_cmd);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.RECEIVED_MOUSE_RIGHT_BUTTON_PRESSED;
	}

}
