package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import c4sci.modelViewPresenterController.jobs.schedulers.HighestCostPriorityFirstJobScheduler;
import c4sci.modelViewPresenterController.jobs.schedulers.SequentialJobScheduler;

public class TestJobConsumerThread {

	static final boolean	printTraces = false;

	class TestCommandA extends Command{
		public int	addValue;

		public TestCommandA(int int_val, Command parent_cmd){
			super(parent_cmd);
			addValue = int_val;
		}
	};

	class TestCommandB extends Command{
		public int		multValue;

		public TestCommandB(Command parent_cmd){
			super(parent_cmd);
		}
	}

	/*
	 * * Adds 2 to the CommandA value and put it into the CommandB result.
	 */
	class AdderJobProcessor extends JobProcessor<TestCommandA, TestCommandB>{
		@Override
		public List<TestCommandB> processJob(TestCommandA processing_cmd) {
			List<TestCommandB> _res = new ArrayList<TestCommandB>();
			TestCommandB _res_cmd = new TestCommandB(processing_cmd);
			_res_cmd.multValue = processing_cmd.addValue + 2;
			_res.add(_res_cmd);
			return _res;
		}

		@Override
		public JobProcessor<TestCommandA, TestCommandB> getClone() {
			return new AdderJobProcessor();
		}
	}

	/*
	 * Multiplies the CommandB value by 2 through two child Command.
	 */
	class MulJobProcessor extends JobProcessor<TestCommandB, TestCommandB>{
		@Override
		public List<TestCommandB> processJob(TestCommandB processing_cmd) {
			List<TestCommandB> _res = new ArrayList<TestCommandB>();
			TestCommandB _res_cmd_1 = new TestCommandB(processing_cmd);
			_res.add(_res_cmd_1);
			_res_cmd_1.multValue = processing_cmd.multValue;
			TestCommandB _res_cmd_2 = new TestCommandB(processing_cmd);
			_res.add(_res_cmd_2);
			_res_cmd_2.multValue = processing_cmd.multValue;
			return _res;
		}

		@Override
		public JobProcessor<TestCommandB, TestCommandB> getClone() {
			return new MulJobProcessor();
		}
	}

	class TransJobProcessor extends JobProcessor<TestCommandB, TestCommandA>{
		@Override
		public List<TestCommandA> processJob(TestCommandB processing_cmd) {
			if (processing_cmd.multValue < 10){
				// tests that balancing by null result pushing works
				_atom_res.addAndGet(processing_cmd.multValue);
				if (printTraces) System.out.println("     TransJobConsumer - process : "+processing_cmd.multValue +"-> null");
				return null;
			}
			if (printTraces) System.out.println("     TransJobConsumer - process : "+processing_cmd.multValue);
			List<TestCommandA> _res = new ArrayList<TestCommandA>();
			TestCommandA _res_cmd = new TestCommandA(processing_cmd.multValue, processing_cmd);
			_res.add(_res_cmd);
			return _res;
		}

		@Override
		public JobProcessor<TestCommandB, TestCommandA> getClone() {
			return new TransJobProcessor();
		}
	}

	class FinishJobProcessor extends JobProcessor<TestCommandA, TestCommandA>{

		@Override
		public List<TestCommandA> processJob(TestCommandA processing_cmd) {
			_atom_res.addAndGet(processing_cmd.addValue);
			if (printTraces) System.out.println("              FinishJobConsumer - process : "+processing_cmd.addValue);
			return null;
		}

		@Override
		public JobProcessor<TestCommandA, TestCommandA> getClone() {
			return new FinishJobProcessor();
		}

	}

	class AddingJobConsumer extends JobConsumerThread<TestCommandA, TestCommandB>{

		public AddingJobConsumer(
				RequestResultInterface<TestCommandA> req_queue,
				RequestResultInterface<TestCommandB> res_queue) {
			super(req_queue, res_queue);
			associateProcessor(TestCommandA.class, new InternalAdderJobProcessor());
		}

		class InternalAdderJobProcessor extends AdderJobProcessor{
			public List<TestCommandB> processJob(TestCommandA processing_cmd){
				// tests the fact of not creating child commands
				if (processing_cmd.addValue < 0){
					return null;
				}
				else{
					if (printTraces) System.out.println("AddingJobConsumer - process : "+processing_cmd.addValue);
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




	@Test
	public void test() {
		
		_atom_res.set(0);
		
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
				if (printTraces) System.out.println("  _mult_thread - process : "+job_req.multValue);
				return super.processJob(job_req);
			}
			public TestCommandB pullJobToProcess() {
				return pullRequestJobToProcess();
			}
			public void pushProcessedJob(List<TestCommandB> job_res) {
				pushJobResultAsResult(job_res);
			}
		};
		_mul_thread.associateProcessor(TestCommandB.class, new MulJobProcessor());

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
		_trans_thread.associateProcessor(TestCommandB.class, new TransJobProcessor());

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
		_result_thread.associateProcessor(TestCommandA.class, new FinishJobProcessor());

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
		if (printTraces) System.out.println("Basic jobs finish.");
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
		if (printTraces) System.out.println("Feeding with alive thread finished.");
		assertTrue("feeding with alive thread does not work : "+_atom_res.get()+" instead of "+_sum, _sum == _atom_res.get());	

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
		if (printTraces) System.out.println("closing with alive thread finished.");
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
		if (printTraces) System.out.println("reopening with alive threads finished");
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
		if (printTraces) System.out.println("Ensuring all threads are dead finished.");
		assertTrue("All threads dead : "+_atom_res.get()+" instead of 0", 0 == _atom_res.get());	

	}
	
