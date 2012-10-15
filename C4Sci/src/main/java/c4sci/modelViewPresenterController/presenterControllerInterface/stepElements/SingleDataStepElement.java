package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
/**
 * This class corresponds to a single data : it may be a logical one (i.e. transitively referring to another and filtering some services) or an implementation one.
 * <br>
 * @author jeanmarc.deniel
 *
 */
public abstract class SingleDataStepElement extends StepElement {

	public SingleDataStepElement() {}
	
	@Override
	public boolean containsProperValue() {
		return true;
	}
	
	public abstract ElementBinding getSingleBinding();
}
