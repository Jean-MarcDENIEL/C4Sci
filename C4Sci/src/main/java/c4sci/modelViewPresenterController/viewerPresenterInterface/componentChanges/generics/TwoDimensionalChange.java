package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class encapsulates the changes expressed by a 2D point (size, position ...).<br>
 * The meaning of the dimensions depends on the sub classes.
 * @author jeanmarc.deniel
 *
 */
public abstract class TwoDimensionalChange extends ComponentChange {

	private PlaneVector		twoDimensionalChange;
	
	private TwoDimensionalChange() {
		super(null, null);
	}

	public TwoDimensionalChange(Component comp_, PlaneVector two_d_value, Command parent_cmd){
		super(comp_, parent_cmd);
		twoDimensionalChange = two_d_value;
	}
	
	public PlaneVector getChange(){
		return twoDimensionalChange;
	}
}
