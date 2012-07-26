package c4sci.modelViewPresenterController.jobs;

import java.util.Iterator;

public class SequentialJobScheduler<C extends Command> implements JobScheduler<C> {


	public C chooseJobToProcess(Iterator<C> job_iterator) throws NoJobToProcessException {
		if (job_iterator.hasNext()){
			return job_iterator.next();
		}
		throw new NoJobToProcessException("Empty waiting queue", null);
	}

}
