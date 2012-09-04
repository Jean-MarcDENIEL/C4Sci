package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.editablesingleDataElements;

import c4sci.data.basicDataParameters.BooleanDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.elementBindings.BooleanValueBinding;

public class BooleanEditableDataElement extends EditableDataElement {

	public BooleanEditableDataElement(BooleanDataParameter data_p) {
		super(data_p);
	}

	@Override
	protected ElementBinding getSingleBinding() {
		return new BooleanValueBinding(this, getDataParameter());
	}

	@Override
	public boolean isInternallyCoherent() {
		return true;
	}

	@Override
	public void ensureCoherentInternalState() {}

}
