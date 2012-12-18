package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements;

import c4sci.data.basicDataParameters.StringDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.StringValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.DataParameterDataElement;
/**
 * This class corresponds to informations given by the application.  
 * @author jeanmarc.deniel
 *
 */
public class LabelDataElement extends DataParameterDataElement {

	public LabelDataElement(StringDataParameter data_p) {
		super(data_p);
	}
	//@Override
	/*public UnitScales getUnits() {
		return null;
	}*/
	@Override
	public ElementBinding getSingleBinding() {
		return new StringValueBinding(this, getDataParameter());
	}
	@Override
	public boolean isInternallyCoherent() {
		return true;
	}
	@Override
	public void ensureCoherentInternalState() {}
}