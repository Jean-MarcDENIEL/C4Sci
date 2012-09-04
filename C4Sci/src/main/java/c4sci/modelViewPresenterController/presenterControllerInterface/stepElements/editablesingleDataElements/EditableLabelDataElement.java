package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.editablesingleDataElements;

import c4sci.data.basicDataParameters.StringDataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;

public class EditableLabelDataElement extends EditableDataElement {

	public EditableLabelDataElement(StringDataParameter data_p) {
		super(data_p);
	}

	@Override
	protected ElementBinding getSingleBinding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInternallyCoherent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void ensureCoherentInternalState() {
		// TODO Auto-generated method stub

	}

}
