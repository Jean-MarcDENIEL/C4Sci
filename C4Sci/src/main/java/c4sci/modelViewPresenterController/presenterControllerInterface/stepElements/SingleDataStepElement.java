package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.NoChildIterator;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;

/**
 * This class corresponds to single Data being involved during an ApplicationStep.<br>
 * <br>
 * @author jeanmarc.deniel
 *
 */
public abstract class SingleDataStepElement extends StepElement {

	public SingleDataStepElement() {

	}

	
	public abstract ElementBinding getSingleBinding();
	@Override
	/**
	 * <b>Pattern</b> This class uses the <b>Factory method</b> GoF pattern by calling {@link #getSingleBinding()} sub classes method.
	 */
	public List<ElementBinding> getBindings() {
		List<ElementBinding> _res = new ArrayList<ElementBinding>();
		_res.add(getSingleBinding());
		return _res;
	}
	@Override
	public Iterator<StepElement> getSubElementsIterator(){
		return new NoChildIterator<StepElement>();
	}
}