package c4sci.modelViewPresenterController.jobs;

import java.util.Iterator;
/**
 * This class aims at choosing a job to process among waiting jobs in a queue represented by an iterator.
 * @author jeanmarc.deniel
 *
 * @param <C> Jobs to process
 */
public interface JobScheduler<C extends Command> {
	/**
	 * Choose a job to process <b>without removing it by iterator.remove().</b>
	 * @param job_iterator an iterator on the waiting jobs queue.
	 * @throws NoJobToProcessException if the job queue is empty.
	 */
	C chooseJobToProcess(Iterator<C> job_iterator) throws NoJobToProcessException;
}
