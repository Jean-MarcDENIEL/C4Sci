package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.setChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.OneDimensionalChange;

/**
 * Transparency is ranged between 0.0 (opaque) and 1.0 (purely transparent).
 * @author jeanmarc.deniel
 *
 */
public class TransparencyChange extends OneDimensionalChange {
	/**
	 * 
	 * @param comp_id The component identity to change transparency to.
	 * @param transp_value Transparency value in the [0-1] range
	 */
	public TransparencyChange(DataIdentity comp_id, float transp_value) {
		super(comp_id, transp_value);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_TRANSPARENCY;
	}

}
