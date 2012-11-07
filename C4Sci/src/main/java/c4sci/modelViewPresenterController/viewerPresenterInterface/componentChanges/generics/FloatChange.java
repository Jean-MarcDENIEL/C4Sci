package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

public abstract class FloatChange extends ComponentChange {

	private float	changeValue;
	
	public FloatChange(Component comp_, float new_value, Command parent_command) {
		super(comp_, parent_command);
		changeValue = new_value;
	}
	
	public float getChange(){
		return changeValue;
	}

}
