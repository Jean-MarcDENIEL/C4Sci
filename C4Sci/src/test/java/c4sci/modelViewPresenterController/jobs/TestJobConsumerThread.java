package c4sci.modelViewPresenterController.jobs;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.modelViewPresenterController.jobs.consumption.ConsumptionProcessor;
import c4sci.modelViewPresenterController.jobs.consumption.JobConsumerThread;

public class TestJobConsumerThread {

	class TestCommandA extends Command{

		@Override
		protected boolean isUndoable() {
			return false;
		}

		@Override
		protected void processJob() {
		}

		@Override
		void unprocessJob() {
		}
	};
	
	class TestCommandB extends Command{

		@Override
		protected boolean isUndoable() {
			return false;
		}

		@Override
		protected void processJob() {
		}

		@Override
		void unprocessJob() {
		}		
	}
	
	@Test
	public void test() {
		
		WaitingJobQueue<TestCommandA> _queue_A = new WaitingJobQueue<TestCommandA>();
		ConsumptionProcessor<TestCommandA> _proc_A = new ConsumptionProcessor<TestCommandA>() {
			public void consumeJob(TestCommandA job_to_process) {}
		};
	
		WaitingJobQueue<TestCommandB> _queue_B = new WaitingJobQueue<TestCommandB>();
		ConsumptionProcessor<TestCommandB> _proc_B = new ConsumptionProcessor<TestCommandB>() {
			public void consumeJob(TestCommandB job_to_process) {}
		};
		
		JobConsumerThread _thread = new JobConsumerThread();
		_thread.addJobTypeToConsume(_queue_A, _proc_A);
		_thread.addJobTypeToConsume(_queue_B, _proc_B);

		fail("Not yet implemented");
	}

}
