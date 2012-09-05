package c4sci.modelViewPresenterController.viewerPresenterInterface;
/**
 * This factory creates components.
 * 
 * @author jeanmarc.deniel
 *
 */
public interface ComponentFactory {
	public enum StandardComponentSet{
		BUTTON,
		CHECKBOX,
		COMBO_BOX,
		LABEL,
		MENU,
		MENU_BAR,
		MENU_ITEM,
		SLIDER,
		SPINNER,
		TOOLBAR,
		TREE
	};
	/**
	 * Creates a Component among the standard set.
	 * @param comp_type The type of the component.
	 * @return A Component of comp_type type. 
	 * @throws CannotCreateSuchComponentException if the factory can't instantiate the asked component.
	 */
	Component createStandardComponent(StandardComponentSet comp_type) throws CannotCreateSuchComponentException;
	/**
	 * 
	 * @param comp_type the component type, as defined in the factory documentation.
	 * @return A Component of comp_type type.
	 * @throws CannotCreateSuchComponentException if the factory can't instantiate the asked component.
	 */
	Component createSpecialComponent(int comp_type) throws CannotCreateSuchComponentException;
}
