package c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges;

import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueStepChange;

public class TwoDimensionalElementModificationStepChange extends
		ElementValueStepChange {

	private PlaneVector valueChange;
	
	public TwoDimensionalElementModificationStepChange(Command parent_command, StepElement step_element, PlaneVector two_dim_chg) {
		super(parent_command, step_element);
		valueChange = two_dim_chg;
	}
	
	public PlaneVector getChange(){
		return valueChange;
	}

}
