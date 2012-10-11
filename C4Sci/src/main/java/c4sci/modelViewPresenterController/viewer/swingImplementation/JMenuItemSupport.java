package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.util.Map;

import javax.swing.JMenuItem;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily;

public class JMenuItemSupport extends JComponentSupport implements BooleanBoundComponentInterface,
		TextShowerComponentInterface {

	private JMenuItem 	jmenuComponent;
	
	public JMenuItemSupport(JMenuItem jmenu_comp, Component supported_comp, Map<ComponentFamily.StandardComponentProperty, String> prop_map) {
		super(jmenu_comp, supported_comp, prop_map);
	}

	public void setText(String text_value) {
		jmenuComponent.setText(text_value);
	}

	public void setBooleanValue(boolean bool_value) {
		jmenuComponent.setEnabled(bool_value);
	}

}
