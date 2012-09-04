package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements.scalableElements;

import c4sci.data.basicDataParameters.IntegerDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.IntegerValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

public class IntegerComputedDataElement extends ScalableComputedDataElement {

	public IntegerComputedDataElement(IntegerDataParameter data_p, UnitScales units_) {
		super(data_p, units_);
	}

	@Override
	protected ElementBinding getSingleBinding() {
		return new IntegerValueBinding(this, getDataParameter());
	}
}
