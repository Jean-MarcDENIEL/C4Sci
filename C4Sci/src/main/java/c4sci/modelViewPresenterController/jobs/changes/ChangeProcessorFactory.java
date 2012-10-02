package c4sci.modelViewPresenterController.jobs.changes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.modelViewPresenterController.jobs.Command;


/**
 * This class is able to give the {@link ChangeProcessorPrototype} corresponding to a certain {@link Command}.<br>
 * <br>
 * <b>Pattern :</b> This class instantiates the <b>Abstract Factory</b> GoF pattern. 
 * @author jeanmarc.deniel
 *
 */
public class ChangeProcessorFactory <C_request extends Command, C_result extends Command>{
	@SuppressWarnings("rawtypes")
	private Map<Class, ChangeProcessorPrototype<C_request,C_result>> changePerformersMap;
	
	@SuppressWarnings("rawtypes")
	public ChangeProcessorFactory() {
		changePerformersMap					= new ConcurrentHashMap<Class, ChangeProcessorPrototype<C_request,C_result>>();
	}
	
	/**
	 * Furnish a {@link ChangeProcessorPrototype} that is able to process the {@link Command} according to the request argument.<br>
	 * <br>
	 * <b>Pattern :</b> This method instantiates the <b>factory method</b> GoF pattern.<br>
	 * <b>Pattern :</b> This method uses to <b>prototype</b> ability of {@link ComponentChangeRunnable ComponentChangeRunnables}.<br>
	 * @param change_command The update request.
	 * @return The Runnable to {@link java.lang.Runnable#run()} in order to obtain the asked change.
	 * @throws CannotPerformSuchChangeException is "this" component can't perform such an update.
	 */
	public ChangeProcessorPrototype<C_request,C_result> createChangePerformer(C_request change_command) throws CannotPerformSuchChangeException{
		ChangeProcessorPrototype<C_request,C_result> _prototype = changePerformersMap.get(change_command.getClass());
		if (_prototype == null){
			throw new CannotPerformSuchChangeException("No runnable for this kind of change.");
		}
		ChangeProcessorPrototype<C_request,C_result> _res = _prototype.getClone(change_command);
		if (_res == null){
			throw new CannotPerformSuchChangeException("Prototype ComponentChangeRunnable cannot clone");
		}
		return _res;
	}
	/**
	 * This method adds the Component the ability to perform some {@link ComponentChange}.<br>
	 * <b>Pattern : </b> This method instantiates the <b>strategy</b> GoF pattern.<br>
	 * <b>Pattern : </b> This method relies on the <b>prototyping</b> ability of the {@link ComponentChangeRunnable} class.
	 * @param comp_chgt The ID of the {@link ComponentChange ComponentChanges} to perform.
	 * @param change_performer_prototype The prototype of the {@link ComponentChangeRunnable} adapted to perform changes of comp_chgt id.
	 */
	@SuppressWarnings("rawtypes")
	public void addChangePerformingAbility(Class comp_chgt, ChangeProcessorPrototype<C_request,C_result> change_performer_prototype){
		changePerformersMap.put(comp_chgt, change_performer_prototype);
	}

}
