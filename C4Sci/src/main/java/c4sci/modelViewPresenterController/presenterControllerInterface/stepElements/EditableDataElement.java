package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
/**
 * This class describes a {@link StepElement} that can be user modified.<br>
 * 
 * Sub elements contain the editable {@link StepElement}.
 * 
 * @author jeanmarc.deniel
 *
 */
public class EditableDataElement extends LogicalSingleDataStepElement {

	public EditableDataElement(LogicalSingleDataStepElement data_elt) {
		super(data_elt);
	}
	
	@Override
	public boolean isEditable() {
		return true;
	}
}
