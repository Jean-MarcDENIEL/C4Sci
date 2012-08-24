package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges;

import c4sci.data.DataIdentity;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class indicates a change of name
 * @author jeanmarc.deniel
 *
 */
public class NameChange extends InternationalizableTermChange {

	public NameChange(DataIdentity comp_id, InternationalizableTerm name_value) {
		super(comp_id, name_value);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_NAME;
	}

}
