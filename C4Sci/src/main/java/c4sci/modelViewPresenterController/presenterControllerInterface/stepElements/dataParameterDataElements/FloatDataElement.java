package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements;

import c4sci.data.basicDataParameters.FloatDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.FloatValueBinding;

/**
 * This class encapsulates a float parameter.
 * @author jeanmarc.deniel
 *
 */
public class FloatDataElement extends DataParameterDataElement {

	private	FloatDataParameter floatElement;
	
	public FloatDataElement(FloatDataParameter data_p) {
		super(data_p);
		floatElement = data_p;
	}

	@Override
	public ElementBinding getSingleBinding() {
		return new FloatValueBinding(this, getDataParameter());
	}

	@Override
	public boolean isInternallyCoherent() {
		return true;
	}

	@Override
	/**
	 * Sets to 0.0.
	 */
	public void ensureCoherentInternalState() {
		floatElement.setFloatValue(0.0f);
		
	}

	@Override
	/**
	 * As a basic behavior it is editable.
	 */
	public boolean isEditable() {
		return true;
	}

}
