package c4sci.modelViewPresenterController.presenter.components;

import java.util.Iterator;

import c4sci.NoChildIterator;
import c4sci.modelViewPresenterController.jobs.exceptions.CannotPerformSuchChangeException;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

/**
 * This class defines components that have no child
 * @author jeanmarc.deniel
 *
 */

public class NoChildComponent extends Component {

	public NoChildComponent() {
	}

	@Override
	public Iterator<Component> getChildComponentIterator() {
		return new NoChildIterator<Component>();
	}

	@Override
	public void addChildComponent(Component child_comp)
			throws CannotPerformSuchChangeException {
		throw new CannotPerformSuchChangeException("Child add not permitted");
	}

}
