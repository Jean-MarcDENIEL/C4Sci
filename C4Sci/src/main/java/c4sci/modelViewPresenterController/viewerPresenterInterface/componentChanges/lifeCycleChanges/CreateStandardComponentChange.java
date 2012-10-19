package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily;

/**
 * This class encapsulates an ask for creating a Component among the standard ComponentFactory set.
 * 
 * @author jeanmarc.deniel
 *
 */
public class CreateStandardComponentChange extends CreateComponentChange {

	private ComponentFamily.StandardComponentSet	compType;
	
	public CreateStandardComponentChange(Component comp_, ComponentFamily.StandardComponentSet comp_type, Command parent_cmd) {
		super(comp_, parent_cmd);
		compType = comp_type;
	}
	
	public ComponentFamily.StandardComponentSet getChange(){
		return compType;
	}

}
