package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.util.Map;

import javax.swing.JPanel;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily.StandardComponentProperty;

public class JPanelSupport extends JComponentSupport implements
		TextShowerComponentInterface {

	private JPanel			jpanelComponent;
	private JPanelSupport 	parentPanel;
	
	public JPanelSupport(JPanel swing_component, JPanelSupport parent_panel, Component supported_component,	Map<StandardComponentProperty, String> prop_values_map) {
		super(swing_component, supported_component, prop_values_map);
		jpanelComponent = swing_component;
		parentPanel		= parent_panel;
	}

	public void setText(String text_value) {
		jpanelComponent.setName(text_value);
	}
	/**
	 * 
	 * @return the parent panel or <i>null</i> if there's no.
	 */
	public JPanelSupport getParentPanel(){
		return parentPanel;
	}

}
