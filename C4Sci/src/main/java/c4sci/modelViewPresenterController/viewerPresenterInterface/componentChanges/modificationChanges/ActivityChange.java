package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.BooleanChange;
/**
 * This class indicates whether a Component is active (i.e it can received user interactions) or not.
 * @author jeanmarc.deniel
 *
 */
public class ActivityChange extends BooleanChange {

	public ActivityChange(Component comp_, boolean activity_val, Command parent_cmd) {
		super(comp_, activity_val, parent_cmd);
	}

}
