package c4sci.modelViewPresenterController.jobs.schedulers;

import java.util.Iterator;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.JobScheduler;
import c4sci.modelViewPresenterController.jobs.exceptions.NoJobToProcessException;

public class SequentialJobScheduler<C extends Command> implements JobScheduler<C> {


	public C chooseJobToProcess(Iterator<C> job_iterator) throws NoJobToProcessException {
		if (job_iterator.hasNext()){
			return job_iterator.next();
		}
		throw new NoJobToProcessException("Empty waiting queue", null);
	}

}
