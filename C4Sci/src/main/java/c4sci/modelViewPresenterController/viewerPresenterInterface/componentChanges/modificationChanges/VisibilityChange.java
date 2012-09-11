package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.BooleanChange;

/**
 * This class is related to Components visibility (whether they appear or not).
 * @author jeanmarc.deniel
 *
 */
public class VisibilityChange extends BooleanChange {

	/**
	 * 
	 * @param comp_id The Component whose visibility is change
	 * @param visibility_val true = the component is visible. Otherwise invisible.
	 */
	public VisibilityChange(DataIdentity comp_id, boolean visibility_val, Command parent_cmd) {
		super(comp_id, visibility_val, parent_cmd);
	}

}
