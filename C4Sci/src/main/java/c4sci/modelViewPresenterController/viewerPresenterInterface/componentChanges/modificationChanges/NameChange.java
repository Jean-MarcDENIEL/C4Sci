package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.InternationalizableTermChange;

/**
 * This class indicates a change of name
 * @author jeanmarc.deniel
 *
 */
public class NameChange extends InternationalizableTermChange {

	public NameChange(DataIdentity comp_id, InternationalizableTerm name_value, Command parent_cmd) {
		super(comp_id, name_value, parent_cmd);
	}
}
