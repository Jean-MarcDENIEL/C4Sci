package c4sci.modelViewPresenterController.jobs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import c4sci.modelViewPresenterController.jobs.exceptions.NoJobToProcessException;

/**
 * A double job queue interface distributing request and collecting results.<br> 
 * 
 * This class is designed to provide an interface between two groups of threads :
 * 
 * <li> Request Threads  : threads pushing requests for processing jobs and pulling processed jobs' results
 * <li> Result Threads : threads pulling requests, processing corresponding jobs and pushing job results
 * <br><br>
 * 
 * To be fully functional the interface must have its Request/Result schedulers set.<br><br>
 * 
 * <b>Clean termination</b><br>
 * This interface must ensure a clean termination : stopping new requests and ensuring that all requests have been fulfilled.<br>
 * To achieve this :
 * <ol>
 * <li> the closeForRequests() method must be called</li>
 * <li> the waitUntilBalanced() method  must be called</li>
 * </ol>
 * Then the current thread will wait until all request have been processed, what means that an even number
 * of results have been pulled or pushed as null.<br><br>
 * In the case below requests are pulled and processed. Then, as there is no result to treat afterward, null result are pushed back or shutRequest() is called :<br>
 * <img src="doc-files/RequestResultInterface balancing.jpg"><br><br>
 * In the case below requests are pulled and processed by a first thread. Then  pulled back results are processed by a second thread.<br>
 * <img src="doc-files/RequestResultInterface balancing 2.jpg"><br><br>
 * 
 * <br>
 * @author jeanmarc.deniel
 *
 */
public final class RequestResultInterface <C extends Command>{
	private WaitingJobQueue<C> requestQueue;
	private WaitingJobQueue<C> resultQueue;
	/**
	 * Number of pushed requests minus number of pulled results
	 */
	//private int 			requestResultBalance;
	private ReentrantLock 	internalLock;
	private Condition 		isBalancedCondition;
	private Condition		requestQueueNotEmptyCondition;
	private Condition		resultQueueNotEmptyCondition;
	private boolean			isOpenedForRequestsFlag;
	private List<C>			jobsToAnalyseForBalanceList;

	
	class BalanceReflex implements Command.CommandReflex{
		public void doReflex(Command processed_command) {
			if (processed_command.hasBeenProcessed()){
				internalLock.lock();
				try{
					//requestResultBalance --;
					//System.out.println(RequestResultInterface.this+":						doReflex() : balance="+requestResultBalance);
					jobsToAnalyseForBalanceList.remove(processed_command);
				}
				finally{
					internalLock.unlock();
				}
				
			}
			if (isBalanced()){
				internalLock.lock();
				try{
						isBalancedCondition.signalAll();
				}
				finally{
					internalLock.unlock();
				}
			}
		}
	}
	
