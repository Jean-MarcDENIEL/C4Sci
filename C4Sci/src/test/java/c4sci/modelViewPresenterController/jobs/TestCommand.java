package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCommand extends Command{

	@Test
	public void testCommand() {
		Command _cmd_1 = new TestCommand();
		Command _cmd_2 = new TestCommand();
		_cmd_2.setParentCommand(_cmd_1);

		assertTrue(_cmd_2.getParentCommand() == _cmd_1);
		Command _cmd_3 = new TestCommand();
		_cmd_2.setCost(100);
		_cmd_2.setPriority(24);
		_cmd_2.setPreviousCommand(_cmd_1);
		
		Command _cmd_4 = new TestCommand();
		_cmd_2.addFollowingCommand(_cmd_4);
		
		_cmd_2.modifyAsClone(_cmd_3);
		assertTrue(_cmd_2.getCost() == _cmd_3.getCost());
		assertTrue(_cmd_2.getParentCommand() == _cmd_3.getParentCommand());
		assertTrue(_cmd_2.getPreviousCommand() == _cmd_3.getPreviousCommand());
		assertTrue(_cmd_2.getPriority() == _cmd_3.getPriority());
		
		
		
		
	}


	public final int JOB_SETTED_VALUE = 10;
	
	public int actualValue;
	public TestCommand(){
		super(null);
		actualValue = 0;
	}
	
}
