package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.NoChildIterator;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.math.algebra.Floatings;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
/**
 * This class encapsulates a {@link SingleDataStepElement} that is bounded by two other {@link SingleDataStepElement} of the same type.<br>
 * The {@link BoundedStepElement} creation needs the API user to furnish a comparator.
 * <br>
 * <b>Warning :</b> It is the responsibility of the API user to create a coherent {@link BoundedStepElement}. 
 * @author jeanmarc.deniel
 *
 */
public class BoundedStepElement<C extends SingleDataStepElement> extends StepElement {

	private C			lowerBound;
	private C			upperBound;
	private C			boundedElement;	
	private Comparator	elementComparator; 
	
	public abstract class Comparator{
		public abstract boolean isGreaterOrEqual(String val_1, String val_2);
		public abstract boolean isLesserOrEqual(String val_1, String val_2);
	}
	
	public class FloatComparator extends Comparator{

		@Override
		public boolean isGreaterOrEqual(String val_1, String val_2) {
			try{
				float _fl_1 = Float.valueOf(val_1);
				float _fl_2 = Float.valueOf(val_2);
				return Floatings.isGreaterEqual(_fl_1, _fl_2);
			}
			catch(NumberFormatException _e){
				return false;
			}
		}

		@Override
		public boolean isLesserOrEqual(String val_1, String val_2) {
			try{
				float _fl_1 = Float.valueOf(val_1);
				float _fl_2 = Float.valueOf(val_2);
				return Floatings.isLessEqual(_fl_1, _fl_2);
			}
			catch(NumberFormatException _e){
				return false;
			}
		}
	}
	
	public class IntegerComparator extends Comparator{

		@Override
		public boolean isGreaterOrEqual(String val_1, String val_2) {
			try{
				int _int_1 = Integer.valueOf(val_1);
				int _int_2 = Integer.valueOf(val_2);
				return _int_1 >= _int_2;
			}
			catch(NumberFormatException _e){
				return false;
			}
		}

		@Override
		public boolean isLesserOrEqual(String val_1, String val_2) {
			try{
				int _int_1 = Integer.valueOf(val_1);
				int _int_2 = Integer.valueOf(val_2);
				return _int_1 <= _int_2;
			}
			catch(NumberFormatException _e){
				return false;
			}
		}
	}
	/**
	 * <b>null</b> arguments are not accepted.
	 */
	public BoundedStepElement(C lower_bound, C upper_bound, C bounded_elt, Comparator elt_comp) {
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
		
		String lower_bound		= lowerBound.getSingleBinding().getBoundParameter().getParameterValue();
		String upper_bound		= upperBound.getSingleBinding().getBoundParameter().getParameterValue();
		String bounded_value	= boundedElement.getSingleBinding().getBoundParameter().getParameterValue();
		
		return 	elementComparator.isLesserOrEqual(lower_bound, bounded_value) &&
				elementComparator.isGreaterOrEqual(upper_bound, bounded_value);
	}

	/**
	 * Sets the bounded value with the lower bound value
	 */
	public void ensureCoherentInternalState() {
		try {
			boundedElement.getSingleBinding().getBoundParameter().setParameterValue(lowerBound.getSingleBinding().getBoundParameter().getParameterValue());
		} catch (DataValueParsingException _e) {
			_e.printStackTrace();
		}
	}

	@Override
	public Iterator<StepElement> getSubElementsIterator() {
		return new NoChildIterator<StepElement>();
	}

	/**
	 * @return the bounded value {@link #isEditable()} result.
	 */
	public boolean isEditable() {
		return boundedElement.isEditable();
	}

	@Override
	public List<ElementBinding> getBindings() {
		List<ElementBinding> _res = new ArrayList<ElementBinding>();
		_res.addAll(lowerBound.getBindings());
		_res.addAll(upperBound.getBindings());
		_res.addAll(boundedElement.getBindings());
		return _res;
	}
}