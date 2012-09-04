package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements.scalableElements;

import c4sci.data.basicDataParameters.FloatDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.FloatValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

public class FloatComputedDataElement extends ScalableComputedDataElement {

	public FloatComputedDataElement(FloatDataParameter data_p, UnitScales units_) {
		super(data_p, units_);
	}

	@Override
	protected ElementBinding getSingleBinding() {
		return new FloatValueBinding(this, getDataParameter());
	}

}
