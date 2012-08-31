package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements.scaledElements;

import c4sci.data.DataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.IntegerValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

public class IntegerComputedDataElement extends ScaledComputedDataElement {

	public IntegerComputedDataElement(DataParameter data_p, UnitScales units_) {
		super(data_p, units_);
	}

	@Override
	protected ElementBinding getSingleBinding() {
		return new IntegerValueBinding(this, getDataParameter());
	}
}
