package c4sci.modelViewPresenterController.viewerPresenterInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This classes creates {@link ComponentChangeRunnable ComponentChangeRunnables} that are able to perform certain {@link ComponentChange ComponentChanges}.<br>
 * <br>
 * <b>Pattern :</b> This class instantiates the <b>Factory </b> GoF pattern.<br>
 * <b>Pattern :</b> This class uses the <b>strategy</b> and <b>prototype</b> GoF patterns in order to avoid having abstract factories.<br>
 *   
 */
public class ComponentChangeRunnableFactory {

	private Map<ComponentChange.ChangeID, ComponentChangeRunnable> changePerformersMap;
	
	public ComponentChangeRunnableFactory() {
		changePerformersMap					= new ConcurrentHashMap<ComponentChange.ChangeID, ComponentChangeRunnable>();
	}
	
	/**
	 * Furnish a ComponentnChangeRunnable that is able to update the Component according to the request argument.<br>
	 * <br>
	 * <b>Pattern :</b> This method instantiates the <b>factory method</b> GoF pattern.<br>
	 * <b>Pattern :</b> This method uses to <b>prototype</b> ability of {@link ComponentChangeRunnable ComponentChangeRunnables}.<br>
	 * @param comp_change The update request.
	 * @return The Runnable to {@link java.lang.Runnable#run()} in order to obtain the asked change.
	 * @throws CannotPerformSuchChangeException is "this" component can't perform such an update.
	 */
	public ComponentChangeRunnable createChangePerformer(ComponentChange comp_change) throws CannotPerformSuchChangeException{
		ComponentChangeRunnable _prototype = changePerformersMap.get(comp_change.getChangeID());
		if (_prototype == null){
			throw new CannotPerformSuchChangeException("No runnable for this kind of change.");
		}
		ComponentChangeRunnable _res = _prototype.clonePrototype(comp_change);
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
	public void addChangePerformingAbility(ComponentChange.ChangeID comp_chgt, ComponentChangeRunnable change_performer_prototype){
		changePerformersMap.put(comp_chgt, change_performer_prototype);
	}

}