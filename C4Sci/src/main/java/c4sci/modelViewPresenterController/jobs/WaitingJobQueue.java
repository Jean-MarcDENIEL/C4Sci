package c4sci.modelViewPresenterController.jobs;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class collects and distributes jobs waiting to be processed.
 * Jobs' previous and following jobs are not modified by this class methods.
 * @author jeanmarc.deniel
 *
 */
public class WaitingJobQueue<C extends Command> {
	
	private class JobChainLink{
		private JobChainLink	previousLink;
		private JobChainLink	followingLink;
		private C				jobElement;
		
		void setPrevious(JobChainLink p_l){
			previousLink = p_l;
		}
		JobChainLink getPrevious(){
			return previousLink;
		}
		void setFollowing(JobChainLink f_l){
			followingLink = f_l;
		}
		JobChainLink getFollowing(){
			return followingLink;
		}
		
		JobChainLink(C job_elt, JobChainLink prev_job, JobChainLink foll_job){
			jobElement 		= job_elt;
			previousLink	= prev_job;
			followingLink	= foll_job;
		}
	};
	// head and queue of a linked chained commands
	private JobChainLink firstCommand;
	private JobChainLink lastCommand;
	private Map<C, JobChainLink> commandLinkMap;
	
	private JobScheduler<C>	jobScheduler;
	
	public WaitingJobQueue(){
		firstCommand	= null;
		lastCommand		= null;
		commandLinkMap	= new ConcurrentHashMap<C, JobChainLink>();
		jobScheduler	= new SequentialJobScheduler<C>();
	}
	
	public final Iterator<C> getJobIterator(){
		return new Iterator<C>(){
			private JobChainLink currentCommand = firstCommand;
			public boolean hasNext() {
				return (currentCommand != null);
			}
			//CHECKSTYLE:OFF
			public C next() {
				//CHECKSTYLE:ON
				C _res= currentCommand.jobElement;
				currentCommand = currentCommand.followingLink;
				return _res;
			}
			//CHECKSTYLE:OFF
			public void remove() {
				//CHECKSTYLE:ON
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Appends a new job at last position to the waiting queue.
	 * @param new_job
	 */
	public void appendJobAtLastPosition(C new_job){

		if (firstCommand == null){
			lastCommand = new JobChainLink(new_job, null, null);
			firstCommand = lastCommand;
		}
		else{
			lastCommand = new JobChainLink(new_job, lastCommand, null);
			lastCommand.getPrevious().setFollowing(lastCommand);
		}
	}
	/**
	 * 	
	 * @return true if there is no job to process.
	 */
	public boolean isEmpty(){
		return firstCommand == null;
	}
	/**
	 * 
	 * @return a job to process, that has been extracted from the waiting job queue. 
	 * @throws NoJobToProcessException if there is no job waiting to be processed.
	 */
	public C extractAJobToProcess() throws NoJobToProcessException{
		C _job_to_process;
		try {
			_job_to_process 		= jobScheduler.chooseJobToProcess(getJobIterator());
			JobChainLink _job_link 	= commandLinkMap.get(_job_to_process);
			
			JobChainLink _previous_link 	= _job_link.getPrevious();
			JobChainLink _following_link	= _job_link.getFollowing();
			
			if (_previous_link != null){
				_previous_link.setFollowing(_following_link);
			}
			if (_following_link != null){
				_following_link.setPrevious(_previous_link);
			}
			if (((Object)_job_link).equals(firstCommand)){
				firstCommand 	= _job_link.getFollowing();
			}
			if (((Object)_job_link).equals(lastCommand)){
				lastCommand 	= _job_link.getPrevious();
			}
		} catch (NoJobToProcessException _e) {
			throw new NoJobToProcessException("no job to process", _e);	
		}
		return _job_to_process;
	}
}