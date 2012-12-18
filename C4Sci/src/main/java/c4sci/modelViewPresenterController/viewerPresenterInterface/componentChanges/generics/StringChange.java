package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * A change that is parameterized by a String (e.g labels ...).
 * @author jeanmarc.deniel
 *
 */
public abstract class StringChange extends ComponentChange {

	private String changeValue;
	
	public StringChange(Component comp_, String str_val, Command parent_cmd) {
		super(comp_, parent_cmd);
		changeValue = str_val;
	}

	public String getChange(){
		return changeValue;
	}

}
