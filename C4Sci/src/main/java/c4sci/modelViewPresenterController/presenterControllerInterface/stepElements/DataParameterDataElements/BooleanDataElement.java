package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.DataParameterDataElements;

import c4sci.data.basicDataParameters.BooleanDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.BooleanValueBinding;

public class BooleanDataElement extends DataParameterDataElement {

	public BooleanDataElement(BooleanDataParameter data_p) {
		super(data_p);
	}

	@Override
	public ElementBinding getSingleBinding() {
		return new BooleanValueBinding(this, getDataParameter());
	}

	@Override
	public boolean isInternallyCoherent() {
		return true;
	}

	@Override
	public void ensureCoherentInternalState() {}

	@Override
	/**
	 * As a default behavior, it is editable.
	 */
	public boolean isEditable() {
		return true;
	}

}