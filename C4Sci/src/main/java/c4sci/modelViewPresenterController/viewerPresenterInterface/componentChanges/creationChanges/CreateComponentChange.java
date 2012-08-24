package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.creationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFactory;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.IntegerChange;

/**
 * This class encapsulates a message asking for Component creation.
 * @author jeanmarc.deniel
 *
 */
public abstract class CreateComponentChange extends ComponentChange {

	private DataIdentity							parentComponentIdentity;
	
	/**
	 * Ask for creating a standard component whose identity will be set to the passed argument.<br>
	 *
	 * @param comp_id Identity of the Component to create.
	 * @param parent_id Identity of the parent Component. May be null.
	 */
	public CreateComponentChange(DataIdentity comp_id, DataIdentity parent_id) {
		super(comp_id);
		parentComponentIdentity = parent_id;
	}
	
	public DataIdentity getParentComponent(){
		return parentComponentIdentity;
	}
}
