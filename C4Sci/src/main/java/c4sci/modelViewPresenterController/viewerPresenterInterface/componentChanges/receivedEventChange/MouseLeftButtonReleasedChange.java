package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class notifies that the mouse left button has been released.<br>
 * @see MouseChange Mouse coordinates system.
 * @author jeanmarc.deniel
 *
 */
public class MouseLeftButtonReleasedChange extends MouseChange {

	public MouseLeftButtonReleasedChange(DataIdentity comp_id, PlaneVector mouse_coor, Command parent_cmd) {
		super(comp_id, mouse_coor, parent_cmd);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.RECEIVED_MOUSE_LEFT_BUTTON_RELEASED;
	}

}
