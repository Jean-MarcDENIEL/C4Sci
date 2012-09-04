package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.editablesingleDataElements;

import c4sci.data.DataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.SingleDataStepElement;

public abstract class EditableDataElement extends SingleDataStepElement {

	public EditableDataElement(DataParameter data_p) {
		super(data_p);
	}

	@Override
	public boolean isEditable() {
		return true;
	}

}
