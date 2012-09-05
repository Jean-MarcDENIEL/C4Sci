package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements;

import c4sci.data.basicDataParameters.IntegerDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.IntegerValueBinding;
/**
 * This class encapsulates an integer parameter.<br>
 * 
 * @author jeanmarc.deniel
 *
 */
public class IntegerDataElement extends DataParameterDataElement {

	private IntegerDataParameter integerElement;
	
	public IntegerDataElement(IntegerDataParameter data_p) {
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
		integerElement.setIntegerValue(0);
	}

	@Override
	/**
	 * As a default behavior it is editable.
	 */
	public boolean isEditable() {
		return true;
	}
}
