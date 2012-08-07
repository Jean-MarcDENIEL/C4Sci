package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import c4sci.modelViewPresenterController.jobs.consumption.JobConsumerThread;
import c4sci.modelViewPresenterController.jobs.schedulers.HighestCostPriorityFirstJobScheduler;
import c4sci.modelViewPresenterController.jobs.schedulers.SequentialJobScheduler;

public class TestJobConsumerThread {

	class TestCommandA extends Command{

		public int	addValue;
		@Override
		protected boolean isUndoable() {
			return false;
		}

		@Override
		protected void processJob() {
			addValue += 2;
		}

		@Override
		void unprocessJob() {
		}
		public TestCommandA(int val_){
			addValue = val_;
		}
	};
	
	class TestCommandB extends Command{

		public int		multValue;
		@Override
		protected boolean isUndoable() {
			return false;
		}

		@Override
		protected void processJob() {
			multValue *= 2;
		}

		@Override
		void unprocessJob() {
		}		
		public TestCommandB(int mult_val){
			multValue = mult_val;
		}
	}
	static AtomicInteger _atom_res = new AtomicInteger(0);	
	@Test
	public void test() {
		RequestResultInterface<TestCommandA> _add_RRI = new RequestResultInterface<TestJobConsumerThread.TestCommandA>();
		RequestResultInterface<TestCommandB> _mul_RRI = new RequestResultInterface<TestJobConsumerThread.TestCommandB>();
		
		_add_RRI.setRequestQueueScheduler(new SequentialJobScheduler<TestJobConsumerThread.TestCommandA>());
		_add_RRI.setResultQueueScheduler(new HighestCostPriorityFirstJobScheduler<TestJobConsumerThread.TestCommandA>());
		
		_mul_RRI.setRequestQueueScheduler(new HighestCostPriorityFirstJobScheduler<TestJobConsumerThread.TestCommandB>());
		_mul_RRI.setResultQueueScheduler(new SequentialJobScheduler<TestJobConsumerThread.TestCommandB>());
		
		int _sum = 0;
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i));
			_sum += (_i+2)*2;
		}
		


		JobConsumerThread<TestCommandA, TestCommandB> _adding_thread = new 
				JobConsumerThread<TestCommandA, TestCommandB>(_add_RRI, _mul_RRI){
			public TestCommandB processJob(TestCommandA job_req) {
				job_req.processJob();
				try {
					Thread.sleep((long) (Math.random()*10.0));
				} 
				catch (InterruptedException _e) {
				}
				return new TestCommandB(job_req.addValue);
			}
			public TestCommandA pullJobToProcess() {
				return pullRequestJobToProcess();
			}
			public void pushProcessedJob(TestCommandB job_res) {
				pushJobResultAsRequest(job_res);
			}
		};

		JobConsumerThread<TestCommandB, TestCommandB> _mul_thread = new
				JobConsumerThread<TestCommandB, TestCommandB>( _mul_RRI, _mul_RRI) {
			public TestCommandB processJob(TestCommandB job_req) {
				job_req.processJob();
				try {
					Thread.sleep((long) (Math.random()*10.0));
				} 
				catch (InterruptedException _e) {
				}
				return new TestCommandB(job_req.multValue);
			}
			public TestCommandB pullJobToProcess() {
				return pullRequestJobToProcess();
			}
			public void pushProcessedJob(TestCommandB job_res) {
				pushJobResultAsResult(job_res);
			}
		};

		JobConsumerThread<TestCommandB, TestCommandA> _trans_thread = new
				JobConsumerThread<TestCommandB, TestCommandA>(_mul_RRI, _add_RRI) {
			public TestCommandA processJob(TestCommandB job_req) {
				try {
					Thread.sleep((long) (Math.random()*10.0));
				} 
				catch (InterruptedException _e) {
				}
				return new TestCommandA(job_req.multValue);
			}
			public TestCommandB pullJobToProcess() {
				return pullResultJobToProcess();
			}
			public void pushProcessedJob(TestCommandA job_res) {
				pushJobResultAsResult(job_res);
			}
		};

		JobConsumerThread<TestCommandA, TestCommandA> _result_thread = new
				JobConsumerThread<TestCommandA, TestCommandA>(_add_RRI, _add_RRI){
			public TestCommandA processJob(TestCommandA job_req) {
				_atom_res.addAndGet(job_req.addValue);
				return null;
			}
			public TestCommandA pullJobToProcess() {
				return pullResultJobToProcess();
			}
			public void pushProcessedJob(TestCommandA job_res) {
				if (job_res.addValue < 10){
					pushJobResultAsRequest(job_res);
				}
				else{
					// does nothing
				}
			}
		};

		_result_thread.start();
		_mul_thread.start();
		_trans_thread.start();
		_adding_thread.start();
		
		// ensure basic jobs work 
		_add_RRI.waitUntilBalanced();
		assertTrue(_sum == _atom_res.get());	
		
		_sum = 0;
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i));
			_sum += (_i+2)*2;
		}
		_add_RRI.waitUntilBalanced();
		assertTrue(_sum*2 == _atom_res.get());	
		
		// ensure closing with alive threads works
		_add_RRI.closeForRequests();
		_sum = 0;
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i));
			_sum += (_i+2)*2;
		}
		_add_RRI.waitUntilBalanced();
		assertTrue(_sum*2 == _atom_res.get());	

		// ensure reopening with alive threads works
		_add_RRI.openForRequests();
		_sum = 0;
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i));
			_sum += (_i+2)*2;
		}
		_add_RRI.waitUntilBalanced();
		assertTrue(_sum*3 == _atom_res.get());	
		
		
		_result_thread.setToDieUnused();
		_mul_thread.setToDieUnused();
		_trans_thread.setToDieUnused();
		_adding_thread.setToDieUnused();
		
		try {
			Thread.sleep(10);
		}
		catch (InterruptedException _e) {
		}
		
		// ensure all threads are dead
		_sum = 0;
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i));
			_sum += (_i+2)*2;
		}
		assertTrue(_sum*3 == _atom_res.get());	


	}

}
