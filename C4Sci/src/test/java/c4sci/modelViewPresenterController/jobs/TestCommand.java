package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCommand extends Command{

	@Test
	public void testDoProcess() {
		doProcess();
		assertEquals(actualValue, 10);
		// ensure to not do the job twice
		actualValue = 0;
		doProcess();
		assertEquals(actualValue, 0);
	}

	@Test
	public void testUndoProcess() {
		doProcess();
		undoProcess();
		assertEquals(actualValue, 0);
		// ensure to not undo the job twice
		actualValue = 2;
		undoProcess();
		assertEquals(actualValue, 2);
	}

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
	@Override
	protected void processJob() {
		previousValue = actualValue;
		actualValue = JOB_SETTED_VALUE;
	}

	@Override
	void unprocessJob() {
		actualValue = previousValue;
		
	}

}
