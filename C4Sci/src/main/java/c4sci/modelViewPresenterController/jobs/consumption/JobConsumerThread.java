package c4sci.modelViewPresenterController.jobs.consumption;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.RequestResultInterface;

/**
 * This subclass of Thread passes its time 
 * <ol>
 * <li> pulling a job (request or result) out of a first RequestResultInterface by calling the abstract <b>pullJobToProcess()</b> method
 * <li> processing the job by calling the abstract <b>processJob()</b> method
 * <li> pushing the result into a second RequestResultInterface< by calling the abstract <b>pushProcessedJob()</b> method
 * </ol>
 * Note : the RequestResultInterfaces can be the same one.<br><br>
 * <b>Pattern :</b> This class makes use of the <b>Template Method </b>GoF pattern : <br>
 * pullJobToProcess() and processJob() methods will be defined in derived classes.
 * @author jeanmarc.deniel
 *
 * @param <C>
 */
public abstract class JobConsumerThread<C_request extends Command, C_result extends Command> extends Thread {
	

	private RequestResultInterface<C_request> 	inputQueue;
	private RequestResultInterface<C_result>  	outputQueue;
	private AtomicBoolean 						shouldDie;
	private long 								waitingTimeMillisec;
	private static final long					MAX_WAITING_TIME_MILLISEC = 256;

	/**
	 * Treats the job.
	 * @param job_req the job to process
	 * @return null in the case where there is no result to treat afterward
	 */
	public abstract C_result 	processJob(C_request job_req);

	/**
	 * This method must be defined to call whether pullRequestJobToProcess()
	 * or pullResultJobToProcess()
	 * @return null if there is no job to process
	 */
	public abstract C_request	pullJobToProcess();
	/**
	 * This method must be defined to call wether pushJobResultAsRequest()
	 * or pushJobResultAsResult()
	 * @param job_res
	 */
	public abstract void pushProcessedJob(C_result job_res);
	
	/**
	 * This method sets the max delay to wait in the case 
	 * the input job queue is empty. <br>
	 * 
	 * This method can be overridden to implement custom max waiting time
	 * @param actual waiting_time_millisec
	 * @return next waiting time in milliseconds, >= 1.
	 */
	public long computeWaitingTimeOnEmptyQueue(long waiting_time_millisec){
		return Math.min(MAX_WAITING_TIME_MILLISEC, waiting_time_millisec * 2);
	}
	
	protected final C_request pullRequestJobToProcess(){
			return inputQueue.pullRequest(waitingTimeMillisec, TimeUnit.MILLISECONDS);
	}
	protected final C_request pullResultJobToProcess(){
			return inputQueue.pullResult(waitingTimeMillisec, TimeUnit.MILLISECONDS);
	}
	protected final void pushJobResultAsRequest(C_result job_res){
		outputQueue.pushRequest(job_res);
	}
	protected final void pushJobResultAsResult(C_result job_res){
		outputQueue.pushResult(job_res);
	}
	
	public JobConsumerThread(RequestResultInterface<C_request> req_queue, RequestResultInterface<C_result> res_queue){
		inputQueue =	req_queue;
		outputQueue = 	res_queue;
		waitingTimeMillisec	=	1;
		shouldDie = new AtomicBoolean(false);
	}
	/**
	 * Sets the thread to terminate as soon as there is no more request to pull and process.
	 */
	public final void setToDieUnused(){
		shouldDie.set(true);
	}
	
	//CHECKSTYLE:OFF
	final public void run(){
		//CHECKSTYLE:ON
		while (true){
			C_request _job_to_do = pullJobToProcess();
			if (_job_to_do == null){
				if (shouldDie.get()){
					return;
				}
				waitingTimeMillisec = computeWaitingTimeOnEmptyQueue(waitingTimeMillisec);
			}
			else{
				waitingTimeMillisec = 1;
				C_result _res_job = processJob(_job_to_do);
				if (_res_job != null){
					pushProcessedJob(_res_job);
				}
			}
		}
	}
}
