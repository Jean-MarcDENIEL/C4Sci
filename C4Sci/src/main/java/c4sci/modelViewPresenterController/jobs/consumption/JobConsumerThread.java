package c4sci.modelViewPresenterController.jobs.consumption;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.RequestResultInterface;

/**
 * This subclass of thread is designed to process jobs waiting in a WaitingJobQueue.
 * <br><br>
 * Until told to die in case of empty waiting job queue, its loop is the following :  
 * <ol>
 * <li> pulling a job (request or result) out of a first RequestResultInterface by calling the abstract <b>pullJobToProcess()</b> method
 * <li> processing the job by calling the abstract <b>processJob()</b> method
 * <li> pushing the result into a second RequestResultInterface< by calling the abstract <b>pushProcessedJob()</b> method
 * </ol>
 * Note : the RequestResultInterfaces can be the same one.<br><br>
 * <b>Pattern :</b> This class makes use of the <b>Template Method </b>GoF pattern : <br>
 * pullJobToProcess() and processJob() and PuhProcessedJob() methods will be defined in derived classes.<br><br>
 * 
 * <b>Pattern :</b> This class makes use of the <b>Strategy Method</b> GoF pattern :<br>
 * job processing is defined in JobProcessor subclasses.<br><br>
 * <b>Consistency warning :</b> The JobconsumerThread will be submitted Commands with different flags. The thread must have a strategy JobProcessor 
 * for every flag otherwise the Commands will be lost without having been processed.
 * <br><br>
 * 
 * In the sequence below a JobConsumerThread pulls requests and  pushes results in a single RequestResultInterface:<br>
 * (please note that Requests and Results have to be of the same type)<br>
 * <img src="doc-files/1_ReqResInterface_treatment.jpg"><br><br>
 * 
 * 
 * In the sequence below  a JobConsumerThread pulls Requests out of one RequestResultInterface and pushes results into another.<br>
 * (In this case, Requests and Results can be of different types)<br>
 * <img src="doc-files/2_ReqResInterface_thread_consumption.jpg"><br><br>
 * 
 * In the sequence below a JobConsumerThread pulls Requests out of one RequestResultInterface and pushes Results into 
 * another or shut the request in the case there's no result to treat afterward.<br>
 * <img src="doc-files/3_ReqResInterface_thread_consumption.jpg"><br><br>
 * 
 * @author jeanmarc.deniel
 *
 */
public abstract class JobConsumerThread<C_request extends Command, C_result extends Command> extends Thread {
	

	private RequestResultInterface<C_request> 	inputQueue;
	private RequestResultInterface<C_result>  	outputQueue;
	private AtomicBoolean 						shouldDie;
	private long 								waitingTimeMillisec;
	private Map<Long, JobProcessor<C_request, C_result>>	processorMap;
	
	
	private static final long					MAX_WAITING_TIME_MILLISEC = 256;

	/**
	 * Treats the job.<br>
	 * This method can be overridden, but offers the following generic behavior : 
	 * <ol>
	 * <li>search the JobProcessor corresponding to the command flag</li>
	 * <li>if such a processor exists, 
	 * 	<ul>
	 * 		<li>returns the result computed by JobProcessor.processJob()
	 * 		<li>otherwise returns null
	 * 	<ul>
	 * </ol>  
	 * @param job_req the job to process
	 * @return null in the case where there is no result to treat afterward, and a C_result otherwise
	 */
	public C_result 	processJob(C_request job_req){
		JobProcessor<C_request, C_result>	_job_proc = processorMap.get(Long.valueOf(job_req.getFlag()));
		if (_job_proc == null){
			return null;
		}
		return _job_proc.processJob(job_req);
	}

	/**
	 * This method must be defined to call whether pullRequestJobToProcess()
	 * or pullResultJobToProcess()
	 * @return null if there is no job to process
	 */
	public abstract C_request	pullJobToProcess();
	/**
	 * This method must be defined to just call whether pushJobResultAsRequest()
	 * or pushJobResultAsResult()
	 * @param job_res may be null, meaning there is no result to treat but balance should be achieved nonetheless.
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
	/**
	 * Makes the Thread to push back a null result in the input result queue.<br>
	 * This method is useful in the case where no result should be passed to the output RequestResultInterface,
	 * as it ensure the good balancing of the input RequestResultInterface.
	 */
	public final void shutRequestJob(){
		inputQueue.pushResult(null);
	}
	
	public JobConsumerThread(RequestResultInterface<C_request> req_queue, RequestResultInterface<C_result> res_queue){
		inputQueue =	req_queue;
		outputQueue = 	res_queue;
		waitingTimeMillisec	=	1;
		shouldDie = new AtomicBoolean(false);
		processorMap	= new ConcurrentHashMap<Long, JobProcessor<C_request,C_result>>();
	}
	
	public final void associateFlagToProcessor(long flag_val, JobProcessor<C_request, C_result> job_proc){
		processorMap.put(Long.valueOf(flag_val), job_proc);
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
				pushProcessedJob(_res_job);
			}
		}
	}
}
