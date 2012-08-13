package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.internationalization.InternationalizableTerm;

public class TestTotalOrderRelationshipsGraph {

	@Test
	public void test() {
		
		TotalOrderRelationshipsGraph<MaxRefValueInfToMinConstrainedValueConstraint> _graph = 
				new TotalOrderRelationshipsGraph<MaxRefValueInfToMinConstrainedValueConstraint>("TestGraph", 
						new InternationalizableTerm("Test Graph"), 
						new InternationalizableTerm("Test Graph"));
		
		
		try {
			_graph.addRelationShip(new MaxRefValueInfToMinConstrainedValueConstraint(0, 1));
			_graph.addRelationShip(new MaxRefValueInfToMinConstrainedValueConstraint(2, 1));
			_graph.addRelationShip(new MaxRefValueInfToMinConstrainedValueConstraint(0, 2));
			_graph.addRelationShip(new MaxRefValueInfToMinConstrainedValueConstraint(2, 3));
			_graph.addRelationShip(new MaxRefValueInfToMinConstrainedValueConstraint(5, 6));
			assertTrue(true);
		} catch (CyclicGraphNotAllowedException _e) {
			fail("there should not be cycles here");
		}
		
		assertTrue(_graph.wouldCreateACycle(1, 0));
		assertFalse(_graph.wouldCreateACycle(2, 4));
		assertFalse(_graph.wouldCreateACycle(4, 1));
		assertTrue(_graph.wouldCreateACycle(6, 5));
		
		assertTrue(_graph.getRelationship(0, 3) == null);
		assertTrue(_graph.getRelationship(1, 2) == null);
		assertTrue((_graph.getRelationship(2, 1).getConstrainedComponentID() == 1) &&
				(_graph.getRelationship(2, 1).getReferenceComponentID() == 2));
		
		assertTrue(_graph.getRelationship(2, 3) != null);
		
		try {
			_graph.addRelationShip(new MaxRefValueInfToMinConstrainedValueConstraint(1, 0));
			fail("should have raised en exception");
		} catch (CyclicGraphNotAllowedException _e) {
			assertTrue(true);
			assertTrue(_e.getConstrainedComponent() == 0);
			assertTrue(_e.getReferenceComponent() == 1);
		}
		
		MaxRefValueInfToMinConstrainedValueConstraint _rel = new MaxRefValueInfToMinConstrainedValueConstraint(4, 6);
		_rel.setAsFixedConstraint(2.0f);
		assertTrue(_rel.isFixed());
		assertEquals(_rel.getFixedConstraint(), 2.0f, 0.1f);
		_rel.setUnfixed();
		assertFalse(_rel.isFixed());
		
	}

}
