package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import c4sci.modelViewPresenterController.jobs.consumption.JobConsumerThread;
import c4sci.modelViewPresenterController.jobs.schedulers.HighestCostPriorityFirstJobScheduler;
import c4sci.modelViewPresenterController.jobs.schedulers.SequentialJobScheduler;

public class TestJobConsumerThread {

	class TestCommandA extends Command{
		public int	addValue;

		public TestCommandA(int int_val, Command parent_cmd){
			super(parent_cmd);
			addValue = int_val;
			setCommandID(1);
		}
	};
	
	class TestCommandB extends Command{
		public int		multValue;

		public TestCommandB(Command parent_cmd){
			super(parent_cmd);
			setCommandID(2);
		}
	}
	
	/*
	 * * Adds 2 to the CommandA value and put it into the CommandB result.
	 */
	class AdderJobProcessor  extends JobProcessor<TestCommandA, TestCommandB>{
		@Override
		public List<TestCommandB> processJob(TestCommandA processing_cmd) {
			List<TestCommandB> _res = new ArrayList<TestCommandB>();
			TestCommandB _res_cmd = new TestCommandB(processing_cmd);
			_res_cmd.multValue = processing_cmd.addValue + 2;
			_res.add(_res_cmd);
			return _res;
		}
	}
	
	/*
	 * Multiplies the CommandB value by 2 and adds it to _atom_res.
	 */
	class MulJobProcessor extends JobProcessor<TestCommandB, TestCommandB>{
		@Override
		public List<TestCommandB> processJob(TestCommandB processing_cmd) {
			List<TestCommandB> _res = new ArrayList<TestCommandB>();
			TestCommandB _res_cmd = new TestCommandB(processing_cmd);
			_res.add(_res_cmd);
			_res_cmd.multValue = processing_cmd.multValue * 2;
			return _res;
		}
	}
	
	class TransJobProcessor extends JobProcessor<TestCommandB, TestCommandA>{
		@Override
		public List<TestCommandA> processJob(TestCommandB processing_cmd) {
			if (processing_cmd.multValue < 10){
				// tests that balancing by null result pushing works
				_atom_res.addAndGet(processing_cmd.multValue);
				System.out.println("     TransJobConsumer - process : "+processing_cmd.multValue +"-> null");
				return null;
			}
			System.out.println("     TransJobConsumer - process : "+processing_cmd.multValue);
			List<TestCommandA> _res = new ArrayList<TestCommandA>();
			TestCommandA _res_cmd = new TestCommandA(processing_cmd.multValue, processing_cmd);
			_res.add(_res_cmd);
			return _res;
		}
	}
	
	class FinishJobProcessor extends JobProcessor<TestCommandA, TestCommandA>{

		@Override
		public List<TestCommandA> processJob(TestCommandA processing_cmd) {
			_atom_res.addAndGet(processing_cmd.addValue);
			System.out.println("              FinishJobConsumer - process : "+processing_cmd.addValue);
			return null;
		}
		
	}
	
	class AddingJobConsumer extends JobConsumerThread<TestCommandA, TestCommandB>{

		public AddingJobConsumer(
				RequestResultInterface<TestCommandA> req_queue,
				RequestResultInterface<TestCommandB> res_queue) {
			super(req_queue, res_queue);
			associateFlagToProcessor(1, new InternalAdderJobProcessor());
		}

		class InternalAdderJobProcessor extends AdderJobProcessor{
			public List<TestCommandB> processJob(TestCommandA processing_cmd){
				// tests the fact of not creating child commands
				if (processing_cmd.addValue < 0){
					return null;
				}
				else{
					System.out.println("AddingJobConsumer - process : "+processing_cmd.addValue);
					return super.processJob(processing_cmd);
				}
			}
		};
		public List<TestCommandB> processJob(TestCommandA job_req) {
			try {
				Thread.sleep((long) (Math.random()*10.0));
			} 
			catch (InterruptedException _e) {
			}
			return super.processJob(job_req);
		}
		public TestCommandA pullJobToProcess() {
			return pullRequestJobToProcess();
		}
		public void pushProcessedJob(List<TestCommandB> job_res) {
			pushJobResultAsRequest(job_res);
		}
	};
	
	static AtomicInteger _atom_res = new AtomicInteger(0);	
	
