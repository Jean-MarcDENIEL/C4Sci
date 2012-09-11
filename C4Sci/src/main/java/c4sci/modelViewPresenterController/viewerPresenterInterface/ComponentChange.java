package c4sci.modelViewPresenterController.viewerPresenterInterface;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;

/**
 * This interface represents the update commands that can be thrown to Component, like : set foreground, set an indicator ....<br>
 * <br> 
 * ComponentChange requests are usually used for special cases that are not treated by {@link Component} 
 * methods.
 * @author jeanmarc.deniel
 * @see Component
 *
 */
public abstract class ComponentChange extends Command{

	private DataIdentity componentIdentity;
	private ComponentChange() {super(null);}
	public ComponentChange(DataIdentity comp_id, Command parent_command){
		super(parent_command);
		componentIdentity = comp_id;
	}

	/**
	 * @return	The identity of the Component asked to change.
	 */
	public DataIdentity	getComponentIdentity(){
		return componentIdentity;
	}
}
