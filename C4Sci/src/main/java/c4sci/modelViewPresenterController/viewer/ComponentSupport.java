package c4sci.modelViewPresenterController.viewer;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

public class ComponentSupport {

	private Component	supportedComponent;
	
	public ComponentSupport(Component comp_) {
		setSupportedComponent(comp_);
	}

	public final Component getSupportedComponent() {
		return supportedComponent;
	}

	public final void setSupportedComponent(Component supported_component) {
		this.supportedComponent = supported_component;
	}

}
