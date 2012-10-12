package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily;

/**
 * This class is useful to support {@link JCheckBox} and {@link JRadioButton} classes 
 * @author jeanmarc.deniel
 *
 */
public class JToggleButtonSupport extends JComponentSupport implements BooleanBoundComponentInterface, 
	TextShowerComponentInterface {

	JToggleButton toggleComponent;
	
	public JToggleButtonSupport(JToggleButton toggle_button, Component supported_component, Map<ComponentFamily.StandardComponentProperty, String> prop_map) {
		super(toggle_button, supported_component, prop_map);
		toggleComponent = toggle_button;
	}

	public void setBooleanValue(boolean bool_value) {
		toggleComponent.setSelected(bool_value);
	}

	public void setText(String text_value) {
		toggleComponent.setText(text_value);
	}

}
