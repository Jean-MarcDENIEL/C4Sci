package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements;

import c4sci.data.basicDataParameters.BooleanDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.BooleanValueBinding;

public class BooleanComputedDataElement extends ComputedDataElement {

	public BooleanComputedDataElement(BooleanDataParameter data_p) {
		super(data_p);
	}

	@Override
	protected ElementBinding getSingleBinding() {
		return new BooleanValueBinding(this, getDataParameter());
	}

}