	public RequestResultInterface(){
		requestQueue 		= new WaitingJobQueue<C>();
		resultQueue  		= new WaitingJobQueue<C>();
		//requestResultBalance = 0;
		internalLock 		= new ReentrantLock();
		isBalancedCondition 			= internalLock.newCondition();
		requestQueueNotEmptyCondition 	= internalLock.newCondition();
		resultQueueNotEmptyCondition	= internalLock.newCondition();
		isOpenedForRequestsFlag = true;
		jobsToAnalyseForBalanceList		= new ArrayList<C>();
	}
	public void setRequestQueueScheduler(JobScheduler<C> job_sch){
		requestQueue.setJobScheduler(job_sch);
	}
	public void setResultQueueScheduler(JobScheduler<C> job_sch){
		resultQueue.setJobScheduler(job_sch);
	}
	/**
	 * 
	 * @return true is all requests answer true
	 */
	public boolean isBalanced(){
		internalLock.lock();
		try{
			for (Iterator<C> _it=jobsToAnalyseForBalanceList.iterator(); _it.hasNext();){
				if (_it.next().hasBeenProcessed()){
					_it.remove();
				}
				else{
					return false;
				}
			}
			return true;
		}finally{
			internalLock.unlock();
		}
	}
	/**
	 * Stops permitting requests to be pushed in.
	 */
	public void closeForRequests(){
		internalLock.lock();
		try{
			isOpenedForRequestsFlag = false;
		}
		finally{
			internalLock.unlock();
		}
	}
	public void openForRequests(){
		internalLock.lock();
		try{
			isOpenedForRequestsFlag = true;
		}
		finally{
			internalLock.unlock();
		}	
	}
	public boolean isOpenedForRequests(){
		internalLock.lock();
		try{
			return isOpenedForRequestsFlag;
		}
		finally{
			internalLock.unlock();
		}
	}
	/**
	 * Waits until all requests have been processed and results have been pulled out.
	 */
	public void waitUntilBalanced(){
		while (!isBalanced()){		
			internalLock.lock();
			try{
				isBalancedCondition.await();
			} 
			catch (InterruptedException _e) {
			}
			finally{
				internalLock.unlock();
			}
		}
	}
	/**
	 * 
	 * @param req_cmd the requested job. Pushing a null value will have no effect.
	 */
	public void pushRequest(C req_cmd){
		if (req_cmd == null){
			return;
		}
	
		req_cmd.addReflexOnChildNotification(new BalanceReflex());
		
		internalLock.lock();
		try{
			if (isOpenedForRequests()){
				requestQueue.appendJobAtLastPosition(req_cmd);
				// sets the command to be analyzed when computing balance
				jobsToAnalyseForBalanceList.add(req_cmd);
				
				//requestResultBalance ++;
				
				requestQueueNotEmptyCondition.signalAll();
			}
		}
		finally{
			internalLock.unlock();
		}
	}
	/**
	 * Pushing a result in the Result queue and computing the balancing if null is pushed.
	 * @see #waitUntilBalanced
	 * @param res_cmd a job result. A <b>null value </b>means that no job result will be pushed corresponding to a previously pushed request.
	 * In this case, the balance between pushed requests and pulled results will be update as if a result had been pulled out.
	 */
	public void pushResult(C res_cmd){
		internalLock.lock();
		try{
			if (res_cmd != null){
				res_cmd.addReflexOnChildNotification(new BalanceReflex());
				resultQueue.appendJobAtLastPosition(res_cmd);
				resultQueueNotEmptyCondition.signalAll();
			}
		}
		finally{
			internalLock.unlock();
		}
	}

	/**
	 * 
	 * @param time_out the time to wait for a new C to be pushed in
	 * @param time_unit
	 * @return null if no Command could be pulled out during the waiting time
	 */
	public C pullResult(long time_out, TimeUnit time_unit){
		long _nanos = time_unit.toNanos(time_out);
		internalLock.lock();
		try {
			if (resultQueue.isEmpty()){
				_nanos = resultQueueNotEmptyCondition.awaitNanos(_nanos);
				if (_nanos <= 0L){
					return null;
				}
			}
			C _res = resultQueue.extractAJobToProcess();
			//balanceOnPulledResult();
			return _res;
		}
		catch (NoJobToProcessException _e) {
			//isBalancedCondition.signalAll();
			return null;
		}
		catch (InterruptedException _e) {
			if (isBalanced()){
				//isBalancedCondition.signalAll();
			}
			return null;
		}
		finally {
			
			internalLock.unlock();
		}
	}

	public C pullRequest(long time_out, TimeUnit time_unit){
		long _nanos = time_unit.toNanos(time_out);
		internalLock.lock();
		try{
			if (requestQueue.isEmpty()){
				_nanos = requestQueueNotEmptyCondition.awaitNanos(_nanos);
				if (_nanos <= 0){
					return null;
				}
			}
			return requestQueue.extractAJobToProcess();
		} catch (InterruptedException _e) {
			return null;
		} catch (NoJobToProcessException _e) {
			return null;
		}
		finally{
			internalLock.unlock();
		}
	}
	
	
}
