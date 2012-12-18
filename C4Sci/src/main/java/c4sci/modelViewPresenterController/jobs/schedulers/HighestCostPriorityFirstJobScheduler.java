package c4sci.modelViewPresenterController.jobs.schedulers;

import java.util.Iterator;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.JobScheduler;
import c4sci.modelViewPresenterController.jobs.exceptions.NoJobToProcessException;

/**
 * This Scheduler choose jobs in descending priority * cost order
 * 
 * @author jeanmarc.deniel
 *
 */
public class HighestCostPriorityFirstJobScheduler<C extends Command> implements JobScheduler<C> {

	public C chooseJobToProcess(Iterator<C> job_iterator)
			throws NoJobToProcessException {
		int _highest_cost_priority = -1;
		C _res = null;
		while(job_iterator.hasNext()){
			C _job = job_iterator.next();
			if (_job.getCost()*_job.getPriority()>_highest_cost_priority){
				_res = _job;
				_highest_cost_priority = _job.getCost()*_job.getPriority();
			}
		}
		
		if (_res == null){
			throw new NoJobToProcessException("empty queue", null);
		}
		return _res;
	}

}
