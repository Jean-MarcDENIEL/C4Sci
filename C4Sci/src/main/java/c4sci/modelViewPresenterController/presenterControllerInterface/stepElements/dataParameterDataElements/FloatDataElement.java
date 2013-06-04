package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements;

import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.basicModifiables.FloatModifiable;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.FloatValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.DataParameterDataElement;

/**
 * This class encapsulates a float parameter.
 * @author jeanmarc.deniel
 *
 */
public class FloatDataElement extends DataParameterDataElement {
	private	GenericDataParameter<FloatModifiable> floatElement;
	public FloatDataElement(GenericDataParameter<FloatModifiable> data_p) {
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
		floatElement.accesValue().setFloatValue(0.0f);
	}

}