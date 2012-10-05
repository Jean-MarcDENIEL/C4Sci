package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.List;

import c4sci.data.exceptions.DataValueParsingException;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.boundedStepElements.BoundsComparator;
/**
 * This class encapsulates a {@link SingleDataStepElement} that is bounded by two other {@link SingleDataStepElement} of the same type.<br>
 * The {@link BoundedStepElement} creation needs the API user to furnish a comparator.
 * <br>
 * <b>Warning :</b> It is the responsibility of the API user to create a coherent {@link BoundedStepElement}. 
 * @author jeanmarc.deniel
 *
 */
public class BoundedStepElement<C extends SingleDataStepElement> extends SingleDataStepElement {

	private C			lowerBound;
	private C			upperBound;
	private C			boundedElement;	
	
	public C getLowerBound() {
		return lowerBound;
	}

	public C getUpperBound() {
		return upperBound;
	}

	public C getBoundedElement() {
		return boundedElement;
	}

	private BoundsComparator	elementComparator; 
	

	/**
	 * <b>null</b> arguments are not accepted.
	 */
	public BoundedStepElement(C lower_bound, C upper_bound, C bounded_elt, BoundsComparator elt_comp) {
		lowerBound			= lower_bound;
		upperBound			= upper_bound;
		boundedElement		= bounded_elt;
		elementComparator	= elt_comp;
	}

	/**
	 * @return true internal elements are coherent and if lower bound <= bounded value <= upper bound
	 */
	public boolean isInternallyCoherent() {
		if (!lowerBound.isInternallyCoherent() || !upperBound.isInternallyCoherent() || !boundedElement.isInternallyCoherent()){
			return false;
		}
		
		String _lower_bound		= lowerBound.getSingleBinding().getBoundData().getValue();
		String _upper_bound		= upperBound.getSingleBinding().getBoundData().getValue();
		String _bounded_value	= boundedElement.getSingleBinding().getBoundData().getValue();
		
		return 	elementComparator.isLesserOrEqual(_lower_bound, _bounded_value) &&
				elementComparator.isGreaterOrEqual(_upper_bound, _bounded_value);
	}

	/**
	 * Sets the bounded value with the lower bound value
	 */
	public void ensureCoherentInternalState() {
		try {
			boundedElement.getSingleBinding().getBoundData().setValue(lowerBound.getSingleBinding().getBoundData().getValue());
		} catch (DataValueParsingException _e) {
			_e.printStackTrace();
		}
	}

	/*@Override
	public Iterator<StepElement> getSubElementsIterator() {
		return new NoChildIterator<StepElement>();
	}*/

	/**
	 * @return the bounded value {@link #isEditable()} result.
	 */
	public boolean isEditable() {
		return boundedElement.isEditable();
	}

	@Override
	public List<ElementBinding> getBindings() {
		List<ElementBinding> _res = new ArrayList<ElementBinding>();
		//_res.addAll(lowerBound.getBindings());
		//_res.addAll(upperBound.getBindings());
		_res.add(boundedElement.getSingleBinding());
		return _res;
	}

	@Override
	public ElementBinding getSingleBinding() {
		return boundedElement.getSingleBinding();
	}
}