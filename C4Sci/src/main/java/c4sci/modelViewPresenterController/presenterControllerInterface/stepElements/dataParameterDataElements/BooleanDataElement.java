package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements;

import c4sci.data.basicDataParameters.BooleanDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.BooleanValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.DataParameterDataElement;

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



}
