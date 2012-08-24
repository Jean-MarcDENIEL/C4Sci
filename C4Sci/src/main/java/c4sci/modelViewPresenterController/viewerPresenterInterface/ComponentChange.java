package c4sci.modelViewPresenterController.viewerPresenterInterface;

import c4sci.data.DataIdentity;

/**
 * This interface represents the update commands that can be thrown to Component, like : set foreground, set an indicator ....<br>
 * <br> 
 * ComponentChange requests are usually used for special cases that are not treated by Component 
 * methods like {@link Component#setActivity(boolean) setActivity()}
 * @author jeanmarc.deniel
 * @see Component
 *
 */
public abstract class ComponentChange {
	/**
	 * This enumerates the possible change cases.<br>
	 * The meaning of {@link ComponentChange#getComponentID()} depends on the enum value :<br>
	 * <ul>
	 * <li>SET_... : {@link ComponentChange#getComponentID()} indicates the modified Component</li>
	 * <li>CREATE_... : {@link ComponentChange#getComponentID()} is the identity of the component to create</li>
	 * <li>RECEIVED_... : {@link ComponentChange#getComponentID()} is the identity of the user accessed Component</li>
	 * </ul>
	 */
	public enum ChangeID { 
		SET_FOREGROUND_COLOR,
		SET_BACKGROUND_COLOR,
		SET_TRANSPARENCY,
		SET_SIZE,
		SET_POSITION,
		SET_FONT_TYPE,
		SET_FONT_SIZE,
		SET_FONT_COLOR,
		SET_FONT_STYLE,
		SET_FOCUS_ORDER,
		SET_VISIBILITY,
		SET_ACTIVITY,
		SET_LABEL,
		SET_NAME,
		SET_DESCRIPTION,
		SET_SPECIAL_FEATURE,
		
		CREATE_STANDARD_COMPONENT,
		CREATE_SPECIAL_COMPONENT,
		
		RECEIVED_MOUSE_LEFT_BUTTON_PRESSED,
		RECEIVED_MOUSE_LEFT_BUTTON_RELEASED,
		RECEIVED_MOUSE_RIGHT_BUTTON_PRESSED,
		RECEIVED_MOUSE_RIGHT_BUTTON_RELEASED,
		RECEIVED_MOUSE_MOVE,
		RECEIVED_FOCUS_GAIN,
		RECEIVED_FOCUS_LOSS,
		RECEIVED_MODIFIED_CONTENT,
	};
	
	private DataIdentity componentIdentity;
	
	@SuppressWarnings("unused")
	private ComponentChange() {}
	
	public ComponentChange(DataIdentity comp_id){
		componentIdentity = comp_id;
	}
	
	/**
	 * 
	 * @return the type of the asked change
	 * @see ChangeID
	 */
	public abstract ChangeID getChangeID();
	/**
	 * 
	 * @return	The identity of the Component asked to change.
	 */
	public DataIdentity	getComponentID(){
		return componentIdentity;
	}
}
