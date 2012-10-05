package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;

public class EditableDataElement extends SingleDataStepElement {

	private SingleDataStepElement editableElement;
	
	public EditableDataElement(SingleDataStepElement data_elt) {
		editableElement = data_elt;
	}
	
	public final SingleDataStepElement getEditableElement(){
		return editableElement;
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	public ElementBinding getSingleBinding() {
		return editableElement.getSingleBinding();
	}

	@Override
	public boolean isInternallyCoherent() {
		return editableElement.isInternallyCoherent();
	}

	@Override
	public void ensureCoherentInternalState() {
		editableElement.ensureCoherentInternalState();
	}

}
