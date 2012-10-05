package c4sci.modelViewPresenterController.jobs;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This class is able to give the {@link JobProcessor} corresponding to a certain {@link Command}.<br>
 * <br>
 * <b>Pattern :</b> This class instantiates the <b>Abstract Factory</b> GoF pattern. 
 * @author jeanmarc.deniel
 *
 */
public class JobProcessorFactory <C_request extends Command, C_result extends Command>{
	@SuppressWarnings("rawtypes")
	private Map<Class, JobProcessor<C_request,C_result>> jobPerformersMap;
	
	@SuppressWarnings("rawtypes")
	public JobProcessorFactory() {
		jobPerformersMap					= new ConcurrentHashMap<Class, JobProcessor<C_request,C_result>>();
	}
	
	/**
	 * Furnish a {@link JobProcessor} that is able to process the {@link Command} according to the request argument.<br>
	 * <br>
	 * <b>Pattern :</b> This method instantiates the <b>factory method</b> GoF pattern.<br>
	 * <b>Pattern :</b> This method uses to <b>prototype</b> ability of {@link ComponentChangeRunnable ComponentChangeRunnables}.<br>
	 * @param change_command The update request.
	 * @return The Runnable to {@link java.lang.Runnable#run()} in order to obtain the asked change.
	 * @throws CannotPerformSuchChangeException is "this" component can't perform such an update.
	 */
	@SuppressWarnings("rawtypes")
	public JobProcessor<C_request,C_result> createJobProcessor(Class change_command) throws CannotPerformSuchChangeException{
		JobProcessor<C_request,C_result> _prototype = jobPerformersMap.get(change_command);
		if (_prototype == null){
			throw new CannotPerformSuchChangeException("No runnable for this kind of change.");
		}
		JobProcessor<C_request,C_result> _res = _prototype.getClone();
		if (_res == null){
			throw new CannotPerformSuchChangeException("Prototype ComponentChangeRunnable cannot clone");
		}
		return _res;
	}
	/**
	 * This method adds the Component the ability to perform some {@link ComponentChange}.<br>
	 * <b>Pattern : </b> This method instantiates the <b>strategy</b> GoF pattern.<br>
	 * <b>Pattern : </b> This method relies on the <b>prototyping</b> ability of the {@link ComponentChangeRunnable} class.
	 * @param comp_chg_class The ID of the {@link ComponentChange ComponentChanges} to perform.
	 * @param change_performer_prototype The prototype of the {@link ComponentChangeRunnable} adapted to perform changes of comp_chgt id.
	 */
	@SuppressWarnings("rawtypes")
	public void addChangePerformingAbility(Class comp_chg_class, JobProcessor<C_request,C_result> change_performer_prototype){
		jobPerformersMap.put(comp_chg_class, change_performer_prototype);
	}
	
	@SuppressWarnings("rawtypes")
	public Set<Class> getManagedCommandTypes(){
		return jobPerformersMap.keySet();
	}

}
