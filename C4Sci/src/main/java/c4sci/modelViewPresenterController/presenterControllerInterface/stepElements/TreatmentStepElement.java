package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.Iterator;

import c4sci.NoChildIterator;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

/**
 * This class corresponds to the case where a treatment is accessible.
 * @author jeanmarc.deniel
 *
 */
public abstract class TreatmentStepElement extends StepElement {

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
}
