package c4sci.modelViewPresenterController.viewer.swingImplementation;

import javax.swing.JPanel;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

public class JPanelSupport extends JComponentSupport implements
		TextShowerComponentInterface {

	private JPanel			jpanelComponent;
	private JPanelSupport 	parentPanel;
	
	public JPanelSupport(JPanel swing_component, JPanelSupport parent_panel, Component supported_component) {
		super(swing_component, supported_component);
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
