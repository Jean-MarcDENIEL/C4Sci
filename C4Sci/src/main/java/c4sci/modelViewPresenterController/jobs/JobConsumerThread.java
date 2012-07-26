package c4sci.modelViewPresenterController.jobs;

import java.util.Vector;

public class JobConsumerThread extends Thread {
	private class QueueProcessorCouple{
		WaitingJobQueue<Command> waitingQueue;
		ConsumptionProcessor<Command> consumptionProcessor;
	};
	private Vector<QueueProcessorCouple> queueProcVector;
	
	public JobConsumerThread(){
		queueProcVector = new Vector<JobConsumerThread.QueueProcessorCouple>();
	}
	
	public <C extends Command>void addJobTypeToConsume(WaitingJobQueue<C> w_q, ConsumptionProcessor<C> c_exec){
		
	}
}
