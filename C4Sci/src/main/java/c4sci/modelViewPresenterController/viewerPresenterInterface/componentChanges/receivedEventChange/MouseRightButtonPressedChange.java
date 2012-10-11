package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
/**
 * Notifies that the mouse right button has been pressed.
 * @author jeanmarc.deniel
 *
 */
public class MouseRightButtonPressedChange extends MouseChange {

	public MouseRightButtonPressedChange(Component comp_, PlaneVector mouse_coor, Command parent_cmd) {
		super(comp_, mouse_coor, parent_cmd);
	}

}
