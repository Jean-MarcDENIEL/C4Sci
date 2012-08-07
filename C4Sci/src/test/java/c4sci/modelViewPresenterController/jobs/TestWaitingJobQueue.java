package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import c4sci.modelViewPresenterController.jobs.exceptions.NoJobToProcessException;
import c4sci.modelViewPresenterController.jobs.schedulers.HighestCostPriorityFirstJobScheduler;
import c4sci.modelViewPresenterController.jobs.schedulers.SequentialJobScheduler;

public class TestWaitingJobQueue {

	class TestCommand extends Command{
		private int testValue;
		private int attendedValue;
		TestCommand(int val_to_test){
			attendedValue = val_to_test;
		}
		void setTestValue(int t_val){
			testValue = t_val;
		}
		@Override
		protected boolean isUndoable() {
			return false;
		}
		@Override
		protected void processJob() {
			assertTrue(attendedValue == testValue);
		}
		@Override
		void unprocessJob() {}
		
	};
	
	
	@Test
	public void testAppendJobAtLastPosition() {
		WaitingJobQueue<TestCommand> _job_queue = new WaitingJobQueue<TestCommand>();
		_job_queue.setJobScheduler(new SequentialJobScheduler<TestWaitingJobQueue.TestCommand>());
		
		for (int _i=0; _i<10; _i++){
			_job_queue.appendJobAtLastPosition(new TestCommand(_i));
		}
		for (int _i=0; _i<10; _i++){
			TestCommand _job;
			try {
				_job = _job_queue.extractAJobToProcess();
				_job.setTestValue(_i);
				_job.processJob();
			} catch (NoJobToProcessException e) {
				fail("there should be enough jobs here");
			}
		}
		try {
			_job_queue.extractAJobToProcess();
			fail("should have rised an exception");
		} catch (NoJobToProcessException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testGetJobIterator(){
		WaitingJobQueue<TestCommand> _queue = new WaitingJobQueue<TestCommand>();
		_queue.appendJobAtLastPosition(new TestCommand(10));
		Iterator<TestCommand> _it = _queue.getNoUnprocessedAncestorJobIterator();
		if (_it.hasNext()){
			TestCommand _job = _it.next();
			_job.setCost(20);
			assertTrue(_job.getCost() == 20);
			_job.setCost(-1);
			assertTrue(_job.getCost() == 0);
			_job.setPriority(-12);
			assertTrue(_job.getPriority() == 0);
			_job.setPriority(5);
			assertTrue(_job.getPriority() == 5);
		}
		else{
			fail();
		}
		assertFalse(_queue.isEmpty());
		
	}
	@Test
	public void testAppendExtract(){
		WaitingJobQueue<TestCommand> _queue = new WaitingJobQueue<TestWaitingJobQueue.TestCommand>();
		_queue.setJobScheduler(new HighestCostPriorityFirstJobScheduler<TestWaitingJobQueue.TestCommand>());
		
		TestCommand _job_1 = new TestCommand(1);
		_job_1.setPriority(1);
		_job_1.setCost(1);
		
		TestCommand _job_20 = new TestCommand(20);
		_job_20.setPriority(20);
		_job_20.setCost(20);
		
		TestCommand _job_5 = new TestCommand(5);
		_job_5.setPriority(5);
		_job_5.setCost(5);
		
		TestCommand _job_30 = new TestCommand(30);
		_job_30.setPriority(30);
		_job_30.setCost(30);
		
		_queue.appendJobAtLastPosition(_job_1);
		_queue.appendJobAtLastPosition(_job_20);
		_queue.appendJobAtLastPosition(_job_5);
		_queue.appendJobAtLastPosition(_job_30);
		
		try {
			TestCommand _test_job = _queue.extractAJobToProcess();
			_test_job.setTestValue(30);
			_test_job.processJob();
			
			_test_job = _queue.extractAJobToProcess();
			_test_job.setTestValue(20);
			_test_job.processJob();
			
			_test_job = _queue.extractAJobToProcess();
			_test_job.setTestValue(5);
			_test_job.processJob();
			
			_test_job = _queue.extractAJobToProcess();
			_test_job.setTestValue(1);
			_test_job.processJob();
		} catch (NoJobToProcessException e) {
			fail("enough jobs here");
		}
		

	}

}
