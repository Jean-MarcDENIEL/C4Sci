package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements;

import c4sci.data.DataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.SingleDataStepElement;


/**
 * This class corresponds to the case of data that have been computed and are not editable.
 * 
 * @author jeanmarc.deniel
 *
 */
public abstract class ComputedDataElement extends SingleDataStepElement {
	public ComputedDataElement(DataParameter data_p) {
		super(data_p);
	}
	@Override
	public boolean isEditable() {
		return false;
	}
	@Override
	public boolean isInternallyCoherent(){
		return true;
	}
	
	@Override
	public void ensureCoherentInternalState() {}

}
