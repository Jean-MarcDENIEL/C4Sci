package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * This class concerns the change parameterized by an integer value.<br>
 * The integer meaning is up to the sub classes.
 * @author jeanmarc.deniel
 *
 */
public abstract class IntegerChange extends ComponentChange {

	private int	integerValue;
	
	public IntegerChange(Component comp_, int int_val, Command parent_cmd) {
		super(comp_, parent_cmd);
		integerValue = int_val;
	}

	public int getChange(){
		return integerValue;
	}

}
