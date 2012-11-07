package c4sci.modelViewPresenterController.viewer.swingImplementation;

import javax.swing.JLabel;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

/**
 * This class support {@link JLabel} and its subclasses.
 * @author jeanmarc.deniel
 *
 */
public class JLabelSupport extends JComponentSupport implements IntegerBoundComponentInterface,
		FloatBoundComponentInterface, BooleanBoundComponentInterface,
		TextShowerComponentInterface {

	private JLabel jlabelComponent;
	
	public JLabelSupport(JLabel jlabel_comp, Component supported_comp) {
		super(jlabel_comp, supported_comp);
		jlabelComponent = jlabel_comp;
	}

	public void setText(String text_value) {
		jlabelComponent.setText(text_value);
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
