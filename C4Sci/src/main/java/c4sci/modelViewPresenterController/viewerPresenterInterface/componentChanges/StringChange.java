package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
/**
 * A change that is parameterized by a String (e.g labels ...).
 * @author jeanmarc.deniel
 *
 */
public abstract class StringChange extends ComponentChange {

	private String changeValue;
	
	public StringChange(DataIdentity comp_id, String str_val) {
		super(comp_id);
		changeValue = str_val;
	}

	public String getChange(){
		return changeValue;
	}

}
