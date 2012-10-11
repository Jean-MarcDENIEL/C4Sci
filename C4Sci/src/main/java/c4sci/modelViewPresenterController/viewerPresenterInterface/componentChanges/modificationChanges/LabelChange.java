package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.StringChange;
/**
 * this class indicates a label change.
 * @author jeanmarc.deniel
 *
 */
public class LabelChange extends StringChange {

	public LabelChange(Component comp_, String label_str, Command parent_cmd) {
		super(comp_, label_str, parent_cmd);
	}

}
