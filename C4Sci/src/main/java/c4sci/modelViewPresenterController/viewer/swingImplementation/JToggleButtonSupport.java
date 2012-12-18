package c4sci.modelViewPresenterController.viewer.swingImplementation;

import javax.swing.JToggleButton;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

/**
 * This class is useful to support {@link JCheckBox} and {@link JRadioButton} classes 
 * @author jeanmarc.deniel
 *
 */
public class JToggleButtonSupport extends JComponentSupport implements BooleanBoundComponentInterface, 
	TextShowerComponentInterface {

	private JToggleButton toggleComponent;
	
	public JToggleButtonSupport(JToggleButton toggle_button, Component supported_component) {
		super(toggle_button, supported_component);
		toggleComponent = toggle_button;
	}

	public void setBooleanValue(boolean bool_value) {
		toggleComponent.setSelected(bool_value);
	}

	public void setText(String text_value) {
		toggleComponent.setText(text_value);
	}

}
