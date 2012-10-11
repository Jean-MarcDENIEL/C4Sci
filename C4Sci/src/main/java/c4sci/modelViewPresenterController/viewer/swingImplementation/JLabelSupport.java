package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.util.Map;

import javax.swing.JLabel;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily;

/**
 * This class support {@link JLabel} and its subclasses.
 * @author jeanmarc.deniel
 *
 */
public class JLabelSupport extends JComponentSupport implements IntegerBoundComponentInterface,
		FloatBoundComponentInterface, BooleanBoundComponentInterface,
		TextShowerComponentInterface {

	private JLabel jlabelComponent;
	
	public JLabelSupport(JLabel jlabel_comp, Component supported_comp, Map<ComponentFamily.StandardComponentProperty, String> prop_map) {
		super(jlabel_comp, supported_comp, prop_map);
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
