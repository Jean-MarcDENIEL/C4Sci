package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

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
	public CreateSpecialComponentChange(Component comp_, int spec_comp_type, Command parent_cmd) {
		super(comp_, parent_cmd);
		specialComponentIdentity = spec_comp_type;
	}
	
	/**
	 * Gives a flag identifying the special component to create.
	 * @return The meaning of this flag depends on the Viewer / Presenter implementations.
	 */
	public int getChange(){
		return specialComponentIdentity;
	}

}
