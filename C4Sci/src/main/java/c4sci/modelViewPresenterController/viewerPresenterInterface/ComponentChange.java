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
	/**
	 * This enumerates the possible change cases.<br>
	 * The meaning of {@link ComponentChange#getComponentIdentity()} depends on the enum value :<br>
	 * <ul>
	 * <li>SET_... : {@link ComponentChange#getComponentIdentity()} indicates the modified Component</li>
	 * <li>CREATE_... : {@link ComponentChange#getComponentIdentity()} is the identity of the component to create</li>
	 * <li>RECEIVED_... : {@link ComponentChange#getComponentIdentity()} is the identity of the user accessed Component</li>
	 * </ul>
	 */
	public enum ChangeID {
		
		SET_FOREGROUND_COLOR(1),
		SET_BACKGROUND_COLOR(2),
		SET_TRANSPARENCY(3),
		SET_SIZE(4),
		SET_POSITION(5),
		SET_FONT_TYPE(6),
		SET_FONT_SIZE(7),
		SET_FONT_COLOR(8),
		SET_FONT_STYLE(9),
		SET_FOCUS_ORDER(10),
		SET_VISIBILITY(11),
		SET_ACTIVITY(12),
		SET_LABEL(13),
		SET_NAME(14),
		SET_DESCRIPTION(15),
		SET_SPECIAL_FEATURE(16),
		CREATE_STANDARD_COMPONENT(17),
		CREATE_SPECIAL_COMPONENT(18),
		RECEIVED_MOUSE_LEFT_BUTTON_PRESSED(19),
		RECEIVED_MOUSE_LEFT_BUTTON_RELEASED(20),
		RECEIVED_MOUSE_RIGHT_BUTTON_PRESSED(21),
		RECEIVED_MOUSE_RIGHT_BUTTON_RELEASED(22),
		RECEIVED_MOUSE_MOVE(23),
		RECEIVED_FOCUS_GAIN(24),
		RECEIVED_FOCUS_LOSS(25),
		RECEIVED_MODIFIED_CONTENT(26),
		SET_INTEGER_VALUE(27),
		SET_BOOLEAN_VALUE(28),
		SET_FLOAT_VALUE(29);
		
		private long changeValue;
		ChangeID(long change_value){changeValue = change_value;}
		public long getChangeValue(){return changeValue;}
	};
	private DataIdentity componentIdentity;
	private ComponentChange() {super(null);}
	public ComponentChange(DataIdentity comp_id, Command parent_command){
		super(parent_command);
		componentIdentity = comp_id;
		setCommandID(getCommandID());
	}
	/**
	 * @return the type of the asked change
	 * @see ChangeID
	 */
	public abstract ChangeID getChangeID();
	/**
	 * @return	The identity of the Component asked to change.
	 */
	public DataIdentity	getComponentIdentity(){
		return componentIdentity;
	}
}
