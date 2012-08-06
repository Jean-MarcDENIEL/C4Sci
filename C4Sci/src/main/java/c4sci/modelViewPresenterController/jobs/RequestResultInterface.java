package c4sci.modelViewPresenterController.jobs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import c4sci.modelViewPresenterController.jobs.exceptions.NoJobToProcessException;

/**
 * This class is designed to provide an interface between two groups of threads :
 * 
 * <li> Request Threads  : threads pushing requests for processing jobs and pulling processed jobs' results
 * <li> Result Threads : threads pulling requests, processing corresponding jobs and pushing job results
 * <br>
 * This interface must ensure a clean termination : stopping new requests and ensuring that all requests have been fullfilled.
 * 
 * @author jeanmarc.deniel
 *
 */
public class RequestResultInterface <C extends Command>{
	private WaitingJobQueue<C> requestQueue;
	private WaitingJobQueue<C> resultQueue;
	/**
	 * Number of pushed requests minus number of pulled results
	 */
	private int 			requestResultBalance;
	private ReentrantLock 	internalLock;
	private Condition 		isBalancedCondition;
	private Condition		requestQueueNotEmptyCondition;
	private Condition		resultQueueNotEmptyCondition;
	private boolean			isOpenedForRequestsFlag;

	public RequestResultInterface(){
		requestQueue 		= new WaitingJobQueue<C>();
		resultQueue  		= new WaitingJobQueue<C>();
		requestResultBalance = 0;
		internalLock 		= new ReentrantLock();
		isBalancedCondition 			= internalLock.newCondition();
		requestQueueNotEmptyCondition 	= internalLock.newCondition();
		resultQueueNotEmptyCondition	= internalLock.newCondition();
		isOpenedForRequestsFlag = true;
	}
	/**
	 * 
	 * @return true is all requests have been processed and corresponding results have been pushed in.
	 */
	public boolean isBalanced(){
		return requestResultBalance == 0;
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
		internalLock.lock();
		try{
			while (!isBalanced()){
				isBalancedCondition.await();
			}
		} 
		catch (InterruptedException _e) {
		}
		finally{
			internalLock.unlock();
		}
	}
	
	public void pushRequest(C req_cmd){
		internalLock.lock();
		try{
			if (isOpenedForRequests()){
				requestQueue.appendJobAtLastPosition(req_cmd);
				requestResultBalance ++;
				requestQueueNotEmptyCondition.signalAll();
			}
		}
		finally{
			internalLock.unlock();
		}
	}
	
	public void pushResult(C res_cmd){
		internalLock.lock();
		try{
			resultQueue.appendJobAtLastPosition(res_cmd);
			resultQueueNotEmptyCondition.signalAll();
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
			-- requestResultBalance;
			if (isBalanced()){
				isBalancedCondition.signalAll();
			}
			return _res;
		}
		catch (NoJobToProcessException _e) {
			isBalancedCondition.signalAll();
			return null;
		}
		catch (InterruptedException _e) {
			if (isBalanced()){
				isBalancedCondition.signalAll();
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
