package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;


/**
 * This class corresponds to the case of data that have been computed and are not editable.
 * 
 * @author jeanmarc.deniel
 *
 */
public class ComputedDataElement extends SingleDataStepElement {
	
	private SingleDataStepElement computedElement;
	
	public ComputedDataElement(SingleDataStepElement data_elt) {
		computedElement = data_elt;
	}
	@Override
	public boolean isEditable() {
		return false;
	}
	@Override
	public boolean isInternallyCoherent(){
		return computedElement.isInternallyCoherent();
	}
	
	@Override
	public void ensureCoherentInternalState() {
		computedElement.ensureCoherentInternalState();
	}
	@Override
	public ElementBinding getSingleBinding() {
		return computedElement.getSingleBinding();
	}

	public final SingleDataStepElement getComputedElement(){
		return computedElement;
	}
}
