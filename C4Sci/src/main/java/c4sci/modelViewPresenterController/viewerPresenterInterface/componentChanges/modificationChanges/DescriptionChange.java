package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.InternationalizableTermChange;

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
