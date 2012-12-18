package c4sci.modelViewPresenterController.viewer.swingImplementation;

import javax.swing.JTextField;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

public class JTextFieldSupport extends JComponentSupport implements FloatBoundComponentInterface, IntegerBoundComponentInterface, TextShowerComponentInterface {

	private JTextField	jTextFieldComponent;
	
	public JTextFieldSupport(JTextField swing_component, Component supported_component) {
		super(swing_component, supported_component);
		jTextFieldComponent = swing_component;
	}

	public void setText(String text_value) {
		jTextFieldComponent.setText(text_value);
		
	}

	public void setIntegerValue(int int_value) {
		setText(Integer.toString(int_value));
	}

	public void setFloatValue(float float_value) {
		setText(Float.toString(float_value));
	}

}
