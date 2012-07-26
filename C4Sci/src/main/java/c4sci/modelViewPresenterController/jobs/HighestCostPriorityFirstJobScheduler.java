package c4sci.modelViewPresenterController.jobs;

import java.util.Iterator;

/**
 * This Scheduler choose jobs in descending priority * cost order
 * 
 * @author jeanmarc.deniel
 *
 */
public class HighestCostPriorityFirstJobScheduler<C extends Command> implements JobScheduler<C> {

	public C chooseJobToProcess(Iterator<C> job_iterator)
			throws NoJobToProcessException {
		int _highest_cost = -1;
		C _res = null;
		while(job_iterator.hasNext()){
			C _job = job_iterator.next();
			if (_job.getCost()>_highest_cost){
				_res = _job;
				_highest_cost = _job.getCost();
			}
		}
		
		if (_res == null){
			throw new NoJobToProcessException("empty queue", null);
		}
		return _res;
	}

}
