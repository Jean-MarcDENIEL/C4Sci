package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
/**
 * This class describes a {@link StepElement} that can be user modified.<br>
 * 
 * Sub elements contain the editable {@link StepElement}.
 * 
 * @author jeanmarc.deniel
 *
 */
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

	@Override
	public boolean containsProperValue() {
		return false;
	}

	@Override
	public String getProperValue() {
		return null;
	}

	@Override
	public void setProperValue(String str_value) {}

	@Override
	public Iterator<StepElement> getSubElementsIterator() {
		List<StepElement> _list = new ArrayList<StepElement>();
		_list.add(getEditableElement());
		return _list.iterator();
	}

}
