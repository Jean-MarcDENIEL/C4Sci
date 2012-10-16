package c4sci.modelViewPresenterController.viewer.swingImplementation;

import javax.swing.JMenuItem;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

public class JMenuItemSupport extends JComponentSupport implements BooleanBoundComponentInterface,
		TextShowerComponentInterface {

	private JMenuItem 	jmenuComponent;
	
	public JMenuItemSupport(JMenuItem jmenu_comp, Component supported_comp) {
		super(jmenu_comp, supported_comp);
		jmenuComponent = jmenu_comp;
	}

	public final void setText(String text_value) {
		jmenuComponent.setText(text_value);
	}

	public final void setBooleanValue(boolean bool_value) {
		jmenuComponent.setEnabled(bool_value);
	}

}
