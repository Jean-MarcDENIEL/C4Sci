package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.modelViewPresenterController.jobs.exceptions.NoJobToProcessException;
import c4sci.modelViewPresenterController.jobs.schedulers.HighestCostPriorityFirstJobScheduler;

public class TestHighestCostFirstScheduler {

	class TestCommand extends Command{
		TestCommand(int val_to_test){
		}
		void setTestValue(int t_val){
		}
		@Override
		protected boolean isUndoable() {
			return false;
		}

		
	};
	@Test
	public void testChooseJobToProcess() {
		WaitingJobQueue<TestCommand> _queue = new WaitingJobQueue<TestHighestCostFirstScheduler.TestCommand>();
		_queue.setJobScheduler(new HighestCostPriorityFirstJobScheduler<TestHighestCostFirstScheduler.TestCommand>());
		
		for (int _i=0; _i<10; _i++){
			TestCommand _job = new TestCommand(100-_i);
			_job.setCost(100-_i);
			_job.setPriority(200-_i);
			_queue.appendJobAtLastPosition(_job);
		}
		for (int _i=0; _i<10; _i++){
			try {
				TestCommand _job = _queue.extractAJobToProcess();
				_job.setTestValue((100-_i));
				assertTrue(_job.getCost() == 100-_i);

			} catch (NoJobToProcessException e) {
				fail("there should be enough jobs");
			}
		}
	}

}
