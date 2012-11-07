package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
/**
 * This class notifies that the mouse left button has been released.<br>
 * @see MouseChange Mouse coordinates system.
 * @author jeanmarc.deniel
 *
 */
public class MouseLeftButtonReleasedChange extends MouseChange {

	public MouseLeftButtonReleasedChange(Component comp_, PlaneVector mouse_coor, Command parent_cmd) {
		super(comp_, mouse_coor, parent_cmd);
	}

}
