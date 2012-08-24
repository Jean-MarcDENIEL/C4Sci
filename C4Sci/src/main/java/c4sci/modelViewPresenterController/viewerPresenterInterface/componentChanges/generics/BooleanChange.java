package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class is done for all changes that are parameterized by a boolean value (e.g. visibility ...)
 * @author jeanmarc.deniel
 *
 */
public abstract class BooleanChange extends ComponentChange {

	private boolean 	booleanValue;
	
	public BooleanChange(DataIdentity comp_id, boolean bool_val) {
		super(comp_id);
		booleanValue = bool_val;
	}

	public boolean getChange(){
		return booleanValue;
	}

}
