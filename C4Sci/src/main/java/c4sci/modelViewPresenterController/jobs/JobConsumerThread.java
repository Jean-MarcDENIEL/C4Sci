package c4sci.modelViewPresenterController.jobs;

import java.util.Vector;

public class JobConsumerThread extends Thread {
	private class QueueProcessorCouple<C extends Command>{
		WaitingJobQueue<C> waitingQueue;
		ConsumptionProcessor<C> consumptionProcessor;
		public QueueProcessorCouple(WaitingJobQueue<C> wait_queue, ConsumptionProcessor<C> cons_proc){
			waitingQueue = wait_queue;
			consumptionProcessor = cons_proc;
		}
	};
	private Vector<QueueProcessorCouple> queueProcVector;
	
	public JobConsumerThread(){
		queueProcVector = new Vector<JobConsumerThread.QueueProcessorCouple>();
	}
	
	public <C extends Command>void addJobTypeToConsume(WaitingJobQueue<C> w_q, ConsumptionProcessor<C> c_exec){
		queueProcVector.add(new QueueProcessorCouple<C>(w_q, c_exec));
	}
	
	public void run(){
		
	}
}
