package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

/**
 * This class describes a {@link StepElement} that can be user modified.<br>
 * 
 * Sub elements contain the editable {@link StepElement}.
 * 
 * @author jeanmarc.deniel
 *
 */
public class EditableDataElement extends LogicalSingleDataStepElement {

	public EditableDataElement(SingleDataStepElement data_elt) {
		super(data_elt);
	}
	
	@Override
	public boolean isEditable() {
		return true;
	}
}
