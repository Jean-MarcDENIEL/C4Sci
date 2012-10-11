package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.viewer.Viewer;
import c4sci.modelViewPresenterController.viewerPresenterInterface.CannotCreateSuchComponentException;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.creationChanges.CreateSpecialComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.creationChanges.CreateStandardComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.SpecialFeatureChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.ActivityChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BackgroundColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.DescriptionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FocusOrderChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FontColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FontSizeChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FontStyleChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FontTypeChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.ForegroundColorChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.LabelChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.NameChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.PositionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.SizeChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.TransparencyChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.VisibilityChange;
/**
 * This class is a viewer implementing {@link Component} through {@link SwingViewer} components.
 * @author jeanmarc.deniel
 *
 */
public class SwingViewer extends Viewer {

	private Map<DataIdentity, JComponentSupport> 			identitySwingComponentMap;
	//
	// JComponents that can be toggled :
	// e.g. JToggleButton (->JCheckBox / ->JRadioButton)
	//
	private Map<DataIdentity, BooleanBoundComponentInterface>	identityBooleanBoundComponentMap;		
	//
	// JComponents bound to a floating value
	//
	private Map<DataIdentity, FloatBoundComponentInterface>		identityFloatBoundComponentMap;
	//
	// JComponents bound to an integer value
	private Map<DataIdentity, IntegerBoundComponentInterface>	identityIntegerBoundComponentMap;
	// 
	// Components showing a text inside. E.g 
	//		JButton, 
	//		JMenuItem (->JcheckBoxMenuItem, ->JMenu, ->JRadioButtonMenuItem), 
	//		JToggleButton (->JCheckBox, JRadiobutton)
	//
	private Map<DataIdentity, TextShowerComponentInterface>		identityTextShowerComponentMap;	
	
	private JPanel							currentPanel;
	
	private final JPanel getCurrentPanel() {
		return currentPanel;
	}
	private final void setCurrentPanel(JPanel currentPanel) {
		this.currentPanel = currentPanel;
	}
	
	public SwingViewer() {
		identitySwingComponentMap 			= new ConcurrentHashMap<DataIdentity, JComponentSupport>();
		identityBooleanBoundComponentMap	= new ConcurrentHashMap<DataIdentity, BooleanBoundComponentInterface>();
		identityFloatBoundComponentMap		= new ConcurrentHashMap<DataIdentity, FloatBoundComponentInterface>();
		identityIntegerBoundComponentMap	= new ConcurrentHashMap<DataIdentity, IntegerBoundComponentInterface>();
		identityTextShowerComponentMap		= new ConcurrentHashMap<DataIdentity, TextShowerComponentInterface>();
		
		setCurrentPanel(null);
	}
	
	@Override
	protected void treatUnableToCreateStandardComponent(CreateStandardComponentChange comp_chg) {
		setMainPaneTitle("unable to create standard component : "+ comp_chg.getChange().getComponentName());
	}

	@Override
	protected void treatUnableToCreateSpecialComponent(CreateSpecialComponentChange comp_chg) {
		setMainPaneTitle("unable to create special component : " + comp_chg.getChange());
	}
	
	private void setMainPaneTitle(String new_title){
		if (getCurrentPanel() != null){
			getCurrentPanel().getRootPane().setName(new_title);
		}
	}

	private void invokeSwing(Runnable feedback_runnable, String feedback_name){

		try{
			SwingUtilities.invokeAndWait(feedback_runnable);
		} catch (InvocationTargetException _e) {
			setMainPaneTitle(feedback_name + " InvocationTargetException");
		} catch (InterruptedException _e) {
			setMainPaneTitle(feedback_name + " InterruptedException");
		}

	}
	
