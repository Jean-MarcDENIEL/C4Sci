package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;


/**
 * This class corresponds to the case of data that have been computed and are not editable.<br>
 * Sub elements contain the computed {@link StepElement}.
 * 
 * @author jeanmarc.deniel
 *
 */
public class ComputedDataElement extends SingleDataStepElement {
	
	private SingleDataStepElement computedElement;
	
	public ComputedDataElement(SingleDataStepElement data_elt) {
		computedElement = data_elt;
	}
	@Override
	public boolean isEditable() {
		return false;
	}
	@Override
	public boolean isInternallyCoherent(){
		return computedElement.isInternallyCoherent();
	}
	
	@Override
	public void ensureCoherentInternalState() {
		computedElement.ensureCoherentInternalState();
	}
	@Override
	public ElementBinding getSingleBinding() {
		return computedElement.getSingleBinding();
	}

	public final SingleDataStepElement getComputedElement(){
		return computedElement;
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
		_list.add(getComputedElement());
		return _list.iterator();
	}
}
