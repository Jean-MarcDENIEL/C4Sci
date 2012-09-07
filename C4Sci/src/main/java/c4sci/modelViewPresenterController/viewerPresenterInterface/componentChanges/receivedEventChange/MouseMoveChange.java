package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * Indicates that the mouse has been moved.
 * @author jeanmarc.deniel
 *
 */
public class MouseMoveChange extends MouseChange {

	public MouseMoveChange(DataIdentity comp_id, PlaneVector mouse_coor, Command parent_cmd) {
		super(comp_id, mouse_coor, parent_cmd);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.RECEIVED_MOUSE_MOVE;
	}

}
