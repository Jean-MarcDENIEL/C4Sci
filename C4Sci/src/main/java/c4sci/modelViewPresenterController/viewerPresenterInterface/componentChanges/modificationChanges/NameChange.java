package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.InternationalizableTermChange;

/**
 * This class indicates a change of name
 * @author jeanmarc.deniel
 *
 */
public class NameChange extends InternationalizableTermChange {

	public NameChange(Component comp_, InternationalizableTerm name_value, Command parent_cmd) {
		super(comp_, name_value, parent_cmd);
	}
}
