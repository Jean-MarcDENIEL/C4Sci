package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements.scaledElements;

import c4sci.data.DataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements.ComputedDataElement;
/**
 * This class corresponds to a value that is expressed relatively to unit and is an information given by the application.
 * @author jeanmarc.deniel
 *
 */
public abstract class ScaledComputedDataElement extends ComputedDataElement {

	private UnitScales		unitScales;
	
	public ScaledComputedDataElement(DataParameter data_p, UnitScales units_) {
		super(data_p);
		unitScales = units_;
	}

	@Override
	public UnitScales getUnits() {
		return unitScales;
	}


}
