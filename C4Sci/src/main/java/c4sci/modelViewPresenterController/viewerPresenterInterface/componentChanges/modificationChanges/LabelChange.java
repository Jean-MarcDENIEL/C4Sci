package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.InternationalizableTermChange;
/**
 * this class indicates a label change.
 * @author jeanmarc.deniel
 *
 */
public class LabelChange extends InternationalizableTermChange {

	public LabelChange(Component comp_, InternationalizableTerm label_str, Command parent_cmd) {
		super(comp_, label_str, parent_cmd);
	}

}
