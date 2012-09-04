package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements.scalableElements;

import c4sci.data.DataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements.ComputedDataElement;
/**
 * This class corresponds to a value that is expressed relatively to unit and is an information given by the application.
 * @author jeanmarc.deniel
 *
 */
public abstract class ScalableComputedDataElement extends ComputedDataElement {

	private UnitScales		unitScales;
	
	public ScalableComputedDataElement(DataParameter data_p, UnitScales units_) {
		super(data_p);
		unitScales = units_;
	}

	@Override
	public UnitScales getUnits() {
		return unitScales;
	}


}
