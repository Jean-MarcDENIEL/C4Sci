package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

public abstract class FloatChange extends ComponentChange {

	private float	changeValue;
	
	public FloatChange(DataIdentity comp_id, float new_value, Command parent_command) {
		super(comp_id, parent_command);
		changeValue = new_value;
	}
	
	public float getChange(){
		return changeValue;
	}

}
