package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.data.exceptions.DataValueParsingException;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.boundedStepElements.BoundsComparator;
/**
 * This class encapsulates a {@link LogicalSingleDataStepElement} that is bounded by two other {@link LogicalSingleDataStepElement} of the same type.<br>
 * The {@link BoundedStepElement} creation needs the API user to furnish a comparator.<br>
 * Sub elements are composed of :
 * <ul>
 * <li>the bounded element</li>
 * <li>the upper and lower bounds<li>
 * </ul>
 * <br>
 * <b>Warning :</b> It is the responsibility of the API user to create a coherent {@link BoundedStepElement}. 
 * @author jeanmarc.deniel
 *
 */
public class BoundedStepElement<C extends SingleDataStepElement> extends LogicalSingleDataStepElement {

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
		super(bounded_elt);
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

	public Iterator<StepElement> getSubElementsIterator() {
		List<StepElement> _list = new ArrayList<StepElement>();
		_list.add(getBoundedElement());
		_list.add(getLowerBound());
		_list.add(getUpperBound());
		return _list.iterator();
	}
}