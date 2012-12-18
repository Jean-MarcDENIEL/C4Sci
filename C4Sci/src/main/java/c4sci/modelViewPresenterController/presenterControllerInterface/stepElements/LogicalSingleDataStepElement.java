package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

/**
 * This class corresponds to the composition of a single Data being involved during an ApplicationStep.<br>
 * This way services are implemented through composition.<br>
 *<br> 
 * All methods refer to the {@link #getSingleElement()} method's result.<br>
 * To achieve certain behavior it is needed to override corresponding methods.<br>
 * <br>
 * @author jeanmarc.deniel
 *
 */
public class LogicalSingleDataStepElement extends SingleDataStepElement {
	
	private SingleDataStepElement	singleElement;
	
	public SingleDataStepElement getSingleElement() {
		return singleElement;
	}
	public final void setSingleElement(SingleDataStepElement single_element) {
		this.singleElement = single_element;
	}
	
	public LogicalSingleDataStepElement(SingleDataStepElement single_element) {
		setSingleElement(single_element);
	}
	
	public ElementBinding getSingleBinding(){
		return singleElement.getSingleBinding();
	}
	
	@Override
	/**
	 * <b>Pattern : </b> This class implements the <b>Factory method</b> GoF pattern by calling {@link #getSingleBinding()} sub classes method.
	 */
	public List<ElementBinding> getBindings() {
		List<ElementBinding> _res = new ArrayList<ElementBinding>();
		_res.add(getSingleBinding());
		return _res;
	}
	@Override
	public boolean isInternallyCoherent() {
		return getSingleElement().isInternallyCoherent();
	}
	@Override
	public void ensureCoherentInternalState() {
		getSingleElement().ensureCoherentInternalState();
		
	}
	@Override
	public String getProperValue() {
		return getSingleElement().getProperValue();
	}
	@Override
	public void setProperValue(String str_value) {
		getSingleElement().setProperValue(str_value);
	}
	@Override
	public Iterator<StepElement> getSubElementsIterator() {
		return getSingleElement().getSubElementsIterator();
	}
	@Override
	public boolean isEditable() {
		return getSingleElement().isEditable();
	}
	@Override
	public UnitScales getUnits() {
		return getSingleElement().getUnits();
	}

	
	
}