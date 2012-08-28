package c4sci.modelViewPresenterController.viewerPresenterInterface;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange.ChangeID;

public class TestPerformer {

	class Comp1 extends Component{

		@Override
		Iterator<Component> getChildComponentIterator() {
			return new NoChildIterator();
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
		
		int _nb_performers = 0;
		
		class TestRunnable extends ComponentChangeRunnable {

			public TestRunnable(){
				super();
			}
			public TestRunnable(ComponentChange comp_change) {
				super(comp_change);
			}

			@Override
			public void run() {
				counterOne ++;
			}

			@Override
			public ComponentChangeRunnable clonePrototype(ComponentChange comp_change) {
				if (comp_change.getChangeID() != ChangeID.RECEIVED_FOCUS_LOSS)
					return new TestRunnable(comp_change);
				return null;
			}
		}

		for(ComponentChange.ChangeID _chg_id : ComponentChange.ChangeID.values()){
			if (_chg_id != ChangeID.RECEIVED_FOCUS_GAIN){
				_nb_performers ++;
				_factory.addChangePerformingAbility(_chg_id, new TestRunnable());
			}
		}

		class TestCompChange extends ComponentChange{
			public ComponentChange.ChangeID changeID;
			public TestCompChange(DataIdentity comp_id) {
				super(comp_id);
			}

			@Override
			public ChangeID getChangeID() {
				return changeID;
			}
			
		}
		
		for (ComponentChange.ChangeID _chg_id : ComponentChange.ChangeID.values()){
			TestCompChange _comp_chg = new TestCompChange(_comp.getIdentity());
			_comp_chg.changeID = _chg_id;
			try {
				ComponentChangeRunnable _runnable =  _comp.getChangePerformerFactory().createChangePerformer(_comp_chg);
				assertTrue(_runnable.getComponentChange().getComponentIdentity().equals(_comp_chg.getComponentIdentity()));
				assertTrue(_runnable.getComponentChange().getComponentIdentity().equals(_comp.getIdentity()));
				_runnable.run();
			} catch (CannotPerformSuchChangeException _e) {
				assertTrue("should not throw on "+_chg_id,(_chg_id == ChangeID.RECEIVED_FOCUS_GAIN)||(_chg_id == ChangeID.RECEIVED_FOCUS_LOSS));
			}
		}
		assertTrue(counterOne == _nb_performers-1);
	}

}
