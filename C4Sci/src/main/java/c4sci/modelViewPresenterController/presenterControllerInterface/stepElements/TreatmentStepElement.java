package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.NoChildIterator;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

/**
 * This class corresponds to the case where a treatment is accessible.
 * @author jeanmarc.deniel
 *
 */
public class TreatmentStepElement extends StepElement {

	public TreatmentStepElement() {}

	@Override
	public boolean isInternallyCoherent() {
		return true;
	}

	@Override
	public void ensureCoherentInternalState() {}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public UnitScales getUnits(){
		return null;
	}
	@Override
	public Iterator<StepElement> getSubElementsIterator(){
		return new NoChildIterator<StepElement>();
	}

	@Override
	public List<ElementBinding> getBindings() {
		return new ArrayList<ElementBinding>();
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
}
