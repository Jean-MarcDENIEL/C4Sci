package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * A change expressed by a unique floating value.<br>
 * The meaning of the value depends on the sub classes.
 * @author jeanmarc.deniel
 *
 */
public abstract class OneDimensionalChange extends ComponentChange {

	private float		oneDimensionalValue;

	private OneDimensionalChange(){
		super(null);
	}
	
	public OneDimensionalChange(DataIdentity comp_id, float one_d_val){
		super(comp_id);
		oneDimensionalValue 	= one_d_val;
	}
	
	/**
	 * 
	 * @return A value ranging from 0.0 (opaque) to 1.0 (transparent)
	 */
	public float getChange(){
		return oneDimensionalValue;
	}

}
