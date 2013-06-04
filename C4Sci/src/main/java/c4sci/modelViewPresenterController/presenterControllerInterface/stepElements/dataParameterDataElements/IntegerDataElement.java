package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements;

import c4sci.data.basicDataParameters.IntegerDataParameter;
import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.basicModifiables.IntegerModifiable;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.IntegerValueBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.DataParameterDataElement;
/**
 * This class encapsulates an integer parameter.<br>
 * 
 * @author jeanmarc.deniel
 *
 */
public class IntegerDataElement extends DataParameterDataElement {

	private GenericDataParameter<IntegerModifiable> integerElement;
	
	public IntegerDataElement(GenericDataParameter<IntegerModifiable> data_p) {
		super(data_p);
		integerElement = data_p;
	}

	@Override
	public ElementBinding getSingleBinding() {
		return new IntegerValueBinding(this, getDataParameter());
	}

	@Override
	public boolean isInternallyCoherent() {
		return true;
	}

	@Override
	/**
	 * Sets to 0
	 */
	public void ensureCoherentInternalState() {
		integerElement.accesValue().setIntegerValue(0);
	}

}
