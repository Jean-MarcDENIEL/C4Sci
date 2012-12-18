package c4sci.modelViewPresenterController.viewer.swingImplementation;

import javax.swing.JSpinner;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

public class JSpinnerSupport extends JComponentSupport {

	private JSpinner	spinnerComponent;
	
	public JSpinnerSupport(JSpinner swing_component, Component supported_component) {
		super(swing_component, supported_component);
		setSpinnerComponent(swing_component);
	}

	public final JSpinner getSpinnerComponent() {
		return spinnerComponent;
	}

	public final void setSpinnerComponent(JSpinner spinner_component) {
		this.spinnerComponent = spinner_component;
	}

}
