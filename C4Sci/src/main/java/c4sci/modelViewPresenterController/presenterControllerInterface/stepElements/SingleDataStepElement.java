package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;

/**
 * This class corresponds to single Data being involved during an ApplicationStep.<br>
 * <br>
 * @author jeanmarc.deniel
 *
 */
public abstract class SingleDataStepElement extends StepElement {
	public SingleDataStepElement() {}
	public abstract ElementBinding getSingleBinding();
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
	public Iterator<StepElement> getSubElementsIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}


}