	@Test
	public void test() {
		RequestResultInterface<TestCommandA> _add_RRI = new RequestResultInterface<TestJobConsumerThread.TestCommandA>();
		RequestResultInterface<TestCommandB> _mul_RRI = new RequestResultInterface<TestJobConsumerThread.TestCommandB>();
		
		_add_RRI.setRequestQueueScheduler(new SequentialJobScheduler<TestJobConsumerThread.TestCommandA>());
		_add_RRI.setResultQueueScheduler(new HighestCostPriorityFirstJobScheduler<TestJobConsumerThread.TestCommandA>());
		
		_mul_RRI.setRequestQueueScheduler(new HighestCostPriorityFirstJobScheduler<TestJobConsumerThread.TestCommandB>());
		_mul_RRI.setResultQueueScheduler(new SequentialJobScheduler<TestJobConsumerThread.TestCommandB>());
		
		AddingJobConsumer _adding_thread = new AddingJobConsumer(_add_RRI, _mul_RRI);


		JobConsumerThread<TestCommandB, TestCommandB> _mul_thread = new
				JobConsumerThread<TestCommandB, TestCommandB>( _mul_RRI, _mul_RRI) {
			public List<TestCommandB> processJob(TestCommandB job_req) {
				try {
					Thread.sleep((long) (Math.random()*10.0));
				} 
				catch (InterruptedException _e) {
				}
				System.out.println("  _mult_thread - process : "+job_req.multValue);
				return super.processJob(job_req);
			}
			public TestCommandB pullJobToProcess() {
				return pullRequestJobToProcess();
			}
			public void pushProcessedJob(List<TestCommandB> job_res) {
				pushJobResultAsResult(job_res);
			}
		};
		_mul_thread.associateFlagToProcessor(2, new MulJobProcessor());

		JobConsumerThread<TestCommandB, TestCommandA> _trans_thread = new
				JobConsumerThread<TestCommandB, TestCommandA>(_mul_RRI, _add_RRI) {
			public List<TestCommandA> processJob(TestCommandB job_req) {
				try {
					Thread.sleep((long) (Math.random()*10.0));
				} 
				catch (InterruptedException _e) {
				}
				return super.processJob(job_req);
			}
			public TestCommandB pullJobToProcess() {
				return pullResultJobToProcess();
			}
			public void pushProcessedJob(List<TestCommandA> job_res) {
				pushJobResultAsResult(job_res);
			}
		};
		_trans_thread.associateFlagToProcessor(2, new TransJobProcessor());

		JobConsumerThread<TestCommandA, TestCommandA> _result_thread = new
				JobConsumerThread<TestCommandA, TestCommandA>(_add_RRI, _add_RRI){
			public TestCommandA pullJobToProcess() {
				return pullResultJobToProcess();
			}
			public void pushProcessedJob(List<TestCommandA> job_res) {
				// tests that pushing null request has no effect
				pushJobResultAsRequest(job_res);
			}
		};
		_result_thread.associateFlagToProcessor(1, new FinishJobProcessor());
		
		int _sum = 0;
		for (int _i=-10; _i<3; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i, null));
			if (_i >=0){
				_sum += (_i+2)*2;
			}
		}
		
		_result_thread.start();
		_mul_thread.start();
		_trans_thread.start();
		_adding_thread.start();
		
		// ensure basic jobs work 
		_add_RRI.waitUntilBalanced();
		System.out.println("Basic jobs finish.");
		assertTrue("basic thread job does not work : "+_atom_res.get()+"instead of "+_sum, _sum == _atom_res.get());	

		//if (true)return;
		
		// ensure feeding with alive threads works
		_sum = 0;
		_atom_res.set(0);
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i, null));
			if (_i >=0){
				_sum += (_i+2)*2;
			}
		}
		_add_RRI.waitUntilBalanced();
		System.out.println("Feeding with alive thread finished.");
		assertTrue("feeding with alive thread does not work : "+_atom_res.get()+" instead of "+_sum, _sum == _atom_res.get());	
		//System.out.println("feeding with alive threads works");
		
		// ensure closing with alive threads works
		_add_RRI.closeForRequests();
		_sum = 0;
		_atom_res.set(0);
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i, null));
			if (_i >=0){
				_sum += (_i+2)*2;
			}
		}
		_add_RRI.waitUntilBalanced();
		System.out.println("closing with alive thread finished.");
		assertTrue("closing with alive threads does not work : "+_atom_res.get()+" instead of " + 0, 0 == _atom_res.get());	


		// ensure reopening with alive threads works
		_add_RRI.openForRequests();
		_sum = 0;
		_atom_res.set(0);
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i, null));
			if (_i >=0){
				_sum += (_i+2)*2;
			}
		}
		_add_RRI.waitUntilBalanced();
		System.out.println("reopening with alive threads finished");
		assertTrue("reopeing with alive threads does not work : "+_atom_res.get()+" instead of "+_sum,_sum == _atom_res.get());	
		
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
		_atom_res.set(0);
		for (int _i=0; _i<10; _i++){
			_add_RRI.pushRequest(new TestCommandA(_i, null));
			_sum += (_i+2)*2;
		}
		System.out.println("Ensuring all threads are dead finished.");
		assertTrue("All threads dead : "+_atom_res.get()+" instead of 0", 0 == _atom_res.get());	

	}

}
