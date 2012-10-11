package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.IntegerChange;

/**
 * This class notifies a change in focus order.
 * @author jeanmarc.deniel
 *
 */
public class FocusOrderChange extends IntegerChange {

	public FocusOrderChange(Component comp_, int focus_val, Command parent_cmd) {
		super(comp_, focus_val, parent_cmd);
	}

}
