package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.data.DataIdentity;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class indicates a change of InternationalizableTerm.
 * @author jeanmarc.deniel
 *
 */
public abstract class InternationalizableTermChange extends ComponentChange {

	private InternationalizableTerm		termValue;
	
	public InternationalizableTermChange(DataIdentity comp_id, InternationalizableTerm term_value, Command parent_cmd) {
		super(comp_id, parent_cmd);
		termValue = term_value;
	}

	public InternationalizableTerm getChange(){
		return termValue;
	}

}
