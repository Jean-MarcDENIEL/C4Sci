package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
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
		super(null);
	}

	public TwoDimensionalChange(DataIdentity comp_id, PlaneVector two_d_value){
		super(comp_id);
		twoDimensionalChange = two_d_value;
	}
	
	public PlaneVector getChange(){
		return twoDimensionalChange;
	}
}