	@Override
	protected void feedbackToActivityChange(final ActivityChange comp_chg) {
		final JComponentSupport _jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp != null){
			invokeSwing(new Runnable() {public void run() {_jcomp.setEnabled(comp_chg.getChange());}}, "ActivityChange");
		}
	}

	@Override
	protected void feedbackToVisibilityChange(final VisibilityChange comp_chg) {
		final JComponentSupport _jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp != null){
			invokeSwing(new Runnable() {public void run() {_jcomp.setVisible(comp_chg.getChange());}}, "VisibilityChange");
		}

	}

	@Override
	protected void feedbackToBooleanValueChange(final BooleanValueChange comp_chg) {
		final BooleanBoundComponentInterface _jtoggle = identityBooleanBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (_jtoggle != null){
			invokeSwing(new Runnable() {public void run() {_jtoggle.setBooleanValue(comp_chg.getChange());}}, "BooleanValueChange");
		}
	}

	@Override
	protected void feedbackToFloatValueChange(final FloatValueChange comp_chg) {
		final FloatBoundComponentInterface _jfloat_comp = identityFloatBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (_jfloat_comp != null){
			invokeSwing(new Runnable() {public void run() {_jfloat_comp.setFloatValue(comp_chg.getChange());}}, "FloatValueChange");
		}
	}

	@Override
	protected void feedbackToIntegerValueChange(final IntegerValueChange comp_chg) {
		final IntegerBoundComponentInterface _jinteger_comp = identityIntegerBoundComponentMap.get(comp_chg.getChange());
		if (_jinteger_comp != null){
			invokeSwing(new Runnable() {public void run() {_jinteger_comp.setIntegerValue(comp_chg.getChange());}}, "IntgerValueChange");
		}
	}

	@Override
	protected void feedbackToFocusOrderChange(final FocusOrderChange comp_chg) {
		final JComponentSupport _jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp != null){
			invokeSwing(new Runnable() {public void run() {/* DO NOTHING*/}}, "FocusOrderChange");
		}
	}

	@Override
	protected void feedbackToSpecialFeatureChange(SpecialFeatureChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToTransparenceyChange(final TransparencyChange comp_chg) {
		final JComponentSupport _jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp != null){
			invokeSwing(new Runnable() {public void run() {_jcomp.setTransparency(comp_chg.getChange());}}, "TransparencyChange");
		}
	}

	@Override
	protected void feedbackToFontSizeChange(final FontSizeChange comp_chg) {
		final JComponentSupport _jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp != null){
			invokeSwing(new Runnable() {public void run() {_jcomp.setFontSize((int)comp_chg.getChange());}}, "FontSizeChange");
		}
	}

	@Override
	protected void feedbackToPositionChange(PositionChange comp_chg) {
		// TODO Auto-generated method stub
		Component _comp = comp_chg.getBoundComponent();
		Component _parent = _comp.getParentComponent();
		int _x, _y;
		if (_parent != null){
			JComponentSupport _jparent = identitySwingComponentMap.get(_parent.getIdentity());
			if (_jparent != null){
				// TODO
				
			}
			else{
				// TODO 
				
			}
		}
		else{
			// TODO
			
		}

	}

	@Override
	protected void feedbackToSizeChange(SizeChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToBackgroundColorChange(BackgroundColorChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToForegroundColorChange(ForegroundColorChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToFontColorChange(FontColorChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToNameChange(NameChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToDescriptionChange(DescriptionChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToLabelChange(LabelChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToFontTypeChange(FontTypeChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void feedbackToFontStyleChange(FontStyleChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Component treatFeedbackCreateStandardComponent(CreateStandardComponentChange comp_chg) throws CannotCreateSuchComponentException {

		JComponentSupport _res = null;
		Map<ComponentFamily.StandardComponentProperty, String> prop_value_map = comp_chg.getPropertyInformationsMap();
		switch (comp_chg.getChange()){
		case PANEL : 
			JPanel _jpane = new JPanel();
			_res = new JComponentSupport(_jpane, comp_chg.getBoundComponent(), prop_value_map);
			setCurrentPanel(_jpane);
			break;
		case BUTTON :
			_res = new JButtonSupport(new JButton(), comp_chg.getBoundComponent(), prop_value_map);
			break;
		case CHECKBOX :
			JToggleButton _jtoggle = new JCheckBox();
			_res = new JToggleButtonSupport(_jtoggle, comp_chg.getBoundComponent(), prop_value_map);
			break;
			//case COMBO_BOX :
			//return new SwingSingleComponent(new JComboBox<E>);
			//break;
		case LABEL :
			JLabel _jlabel = new JLabel();
			_res = new JLabelSupport(_jlabel, comp_chg.getBoundComponent(), prop_value_map);
			break;
		case MENU :
			JMenu _jmenu = new JMenu();
			_res = new JMenuItemSupport(_jmenu, comp_chg.getBoundComponent(), prop_value_map);
			break;
		case MENU_BAR :
			_res = new JComponentSupport(new JMenuBar(), comp_chg.getBoundComponent(), prop_value_map);
			break;
		case MENU_ITEM :
			JMenuItem _jmenu_item = new JMenuItem();
			_res = new JMenuItemSupport(_jmenu_item, comp_chg.getBoundComponent(), prop_value_map);
			break;
		case SLIDER :
			JSlider _jslider = new JSlider();
			//identity
			_res = new JSliderSupport(_jslider, comp_chg.getBoundComponent(), prop_value_map);
			break;
		case SPINNER :
			JSpinner _jspinner = new JSpinner();
			_res = new JSpinnerSupport(_jspinner, comp_chg.getBoundComponent(), prop_value_map);
			break;
		case TOOLBAR :
			_res = new JComponentSupport(new JToolBar(), comp_chg.getBoundComponent(), prop_value_map);
			break;
		case TREE :
			_res = new JComponentSupport(new JTree(), comp_chg.getBoundComponent(), prop_value_map);
			break;
		default:
			throw new CannotCreateSuchComponentException("Standard Component");
		}

		// TO BE CONTINUED : use class.getInterfaces to put _res in corresponding  maps.
		identitySwingComponentMap.put(comp_chg.getComponentIdentity(), _res.getSwingComponent());
		Class[] _interface_tab = _res.getClass().getInterfaces();
		/*for (Class _interf : _interface_tab){
			if (_interf == )
		}*/
		// TODO TO BE CONTINUED

		if (getCurrentPanel() != _res.getSwingComponent()){
			getCurrentPanel().add(_res.getSwingComponent());
		}

		return _res;
	}

	@Override
	protected Component treatFeedbackCreateSpecialComponent(
			CreateSpecialComponentChange comp_chg) throws CannotCreateSuchComponentException {
		JComponentSupport _res = null;
		// TODO METHOD TO BE CONTINUED
		
		if (getCurrentPanel() != null){
			getCurrentPanel().add(_res.getSwingComponent());
		}
		return _res;
	}

}
