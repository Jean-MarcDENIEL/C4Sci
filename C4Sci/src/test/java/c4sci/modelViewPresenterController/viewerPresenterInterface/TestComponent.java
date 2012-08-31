package c4sci.modelViewPresenterController.viewerPresenterInterface;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import c4sci.NoChildIterator;
import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;

public class TestComponent {

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
	
	class Comp2 extends Component{

		private List<Component> childComponents;
		
		public Comp2(){
			childComponents = new ArrayList<Component>();
		}
		
		@Override
		Iterator<Component> getChildComponentIterator() {
			return childComponents.iterator();
		}

		@Override
		void addChildComponent(Component child_comp)
				throws CannotPerformSuchChangeException {
			childComponents.add(child_comp);
		}
	}

	@Test
	public void testOrigin() {
		Component comp_1 = new Comp1();
		comp_1.setUpperLeftOrigin(new PlaneVector(4.0f, 1.5f));
		assertTrue(new PlaneVector(1.0f, 1.0f).isEqualTo(comp_1.getUpperLeftOrigin()));

		comp_1.setUpperLeftOrigin(new PlaneVector(-0.5f, -1.0f));
		assertTrue(new PlaneVector(0.0f, 0.0f).isEqualTo(comp_1.getUpperLeftOrigin()));

		comp_1.setUpperLeftOrigin(new PlaneVector(0.35f, 0.0f));
		assertTrue(new PlaneVector(0.35f,  0.0f).isEqualTo(comp_1.getUpperLeftOrigin()));
	}

	@Test
	public void testSize() {
		Component comp_1 = new Comp1();
		comp_1.setSize(new PlaneVector(4.0f, 1.5f));
		assertTrue(new PlaneVector(1.0f, 1.0f).isEqualTo(comp_1.getSize()));

		comp_1.setSize(new PlaneVector(-0.5f, -1.0f));
		assertTrue(new PlaneVector(0.0f, 0.0f).isEqualTo(comp_1.getSize()));

		comp_1.setSize(new PlaneVector(0.35f, 0.0f));
		assertTrue(new PlaneVector(0.35f,  0.0f).isEqualTo(comp_1.getSize()));
	}

	@Test
	public void testIdentity() {
		for (int _i=0; _i<1000; _i++){
			DataIdentity _id = new DataIdentity();

			Component _comp_1 = new Comp1();
			_comp_1.setIdentity(_id);

			DataIdentity _id_2 = new DataIdentity();
			assertTrue(_id.equals(_comp_1.getIdentity()));
			assertFalse(_id_2.equals(_comp_1.getIdentity()));
		}
		
		for (int _i=0; _i<100; _i++){
			Component _comp_1 = new Comp1();
			Component _comp_2 = new Comp1();
			
			assertFalse(_comp_1.getIdentity().equals(_comp_2.getIdentity()));
			assertTrue(_comp_1.getIdentity().equals(_comp_1.getIdentity()));
		}
	}

	@Test
	public void testChildComponentIterator() {
		Component _comp_1 = new Comp1();
		int _nb_child = 0;
		for (int _i=0; _i<10; _i++){
			try{
				_comp_1.addChildComponent(new Comp1());
				fail("should not be there");
			}
			catch(CannotPerformSuchChangeException _e){
				assertTrue(true);
			}
		}
		for (Iterator<Component> _it = _comp_1.getChildComponentIterator(); _it.hasNext(); ){
			_it.next();
			_nb_child ++;
		}
		assertTrue(_nb_child == 0);
		Iterator<Component> _it_tmp = _comp_1.getChildComponentIterator();
		try{
			@SuppressWarnings("unused")
			Component _cmp_tmp = _it_tmp.next();
			fail("should have thrown");
		}
		catch(NoSuchElementException _e){
			assertTrue(true);
		}
		try{
			_it_tmp.remove();
			fail("should have thrown");
		}
		catch(UnsupportedOperationException _e){
			assertTrue(true);
		}
		
		Component _comp_2 = new Comp2();
		for (int _i=0; _i<10; _i++){
			try{
				Component _comp_tmp = new Comp2();
				_comp_2.addChildComponent(_comp_tmp);
				_comp_tmp.setParentComponent(_comp_2);
				assertTrue(true);
			}
			catch(CannotPerformSuchChangeException _e){
				fail("should not fail here");
			}
		}
		_nb_child = 0;
		for (Iterator<Component> _it=_comp_2.getChildComponentIterator(); _it.hasNext();){
			Component _comp_tmp = _it.next();
			assertFalse(_comp_tmp.getChildComponentIterator().getClass() ==  NoChildIterator.class);
			assertTrue(_comp_tmp.getParentComponent() == _comp_2);
			_nb_child++;
		}
		assertTrue(_nb_child == 10);
	}

}
