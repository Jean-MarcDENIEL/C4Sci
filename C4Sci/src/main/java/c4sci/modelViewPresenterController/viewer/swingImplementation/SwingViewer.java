package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.viewer.ComponentSupport;
import c4sci.modelViewPresenterController.viewer.Viewer;
import c4sci.modelViewPresenterController.viewerPresenterInterface.CannotCreateSuchComponentException;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
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

	private JPanelSupport										currentPanel;

	private final JPanelSupport getCurrentPanel() {
		return currentPanel;
	}
	private final void setCurrentPanel(JPanelSupport current_panel) {
		this.currentPanel = current_panel;
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
	@Override
	protected void treatUnableToProcessCommand(ComponentChange comp_chg) {
		setMainPaneTitle("unable to process component change "  + comp_chg.getClass().getName());
	}

	private void setMainPaneTitle(final String new_title){
		final JPanelSupport _jpanel = getCurrentPanel();
		invokeSwing(new Runnable() {
			public void run() {
				if (_jpanel != null){
					_jpanel.getSwingComponent().getRootPane().setName(new_title);
				}
			}
		}, "SetMainTitle : "+new_title);
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
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToVisibilityChange(final VisibilityChange comp_chg) {
		final JComponentSupport _jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp != null){
			invokeSwing(new Runnable() {public void run() {_jcomp.setVisible(comp_chg.getChange());}}, "VisibilityChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}

	}

	@Override
	protected void feedbackToBooleanValueChange(final BooleanValueChange comp_chg) {
		final BooleanBoundComponentInterface _jtoggle = identityBooleanBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (_jtoggle != null){
			invokeSwing(new Runnable() {public void run() {_jtoggle.setBooleanValue(comp_chg.getChange());}}, "BooleanValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToFloatValueChange(final FloatValueChange comp_chg) {
		final FloatBoundComponentInterface _jfloat_comp = identityFloatBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (_jfloat_comp != null){
			invokeSwing(new Runnable() {public void run() {_jfloat_comp.setFloatValue(comp_chg.getChange());}}, "FloatValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToIntegerValueChange(final IntegerValueChange comp_chg) {
		final IntegerBoundComponentInterface _jinteger_comp = identityIntegerBoundComponentMap.get(comp_chg.getChange());
		if (_jinteger_comp != null){
			invokeSwing(new Runnable() {public void run() {_jinteger_comp.setIntegerValue(comp_chg.getChange());}}, "IntgerValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToFocusOrderChange(final FocusOrderChange comp_chg) {
		final JComponentSupport _jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp != null){
			invokeSwing(new Runnable() {public void run() {/* DO NOTHING*/}}, "FocusOrderChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
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
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToFontSizeChange(final FontSizeChange comp_chg) {
		final JComponentSupport _jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp != null){
			invokeSwing(new Runnable() {public void run() {_jcomp.setFontSize((int)comp_chg.getChange());}}, "FontSizeChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToPositionChange(PositionChange comp_chg) {
		Component _comp = comp_chg.getBoundComponent();
		Component _parent = _comp.getParentComponent();
		final JComponentSupport _jcomp_support = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp_support != null){
			final int _x, _y;
			if (_parent != null){
				JComponentSupport _jparent = identitySwingComponentMap.get(_parent.getIdentity());
				if (_jparent != null){
					Dimension _parent_dim = _jparent.getSize();
					_x = (int) (((float)(_parent_dim.width)) * comp_chg.getChange().getX());
					_y = (int) (((float)(_parent_dim.height)) * comp_chg.getChange().getY());
				}
				else{
					_x = (int) (((float)(_jcomp_support.getSwingComponent().getRootPane().getWidth())) *comp_chg.getChange().getX());
					_y = (int) (((float)(_jcomp_support.getSwingComponent().getRootPane().getHeight())) *comp_chg.getChange().getY());
				}
			}
			else{
				_x = (int) (((float)(_jcomp_support.getSwingComponent().getRootPane().getWidth())) *comp_chg.getChange().getX());
				_y = (int) (((float)(_jcomp_support.getSwingComponent().getRootPane().getHeight())) *comp_chg.getChange().getY());
			}
			invokeSwing(new Runnable() {public void run() {_jcomp_support.setLocation(new Point(_x,_y));}}, "PositionChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToSizeChange(SizeChange comp_chg) {
		final JComponentSupport _jcomp_support = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp_support != null){
			final Dimension _parent_dim; 
			Component	_parent;
			JComponentSupport	_jparent;
			
			if (((_parent = _jcomp_support.getSupportedComponent().getParentComponent()) != null)&&
					((_jparent = identitySwingComponentMap.get(_parent.getIdentity())) != null)) {
				_parent_dim = _jparent.getSize();
			}
			else{
				_parent_dim = _jcomp_support.getSwingComponent().getRootPane().getSize();
			}
			invokeSwing(new Runnable() {public void run() {_jcomp_support.setSize(_parent_dim);}}, "SizeChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToBackgroundColorChange(final BackgroundColorChange comp_chg) {
		final JComponentSupport _jcomp_support = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		SpaceVector _color = comp_chg.getChange();
		final float _color_tab[] = {_color.getX(), _color.getY(), _color.getZ(), 0.0f};
		if (_jcomp_support != null){
			invokeSwing(new Runnable() {public void run() {_jcomp_support.setBackground(_color_tab);}}, "BackgroundColorChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToForegroundColorChange(ForegroundColorChange comp_chg) {
		final JComponentSupport _jcomp_support = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		SpaceVector _color = comp_chg.getChange();
		final float _color_tab[] = {_color.getX(), _color.getY(), _color.getZ(), 0.0f};
		if (_jcomp_support != null){
			invokeSwing(new Runnable() {public void run() {_jcomp_support.setForeground(_color_tab);}}, "ForegroundColorChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToFontColorChange(FontColorChange comp_chg) {
		//
		// in fact font color is the foreground color
		//
		final JComponentSupport _jcomp_support = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		SpaceVector _color = comp_chg.getChange();
		final float _color_tab[] = {_color.getX(), _color.getY(), _color.getZ(), 0.0f};
		if (_jcomp_support != null){
			invokeSwing(new Runnable() {public void run() {_jcomp_support.setForeground(_color_tab);}}, "ForegroundColorChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToNameChange(final NameChange comp_chg) {
		final JComponentSupport _jcomp_support = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp_support != null){
			invokeSwing(new Runnable() {public void run() {
				_jcomp_support.setName(comp_chg.getChange().getDefaultValue());
				}}, "NameChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToDescriptionChange(final DescriptionChange comp_chg) {
		final JComponentSupport _jcomp_support = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jcomp_support != null){
			invokeSwing(new Runnable() {public void run() {_jcomp_support.setDescription(comp_chg.getChange().getDefaultValue());}}, "DescriptionChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToLabelChange(final LabelChange comp_chg) {
		final TextShowerComponentInterface _jtext_support = identityTextShowerComponentMap.get(comp_chg.getComponentIdentity());
		if (_jtext_support != null){
			invokeSwing(new Runnable() {public void run() {_jtext_support.setText(comp_chg.getChange().getDefaultValue());}}, "LabelChange");
		}
	}

	@Override
	protected void feedbackToFontTypeChange(final FontTypeChange comp_chg) {
		final JComponentSupport _jsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jsupport != null){
			invokeSwing(new Runnable() {public void run() {_jsupport.setFont(comp_chg.getChange());}}, "FontTypeChange");
		}
	}

	@Override
	protected void feedbackToFontStyleChange(final FontStyleChange comp_chg) {
		final JComponentSupport _jsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_jsupport != null){
			invokeSwing(new Runnable() {public void run() {_jsupport.setFontStyle(comp_chg.getChange());}}, "FontStyleChange");
		}
	}

	@Override
	protected ComponentSupport treatFeedbackCreateStandardComponent(CreateStandardComponentChange comp_chg) throws CannotCreateSuchComponentException {

		JComponentSupport _res = null;
		Map<ComponentFamily.StandardComponentProperty, String> prop_value_map = comp_chg.getPropertyInformationsMap();
		switch (comp_chg.getChange()){
		case PANEL : 
			JPanel _jpane = new JPanel();
			JPanelSupport _jpane_support = new JPanelSupport(_jpane, currentPanel, comp_chg.getBoundComponent(), prop_value_map);
			setCurrentPanel(_jpane_support);
			_res = _jpane_support;
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

		//  
		// _res is put in maps corresponding  to its class and those (incl. interfaces) it inherits from.
		//
		identitySwingComponentMap.put(comp_chg.getComponentIdentity(), _res);

		if (BooleanBoundComponentInterface.class.isInstance(_res)){
			identityBooleanBoundComponentMap.put(_res.getSupportedComponent().getIdentity(), (BooleanBoundComponentInterface)_res);
		}
		if (FloatBoundComponentInterface.class.isInstance(_res)){
			identityFloatBoundComponentMap.put(_res.getSupportedComponent().getIdentity(), (FloatBoundComponentInterface)_res);
		}
		if (IntegerBoundComponentInterface.class.isInstance(_res)){
			identityIntegerBoundComponentMap.put(_res.getSupportedComponent().getIdentity(), (IntegerBoundComponentInterface)_res);
		}
		if (TextShowerComponentInterface.class.isInstance(_res)){
			identityTextShowerComponentMap.put(_res.getSupportedComponent().getIdentity(), (TextShowerComponentInterface)_res);
		}

		//
		// _res swing component is added to its parent if there is one
		//
		JComponentSupport 	_jparent_support;
		Component			parent;
		if (((parent = comp_chg.getBoundComponent().getParentComponent()) != null) &&
				((_jparent_support = identitySwingComponentMap.get(parent.getIdentity())) != null)&&
				(_jparent_support != getCurrentPanel())){
			_jparent_support.getSwingComponent().add(_res.getSwingComponent());
		}else{
			//
			// _res is added to the current panel (if it's not itself the current panel)
			//
			if (getCurrentPanel() != _res){
				getCurrentPanel().getSwingComponent().add(_res.getSwingComponent());
			}
		}
		return _res;
	}

	@Override
	protected ComponentSupport treatFeedbackCreateSpecialComponent(
			CreateSpecialComponentChange comp_chg) throws CannotCreateSuchComponentException {
		JComponentSupport _res = null;
		// TODO METHOD TO BE CONTINUED

		// TODO
		/*
		if (getCurrentPanel() != null){
			getCurrentPanel().add(_res.getSwingComponent());
		}*/

		return _res;
	}


}
