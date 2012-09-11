package c4sci.modelViewPresenterController.viewerPresenterInterface;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import c4sci.NoChildIterator;
import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.ActivityChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange.FocusGainChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange.FocusLossChange;

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

		class TestChangeRunnable extends ComponentChangeRunnable{

			public TestChangeRunnable(ComponentChange comp_change) {
				super(comp_change);
			}

			@Override
			public void run() {
				assertTrue(getComponentChange() instanceof FocusGainChange);				
			}

			@Override
			public ComponentChangeRunnable clonePrototype(ComponentChange comp_change) {
				return new TestChangeRunnable(comp_change);
			}
		};

		class TestChangeRunnable2 extends ComponentChangeRunnable{

			public TestChangeRunnable2(ComponentChange comp_change) {
				super(comp_change);
			}

			@Override
			public void run() {
				assertTrue(getComponentChange() instanceof ActivityChange);
			}

			@Override
			public ComponentChangeRunnable clonePrototype(
					ComponentChange comp_change) {
				return null;
			}

		}

		_factory.addChangePerformingAbility(FocusGainChange.class, new TestChangeRunnable(null));
		_factory.addChangePerformingAbility(ActivityChange.class, new TestChangeRunnable2(null));

		try {
			ComponentChangeRunnable _runnable = _factory.createChangePerformer(new FocusGainChange(new DataIdentity(), null));
			assertTrue(_runnable.getComponentChange().getComponentIdentity() != null);
			assertTrue(_runnable.getComponentChange().getComponentIdentity() != new FocusGainChange(new DataIdentity(), null).getComponentIdentity());
			_runnable.run();
		} catch (CannotPerformSuchChangeException _e) {
			fail("should not throw here");
		}

		try {
			_factory.createChangePerformer(new FocusLossChange(new DataIdentity(), null));
			fail("should have thrown");
		} catch (CannotPerformSuchChangeException _e) {
			assertTrue(true);
		}
		try{
			_factory.createChangePerformer(new ActivityChange(new DataIdentity(), true, null));
			fail("should have thrown");
		}
		catch (CannotPerformSuchChangeException _e) {
			assertTrue(true);
		}

	}

}
