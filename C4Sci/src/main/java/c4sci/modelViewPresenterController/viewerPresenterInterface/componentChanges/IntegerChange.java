package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class concerns the change parameterized by an integer value.<br>
 * The integer meaning is up to the sub classes.
 * @author jeanmarc.deniel
 *
 */
public abstract class IntegerChange extends ComponentChange {

	private int	integerValue;
	
	public IntegerChange(DataIdentity comp_id, int int_val) {
		super(comp_id);
		integerValue = int_val;
	}

	public int getChange(){
		return integerValue;
	}

}
