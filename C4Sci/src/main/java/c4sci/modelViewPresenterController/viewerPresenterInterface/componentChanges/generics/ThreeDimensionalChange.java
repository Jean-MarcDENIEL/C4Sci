package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This abstract class factories all the shared services between changes affected by three floating values.<br>
 * The meaning of the dimensions depend on the sub classes.
 * 
 * @author jeanmarc.deniel
 *
 */
public abstract class ThreeDimensionalChange extends ComponentChange {

	private SpaceVector		value3D;
	
	private ThreeDimensionalChange(){
		super(null);
	}
	/**
	 * 
	 * @param comp_id	The component ID to apply change to.
	 * @param three_d_val The floating change value.</li>
	 */
	public ThreeDimensionalChange(DataIdentity comp_id, SpaceVector three_d_val) {
		super(comp_id);
		value3D = three_d_val;
	}

	/**
	 * 
	 * @return The three floating change values.</li>
	 */
	public SpaceVector getChange(){
		return value3D;
	}

}
