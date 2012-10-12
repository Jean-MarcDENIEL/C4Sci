package c4sci.modelViewPresenterController.viewer;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
/**
 * This class is a facade for system visual components.<br>
 * <br>
 * <b>Pattern:</b> This class implements the <b>facade</b> GoF pattern.
 * @author jeanmarc.deniel
 *
 */
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
