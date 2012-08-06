package c4sci.modelViewPresenterController.jobs.consumption;

import java.util.ArrayList;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.WaitingJobQueue;

public class JobConsumerThread<C extends Command> extends Thread {
	private final class QueueProcessorCouple<C2 extends Command>{
		private WaitingJobQueue<C2> waitingQueue;
		private ConsumptionProcessor<C2> consumptionProcessor;
		public QueueProcessorCouple(WaitingJobQueue<C2> wait_queue, ConsumptionProcessor<C2> cons_proc){
			setWaitingQueue(wait_queue);
			setConsumptionProcessor(cons_proc);
		}
		@SuppressWarnings("unused")
		public ConsumptionProcessor<C2> getConsumptionProcessor() {
			return consumptionProcessor;
		}
		public void setConsumptionProcessor(ConsumptionProcessor<C2> consumption_processor) {
			this.consumptionProcessor = consumption_processor;
		}
		@SuppressWarnings("unused")
		public WaitingJobQueue<C2> getWaitingQueue() {
			return waitingQueue;
		}
		public void setWaitingQueue(WaitingJobQueue<C2> waiting_queue) {
			this.waitingQueue = waiting_queue;
		}
		
	};
	private ArrayList<QueueProcessorCouple<C>> queueProcVector;
	
	public JobConsumerThread(){
		queueProcVector = new ArrayList<JobConsumerThread<C>.QueueProcessorCouple<C>>();
	}
	
	public void addJobTypeToConsume(WaitingJobQueue<C> w_q, ConsumptionProcessor<C> c_exec){
		queueProcVector.add(new QueueProcessorCouple<C>(w_q, c_exec));
	}
	//CHECKSTYLE:OFF
	public void run(){
		//CHECKSTYLE:ON
	}
}