	final boolean printTracesAncestry = false;
	
	class TestCommandD extends TestCommandA{

		public TestCommandD(int int_val, Command parent_cmd) {
			super(int_val, parent_cmd);
		}
		
	}
	
	class TestCommandE extends TestCommandA{

		public TestCommandE(int int_val, Command parent_cmd) {
			super(int_val, parent_cmd);
		}
		
	}

	class TestAncestryJobProcessor3 extends JobProcessor<TestCommandA, TestCommandA>{

		@Override
		public List<TestCommandA> processJob(TestCommandA processing_cmd) {
			if (printTracesAncestry) System.out.println("JobProc3 :		before _atom = "+_atom_res.get());

			_atom_res.set(processing_cmd.addValue);
			if (printTracesAncestry) System.out.println("			after _atom = "+_atom_res.get());
			
			List<TestCommandA> _res = new ArrayList<TestCommandA>();
			for (int _i=0; _i<3; _i++){
				TestCommandD _test_child = new TestCommandD(1, processing_cmd);
				_res.add(_test_child);
			}
			TestCommandE _finishing_next = new TestCommandE(4, null);
			_finishing_next.setPreviousCommand(processing_cmd);
			_res.add(_finishing_next);
			return _res;
		}

		@Override
		public JobProcessor<TestCommandA, TestCommandA> getClone() {
			return new TestAncestryJobProcessor3();
		}

	}

	class TestAncestryJobProcessor4 extends JobProcessor<TestCommandA, TestCommandA>{
		@Override
		public List<TestCommandA> processJob(TestCommandA processing_cmd) {
			_atom_res.incrementAndGet();
			if (printTracesAncestry) System.out.println("JobProc4 : updated value = " + _atom_res.get());
			
			return null;
		}

		@Override
		public JobProcessor<TestCommandA, TestCommandA> getClone() {
			return new TestAncestryJobProcessor4();
		}
	}
	static AtomicInteger _atom_res = new AtomicInteger(0);		

	class TestAncestryJobProcessor5 extends JobProcessor<TestCommandA, TestCommandA>{

		@Override
		public List<TestCommandA> processJob(TestCommandA processing_cmd) {
			if (printTracesAncestry) System.out.println("JobProc5	before: _atom = " +_atom_res.get());
			_atom_res.addAndGet(_atom_res.get()+processing_cmd.addValue);
			if (printTracesAncestry) System.out.println("			after : _atom = "+_atom_res.get());
			return null;
		}

		@Override
		public JobProcessor<TestCommandA, TestCommandA> getClone() {
			return new TestAncestryJobProcessor5();
		}
	}

	class TestAncestryJobConsumer extends JobConsumerThread<TestCommandA, TestCommandA>{

		public TestAncestryJobConsumer(
				RequestResultInterface<TestCommandA> req_queue,
				RequestResultInterface<TestCommandA> res_queue) {
			super(req_queue, res_queue);
			associateProcessor(TestCommandC.class, new TestAncestryJobProcessor3());
			associateProcessor(TestCommandD.class, new TestAncestryJobProcessor4());
			associateProcessor(TestCommandE.class, new TestAncestryJobProcessor5());
		}

		@Override
		public TestCommandA pullJobToProcess() {
			return pullRequestJobToProcess();
		}

		@Override
		public void pushProcessedJob(List<TestCommandA> job_res) {
			pushJobResultAsRequest(job_res);
		}

	}


	class TestCommandC extends TestCommandA{

		public TestCommandC(int int_val, Command parent_cmd) {
			super(int_val, parent_cmd);
		}
		
	}
	
	
	
	@Test
	public void testAncestry() {
		RequestResultInterface<TestCommandA> _add_RRI = new RequestResultInterface<TestJobConsumerThread.TestCommandA>();
		_add_RRI.setRequestQueueScheduler(new SequentialJobScheduler<TestJobConsumerThread.TestCommandA>());
		_add_RRI.setResultQueueScheduler(new HighestCostPriorityFirstJobScheduler<TestJobConsumerThread.TestCommandA>());

		// test crossing ancestry via previous/next and parent/children relationships
		// A creates B C D E 
		// A is parent of B C D
		// A is previous of E
		// B C D should be processed before E so that E can use B C D results

		TestAncestryJobConsumer _test_ancestry_thread = new TestAncestryJobConsumer(_add_RRI, _add_RRI);
		
		int _val_test_anc = 2;
		
		TestCommandA _test_anc_req = new TestCommandC(_val_test_anc, null);
		_add_RRI.pushRequest(_test_anc_req);
		
		_test_ancestry_thread.setToDieUnused();
		_test_ancestry_thread.start();
		
		_add_RRI.waitUntilBalanced();
		
		int _attended_value =  (_val_test_anc + 3) * 2 + 4;
		assertTrue("get "+ _atom_res.get()+" instead of " + _attended_value, _atom_res.get() == _attended_value);

	}

}
