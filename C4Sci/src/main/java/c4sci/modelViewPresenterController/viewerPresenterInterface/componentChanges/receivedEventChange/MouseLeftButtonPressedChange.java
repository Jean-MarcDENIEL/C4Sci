package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
/**
 * Notifies that the mouse left button has been pressed.<br>
 * @see MouseChange mouse coordinates system.
 * @author jeanmarc.deniel
 *
 */
public class MouseLeftButtonPressedChange extends MouseChange {

	public MouseLeftButtonPressedChange(Component comp_, PlaneVector mouse_coor, Command parent_cmd) {
		super(comp_, mouse_coor, parent_cmd);
	}

}
