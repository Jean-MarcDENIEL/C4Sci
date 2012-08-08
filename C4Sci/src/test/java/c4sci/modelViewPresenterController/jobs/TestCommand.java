package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCommand extends Command{

	@Test
	public void testIsUndoable() {
		assertTrue(isUndoable());
	}

	@Override
	protected boolean isUndoable() {
		return true;
	}
	/********************************************************/
	
	private int previousValue;
	public final int JOB_SETTED_VALUE = 10;
	
	public int actualValue;
	public TestCommand(){
		previousValue = 0;
		actualValue = 0;
	}
	
}
