package c4sci.modelViewPresenterController.viewerPresenterInterface;

/**
 * This class describes components that can be created by Viewers on Presenters demands,
 * and the properties that can be associated to a creation or modification {@link Command}.
 * 
 * @author jeanmarc.deniel
 *
 */
public interface ComponentFamily {
	public enum StandardComponentSet{
		PANEL ("Panel"),
		/**
		 * Modified by {@link LabelChange}, {@link FloatChange}, {@link IntegerChange}
		 */
		BUTTON ("Button"),
		/**
		 * Modified by {@link BooleanChange}
		 */
		CHECKBOX ("Checkbox"),
		COMBO_BOX ("ComboBox"),
		/**
		 * Modified by {@link LabelChange}, {@link FloatChange}, {@link IntegerChange}
		 */
		LABEL ("Label"),
		/**
		 * modified by {@link LabelChange}
		 */
		MENU ("Menu"),
		MENU_BAR ("Menu bar"),
		/**
		 * Modified by {@link LabelChange}
		 */
		MENU_ITEM ("Menu item"),
		/**
		 * Modified by {@link IntegerChange}
		 */
		SLIDER ("Slider"),
		SPINNER ("Spinner"),
		TOOLBAR ("Toolbar"),
		TREE ("Tree"),
		INVISIBLE_CONTAINER ("Invisible container"),
		TEXT_FIELD ("Text field");
		
		private String componentName;

		StandardComponentSet(String comp_name){
			setComponentName(comp_name);
		}
		public final String getComponentName() {
			return componentName;
		}
		public final void setComponentName(String component_name) {
			this.componentName = component_name;
		}
		
	};

	public enum StandardComponentProperty {
		
		MIN_VALUE("MinValue"),
		MAX_VALUE("MaxValue");
		
		private String propertyName;
		StandardComponentProperty(String prop_name){
			setPropertyName(prop_name);
		}
		public final String getPropertyName() {
			return propertyName;
		}
		public final void setPropertyName(String prop_name) {
			this.propertyName = prop_name;
		}
	}
	
}
