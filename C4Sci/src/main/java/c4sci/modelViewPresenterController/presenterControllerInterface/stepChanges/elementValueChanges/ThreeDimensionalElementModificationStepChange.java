package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges;

import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueStepChange;

public class ThreeDimensionalElementModificationStepChange extends
		ElementValueStepChange {

	private SpaceVector valueChange;
	
	public ThreeDimensionalElementModificationStepChange(Command parent_command, StepElement step_element, SpaceVector value_chg) {
		super(parent_command, step_element);
		valueChange = value_chg;
	}
	
	public SpaceVector getChange(){
		return valueChange;
	}

}
