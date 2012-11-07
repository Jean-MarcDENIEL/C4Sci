package c4sci.modelViewPresenterController.presenter.components;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.modelViewPresenterController.jobs.CannotPerformSuchChangeException;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
/**
 * This class defines a {@link Component} that is able to have children {@link Component Components}.<br>
 * There is no relationship between child components.
 * 
 * @author jeanmarc.deniel
 *
 */
public class CompoundComponent extends Component {

	private List<Component>		childComponents;
	
	public CompoundComponent() {
		childComponents = new ArrayList<Component>();
	}

	@Override
	public Iterator<Component> getChildComponentIterator() {
		return childComponents.iterator();
	}

	@Override
	public void addChildComponent(Component child_comp)
			throws CannotPerformSuchChangeException {
		childComponents.add(child_comp);
	}

}
