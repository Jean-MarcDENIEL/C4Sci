package c4sci.modelViewPresenterController.viewerPresenterInterface;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import c4sci.NoChildIterator;
import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange.FocusGainChange;

public class TestPerformer {

	class Comp1 extends Component{

		@Override
		Iterator<Component> getChildComponentIterator() {
			return new NoChildIterator<Component>();
		}

		@Override
		void addChildComponent(Component child_comp)
				throws CannotPerformSuchChangeException {
			throw new CannotPerformSuchChangeException();
		}
		
	}
	
	static int 	counterOne = 0;
	static int	counterTwo = 0;
	
	@Test
	public void test() {
		

		
		Component _comp = new Comp1();
		ComponentChangeRunnableFactory _factory = _comp.getChangePerformerFactory();
		assertTrue(_factory != null);
	
	}

}
