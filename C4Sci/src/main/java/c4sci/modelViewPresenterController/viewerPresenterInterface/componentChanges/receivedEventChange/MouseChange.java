package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.TwoDimensionalChange;

/**
 * This class notifies an event related to the mouse use.<br>
 * 
 * Mouse coordinates are those : X = width dimension, Y = height dimension
 * Mouse coordinates are given relatively to the Component size :
 * <ul>
 * 		<li> [0-1] is the range inside the Component size. e.g [0,0] = the upper left Component's corner.</li>
 * 		<li> negative values mean under the lower limits of the Component. e.g [-1, 0] means "on the upper bound, but outside the left bound by a distance equal to the width of the component".</li>
 * 		<li> values above 1 mean over the upper limit of the Component. e.g [0.5, 1.5] means "left-right centered but outside the lower bound 
 * </ul>
 * 
 * @author jeanmarc.deniel
 *
 */
public abstract class MouseChange extends TwoDimensionalChange {

	public MouseChange(DataIdentity comp_id, PlaneVector mouse_coor) {
		super(comp_id, mouse_coor);
	}
}
