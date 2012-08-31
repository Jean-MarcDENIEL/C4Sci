package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;
/**
 * This class instantiates the <b>Composite</b> GoF pattern used by its {@link StepElement} superclass.
 * @author jeanmarc.deniel
 *
 */
public abstract class CompoundStepElement extends StepElement {

	private Map<Integer, StepElement>	subElements;			// elements composing the current element
	
	public CompoundStepElement() {
		subElements			= new ConcurrentHashMap<Integer, StepElement>();
	}

	public final Iterator<StepElement> getSubElementsIterator(){
		return subElements.values().iterator();
	}
	
	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ElementBinding> getBindings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitScales getUnits() {
		// TODO Auto-generated method stub
		return null;
	}

}
