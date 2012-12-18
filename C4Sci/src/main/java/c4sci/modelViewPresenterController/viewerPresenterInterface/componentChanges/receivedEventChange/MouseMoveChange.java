package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
/**
 * Indicates that the mouse has been moved.
 * @author jeanmarc.deniel
 *
 */
public class MouseMoveChange extends MouseChange {

	public MouseMoveChange(Component comp_, PlaneVector mouse_coor, Command parent_cmd) {
		super(comp_, mouse_coor, parent_cmd);
	}

}
