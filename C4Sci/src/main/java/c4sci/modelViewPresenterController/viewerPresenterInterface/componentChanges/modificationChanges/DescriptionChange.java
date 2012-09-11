package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.InternationalizableTermChange;

/**
 * This class indicate a change in the description of a Component.
 * @author jeanmarc.deniel
 *
 */
public class DescriptionChange extends InternationalizableTermChange {

	public DescriptionChange(DataIdentity comp_id, 			InternationalizableTerm descr_term, Command parent_cmd) {
		super(comp_id, descr_term, parent_cmd);
	}

}
