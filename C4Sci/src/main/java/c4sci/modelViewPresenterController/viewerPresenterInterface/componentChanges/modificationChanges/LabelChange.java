package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.StringChange;
/**
 * this class indicates a label change.
 * @author jeanmarc.deniel
 *
 */
public class LabelChange extends StringChange {

	public LabelChange(DataIdentity comp_id, String label_str) {
		super(comp_id, label_str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_LABEL;
	}

}