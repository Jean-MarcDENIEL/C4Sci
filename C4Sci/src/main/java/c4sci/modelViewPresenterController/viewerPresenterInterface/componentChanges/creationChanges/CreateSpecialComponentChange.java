package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.creationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * The special component depends on the implementation of the Presenter and Viewer layers.
 * 
 * @author jeanmarc.deniel
 *
 */
public class CreateSpecialComponentChange extends CreateComponentChange {

	private int	specialComponentIdentity;
	/**
	 * 
	 * @param spec_comp_type The meaning of this flag depends on the Viewer / Presenter implementations.
	 */
	public CreateSpecialComponentChange(DataIdentity comp_id,
			DataIdentity parent_id, int spec_comp_type, Command parent_cmd) {
		super(comp_id, parent_id, parent_cmd);
		specialComponentIdentity = spec_comp_type;
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.CREATE_SPECIAL_COMPONENT;
	}
	
	/**
	 * Gives a flag identifying the special component to create.
	 * @return The meaning of this flag depends on the Viewer / Presenter implementations.
	 */
	public int getChange(){
		return specialComponentIdentity;
	}

}
