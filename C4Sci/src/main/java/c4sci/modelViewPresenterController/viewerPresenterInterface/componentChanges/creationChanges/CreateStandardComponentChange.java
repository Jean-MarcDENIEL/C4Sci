package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.creationChanges;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFactory;

/**
 * This class encapsulates an ask for creating a Component among the standard ComponentFactory set.
 * 
 * @author jeanmarc.deniel
 *
 */
public class CreateStandardComponentChange extends CreateComponentChange {

	private ComponentFactory.StandardComponentSet	compType;
	
	public CreateStandardComponentChange(DataIdentity comp_id, DataIdentity parent_id, ComponentFactory.StandardComponentSet comp_type, Command parent_cmd) {
		super(comp_id, parent_id, parent_cmd);
		compType = comp_type;
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.CREATE_STANDARD_COMPONENT;
	}
	
	public ComponentFactory.StandardComponentSet getChange(){
		return compType;
	}

}
