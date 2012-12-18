package c4sci.modelViewPresenterController.viewer.swingImplementation;

import javax.swing.JButton;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

/**
 * Support for the {@link JButton} class 
 * @author jeanmarc.deniel
 *
 */
public class JButtonSupport extends JComponentSupport implements TextShowerComponentInterface, 
	IntegerBoundComponentInterface, FloatBoundComponentInterface, 
	BooleanBoundComponentInterface {

	private JButton	textSupportComponent;
	
	public JButtonSupport(JButton abs_button, Component supported_comp) {
		super(abs_button, supported_comp);
		textSupportComponent = abs_button;
	}

	public void setText(String text_value) {
		textSupportComponent.setText(text_value);
	}

	public void setBooleanValue(boolean bool_value) {
		setText(Boolean.toString(bool_value));
	}

	public void setFloatValue(float float_value) {
		setText(Float.toString(float_value));
	}

	public void setIntegerValue(int int_value) {
		setText(Integer.toString(int_value));
	}


}
