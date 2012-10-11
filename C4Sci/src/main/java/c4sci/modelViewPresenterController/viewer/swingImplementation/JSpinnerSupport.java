package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.util.Map;

import javax.swing.JSpinner;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily.StandardComponentProperty;

public class JSpinnerSupport extends JComponentSupport {

	private JSpinner	spinnerComponent;
	
	public JSpinnerSupport(JSpinner swing_component, Component supported_component, 
			Map<StandardComponentProperty, String> prop_values_map) {
		super(swing_component, supported_component, prop_values_map);
		spinnerComponent = swing_component;
	}

}
