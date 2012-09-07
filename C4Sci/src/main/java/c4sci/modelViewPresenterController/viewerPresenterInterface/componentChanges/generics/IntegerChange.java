package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class concerns the change parameterized by an integer value.<br>
 * The integer meaning is up to the sub classes.
 * @author jeanmarc.deniel
 *
 */
public abstract class IntegerChange extends ComponentChange {

	private int	integerValue;
	
	public IntegerChange(DataIdentity comp_id, int int_val, Command parent_cmd) {
		super(comp_id, parent_cmd);
		integerValue = int_val;
	}

	public int getChange(){
		return integerValue;
	}

}
