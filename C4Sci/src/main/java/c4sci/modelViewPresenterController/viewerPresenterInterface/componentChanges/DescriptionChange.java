package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges;

import c4sci.data.DataIdentity;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class indicate a change in the description of a Component.
 * @author jeanmarc.deniel
 *
 */
public class DescriptionChange extends InternationalizableTermChange {

	public DescriptionChange(DataIdentity comp_id, 			InternationalizableTerm descr_term) {
		super(comp_id, descr_term);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_DESCRIPTION;
	}

}
