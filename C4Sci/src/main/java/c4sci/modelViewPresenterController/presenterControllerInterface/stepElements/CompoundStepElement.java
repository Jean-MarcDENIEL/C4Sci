package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
/**
 * This class instantiates the <b>Composite</b> GoF pattern used by its {@link StepElement} superclass.<br>
 * all its methods are delegated to its sub elements.
 * @author jeanmarc.deniel
 *
 */
public class CompoundStepElement extends StepElement {
	private Map<Integer, StepElement>	subElements;			// elements composing the current element
	public CompoundStepElement() {
		subElements			= new ConcurrentHashMap<Integer, StepElement>();
	}
	public final Iterator<StepElement> getSubElementsIterator(){
		return subElements.values().iterator();
	}
	public final void setSubElement(int sub_elt_ref, StepElement sub_elt){
		subElements.put(Integer.valueOf(sub_elt_ref), sub_elt);
	}
	/**
	 * @param sub_elt_ref The reference key of the sub element.
	 * @return The corresponding sub element or null if there is no one for the argument key.
	 */
	public final StepElement getSubElement(int sub_elt_ref){
		return subElements.get(Integer.valueOf(sub_elt_ref));
	}
	@Override
	/**
	 * @return true if and only if all its sub elements are editable.
	 */
	public boolean isEditable() {
		for (Iterator<StepElement> _it=getSubElementsIterator(); _it.hasNext();){
			if (!_it.next().isEditable()){
				return false;
			}
		}
		return true;
	}
	@Override
	/**
	 * 
	 * @return all its sub elements bindings
	 */
	public List<ElementBinding> getBindings() {
		List<ElementBinding> _res = new ArrayList<ElementBinding>();
		for (Iterator<StepElement> _it=getSubElementsIterator();_it.hasNext();){
			List<ElementBinding> _sub_res = _it.next().getBindings();
			_res.addAll(_sub_res);
		}
		return _res;
	}
	/**
	 * @return true if and only if all its sub elements are internally coherent.
	 */
	public boolean isInternallyCoherent() {
		for (Iterator<StepElement> _it=getSubElementsIterator();_it.hasNext();){
			if (!_it.next().isInternallyCoherent()){
				return false;
			}
		}
		return true;
	}
	/**
	 * Ensure coherent state to all of its sub elements
	 */
	public void ensureCoherentInternalState() {
		for (Iterator<StepElement> _it=getSubElementsIterator();_it.hasNext();){
			_it.next().ensureCoherentInternalState();
		}
	}
}
