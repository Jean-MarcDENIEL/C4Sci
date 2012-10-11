package c4sci.modelViewPresenterController.viewerPresenterInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;

/**
 * This class represents the update commands that can be thrown to Components, like : set the foreground, set an indicator ....<br>
 * <br> 
 * {@link ComponentChange} requests are usually used for special cases that are not treated by {@link Component} 
 * methods.
 * @author jeanmarc.deniel
 * @see Component
 *
 */
public abstract class ComponentChange extends Command{


	private Map<ComponentFamily.StandardComponentProperty, String> propertyInformationsMap;
	private Component	boundComponent;
	private ComponentChange() {super(null);}
	public ComponentChange(Component bound_comp, Command parent_command){
		super(parent_command);
		boundComponent 		= bound_comp;
		propertyInformationsMap = new ConcurrentHashMap<ComponentFamily.StandardComponentProperty, String>();
	}

	/**
	 * @return	The identity of the Component asked to change.
	 */
	public DataIdentity	getComponentIdentity(){
		return boundComponent.getIdentity();
	}
	public Map<ComponentFamily.StandardComponentProperty, String> getPropertyInformationsMap() {
		return propertyInformationsMap;
	}
	/**
	 * Inserts a property bound to a string value.
	 * @param prop_id The property to bound
	 * @param prop_value The string value. Its meaning depends on the property itself.
	 */
	public void addPropertyInformations(ComponentFamily.StandardComponentProperty prop_id, String prop_value) {
		propertyInformationsMap.put(prop_id, prop_value);
	}
	public final Component getBoundComponent() {
		return boundComponent;
	}
	public final void setBoundComponent(Component bound_component) {
		this.boundComponent = bound_component;
	}
}
