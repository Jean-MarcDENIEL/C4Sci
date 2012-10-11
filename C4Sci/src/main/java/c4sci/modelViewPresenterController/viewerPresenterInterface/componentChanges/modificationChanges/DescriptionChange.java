package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.InternationalizableTermChange;

/**
 * This class indicate a change in the description of a Component.
 * @author jeanmarc.deniel
 *
 */
public class DescriptionChange extends InternationalizableTermChange {

	public DescriptionChange(Component comp_, 			InternationalizableTerm descr_term, Command parent_cmd) {
		super(comp_, descr_term, parent_cmd);
	}

}
