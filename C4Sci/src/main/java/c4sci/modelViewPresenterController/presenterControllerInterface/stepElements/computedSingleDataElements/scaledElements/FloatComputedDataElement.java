package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements.scaledElements;

import c4sci.data.DataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.FloatValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

public class FloatComputedDataElement extends ScaledComputedDataElement {

	public FloatComputedDataElement(DataParameter data_p, UnitScales units_) {
		super(data_p, units_);
	}

	@Override
	protected ElementBinding getSingleBinding() {
		return new FloatValueBinding(this, getDataParameter());
	}

}
