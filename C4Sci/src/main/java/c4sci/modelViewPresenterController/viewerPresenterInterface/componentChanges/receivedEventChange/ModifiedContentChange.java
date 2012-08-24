package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class indicates that the content of the component has been modified.<br>
 * This class must then sub classed in order to take into account each case of content upgrade.
 * 
 * @author jeanmarc.deniel
 *
 */
public class ModifiedContentChange extends ComponentChange {

	public ModifiedContentChange(DataIdentity comp_id) {
		super(comp_id);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.RECEIVED_MODIFIED_CONTENT;
	}

}
