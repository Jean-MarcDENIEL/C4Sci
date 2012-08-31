package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.computedSingleDataElements;

import c4sci.data.basicDataParameters.StringDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.StringValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;
/**
 * This class corresponds to informations given by the application.  
 * @author jeanmarc.deniel
 *
 */
public class LabelComputedDataElement extends ComputedDataElement {

	public LabelComputedDataElement(StringDataParameter data_p) {
		super(data_p);
	}
	@Override
	public UnitScales getUnits() {
		return null;
	}
	@Override
	protected ElementBinding getSingleBinding() {
		return new StringValueBinding(this, getDataParameter());
	}
}