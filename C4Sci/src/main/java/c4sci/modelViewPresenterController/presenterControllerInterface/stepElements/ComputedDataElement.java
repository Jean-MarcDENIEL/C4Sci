package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

/**
 * This class corresponds to the case of data that have been computed and are not editable.<br>
 * Sub elements contain the computed {@link StepElement}.
 * 
 * @author jeanmarc.deniel
 *
 */
public class ComputedDataElement extends LogicalSingleDataStepElement {
	

	public ComputedDataElement(LogicalSingleDataStepElement data_elt) {
		super(data_elt);
	}
	@Override
	public boolean isEditable() {
		return false;
	}

}
