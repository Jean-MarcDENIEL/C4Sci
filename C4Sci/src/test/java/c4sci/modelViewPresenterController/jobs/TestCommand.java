package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCommand extends Command{

	@Test
	public void testIsUndoable() {
		assertTrue(isUndoable());
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
	/********************************************************/
	

	public final int JOB_SETTED_VALUE = 10;
	
	public int actualValue;
	public TestCommand(){
		super(null);
		actualValue = 0;
	}
	
}
