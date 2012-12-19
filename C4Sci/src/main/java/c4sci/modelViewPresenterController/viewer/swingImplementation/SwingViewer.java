package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.viewer.ComponentSupport;
import c4sci.modelViewPresenterController.viewer.Viewer;
import c4sci.modelViewPresenterController.viewerPresenterInterface.CannotCreateSuchComponentException;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.SpecialFeatureChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.CreateSpecialComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.CreateStandardComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.lifeCycleChanges.SuppressComponentChange;
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
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.StringValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.TransparencyChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.VisibilityChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.sessionChanges.BeginSessionChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.sessionChanges.EndSessionChange;
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

	private boolean												sessionChangeOpened;

	private final JFrame										windowFrame;

	private static final int BASE_SIZE = 500;

	private JPanelSupport getCurrentPanel() {
		return currentPanel;
	}
	private void setCurrentPanel(JPanelSupport current_panel) {

		final JPanel _J_PANEL = (JPanel) current_panel.getSwingComponent();

		invokeSwing(new Runnable() {public void run() {
			_J_PANEL.setLayout(null);
			_J_PANEL.setBackground(Color.red);
			_J_PANEL.setSize(windowFrame.getSize());
			_J_PANEL.setVisible(true);
			windowFrame.add(_J_PANEL);
		}}, "setCurrentPanel");

		currentPanel = current_panel;
	}

	public SwingViewer() {
		identitySwingComponentMap 			= new ConcurrentHashMap<DataIdentity, JComponentSupport>();
		identityBooleanBoundComponentMap	= new ConcurrentHashMap<DataIdentity, BooleanBoundComponentInterface>();
		identityFloatBoundComponentMap		= new ConcurrentHashMap<DataIdentity, FloatBoundComponentInterface>();
		identityIntegerBoundComponentMap	= new ConcurrentHashMap<DataIdentity, IntegerBoundComponentInterface>();
		identityTextShowerComponentMap		= new ConcurrentHashMap<DataIdentity, TextShowerComponentInterface>();

		currentPanel						= null;

		sessionChangeOpened					= false;

		windowFrame							= new JFrame();
		invokeSwing(new Runnable() {
			public void run() {
				windowFrame.setSize(BASE_SIZE, BASE_SIZE);
				windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				windowFrame.setVisible(true);
			}}, "SwingViewer Constructor");

	}

	@Override
	public void treatUnableToCreateStandardComponent(CreateStandardComponentChange comp_chg) {
		setMainPaneTitle("unable to create standard component : "+ comp_chg.getChange().getComponentName());
	}
	@Override
	public void treatUnableToCreateSpecialComponent(CreateSpecialComponentChange comp_chg) {
		setMainPaneTitle("unable to create special component : " + comp_chg.getChange());
	}
	@Override
	public void treatUnableToProcessCommand(ComponentChange comp_chg, String compl_msg) {
		String _compl_msg_tmp = compl_msg;
		if (compl_msg == null){
			_compl_msg_tmp = "";
		}
		setMainPaneTitle("unable to process component change "  + comp_chg.getClass().getName()+" : "+_compl_msg_tmp);
	}
	@Override
	public void treatUnableToProcessCommand(ComponentChange comp_chg){
		treatUnableToProcessCommand(comp_chg, null);
	}

	private void setMainPaneTitle(final String new_title){
		invokeSwing(new Runnable() {
			public void run() {
				windowFrame.setTitle(new_title);
			}
		}, "SetMainTitle : "+new_title);
	}

	/**
	 * Launches swing operations in a thread safe way.<br>
	 * In the case the {@link #sessionChangeOpened} == false, forces a paint of the current panel.
	 * 
	 * @param feedback_runnable
	 * @param feedback_name
	 */
	private void invokeSwing(Runnable feedback_runnable, String feedback_name){

		// DEBUG
		//System.out.println("        invoke swing : "+ feedback_name);
		try{
			SwingUtilities.invokeAndWait(feedback_runnable);
			if (!sessionChangeOpened){
				SwingUtilities.invokeAndWait(new Runnable() {public void run() {windowFrame.repaint();}});
			}
		} catch (InvocationTargetException _e) {
			setMainPaneTitle(feedback_name + " InvocationTargetException");
		} catch (InterruptedException _e) {
			setMainPaneTitle(feedback_name + " InterruptedException");
		}

	}


	@Override
	public void feedbackToActivityChange(final ActivityChange comp_chg) {
		final JComponentSupport _J_COMP = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP != null){
			invokeSwing(new Runnable() {public void run() {_J_COMP.setEnabled(comp_chg.getChange());}}, "ActivityChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToVisibilityChange(final VisibilityChange comp_chg) {
		final JComponentSupport _J_COMP = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP != null){
			invokeSwing(new Runnable() {public void run() {_J_COMP.setVisible(comp_chg.getChange());}}, "VisibilityChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}

	}

	@Override
	public void feedbackToBooleanValueChange(final BooleanValueChange comp_chg) {
		final BooleanBoundComponentInterface _J_TOGGLE = identityBooleanBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_TOGGLE != null){
			invokeSwing(new Runnable() {public void run() {_J_TOGGLE.setBooleanValue(comp_chg.getChange());}}, "BooleanValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToFloatValueChange(final FloatValueChange comp_chg) {
		final FloatBoundComponentInterface _J_FLOAT_COMP = identityFloatBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_FLOAT_COMP != null){
			invokeSwing(new Runnable() {public void run() {_J_FLOAT_COMP.setFloatValue(comp_chg.getChange());}}, "FloatValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToIntegerValueChange(final IntegerValueChange comp_chg) {
		final IntegerBoundComponentInterface _J_INTEGER_COMP = identityIntegerBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_INTEGER_COMP != null){
			invokeSwing(new Runnable() {public void run() {_J_INTEGER_COMP.setIntegerValue(comp_chg.getChange());}}, "IntgerValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	protected void feedbackToStringValueChange(final StringValueChange comp_chg) {
		final TextShowerComponentInterface _J_TEXT_COMP = identityTextShowerComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_TEXT_COMP != null){
			invokeSwing(new Runnable() {public void run() {_J_TEXT_COMP.setText(comp_chg.getChange());}}, "StringValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}
	
	
	@Override
	public void feedbackToFocusOrderChange(final FocusOrderChange comp_chg) {
		final JComponentSupport _J_COMP = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP != null){
			invokeSwing(new Runnable() {public void run() {/* DO NOTHING*/}}, "FocusOrderChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToSpecialFeatureChange(SpecialFeatureChange comp_chg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void feedbackToTransparenceyChange(final TransparencyChange comp_chg) {
		final JComponentSupport _J_COMP = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP != null){
			invokeSwing(new Runnable() {public void run() {_J_COMP.setTransparency(comp_chg.getChange());}}, "TransparencyChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToFontSizeChange(final FontSizeChange comp_chg) {
		final JComponentSupport _J_COMP = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP != null){
			invokeSwing(new Runnable() {public void run() {_J_COMP.setFontSize((int)comp_chg.getChange());}}, "FontSizeChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToPositionChange(PositionChange comp_chg) {
		Component _comp = comp_chg.getBoundComponent();
		Component _parent = _comp.getParentComponent();
		final JComponentSupport _J_COMP_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());

		if (_J_COMP_SUPPORT != null){
			PlaneVector _pos = _comp.getUpperLeftOrigin();
			final int _POS_X, _POS_Y;
			if (_parent != null){
				JComponentSupport _jparent = identitySwingComponentMap.get(_parent.getIdentity());
				if (_jparent != null){
					Dimension _parent_dim = _jparent.getSize();
					_POS_X = (int) (((float)(_parent_dim.width)) * _pos.getX());
					_POS_Y = (int) (((float)(_parent_dim.height)) * _pos.getY());
				}
				else{
					_POS_X = (int) (((float)(windowFrame.getWidth())) * _pos.getX());
					_POS_Y = (int) (((float)(windowFrame.getHeight())) * _pos.getY());
				}
			}
			else{
				_POS_X = (int) (((float)(windowFrame.getWidth())) * _pos.getX());
				_POS_Y = (int) (((float)(windowFrame.getHeight())) * _pos.getY());
			}
			invokeSwing(new Runnable() {public void run() {_J_COMP_SUPPORT.setLocation(new Point(_POS_X,_POS_Y));}}, "PositionChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToSizeChange(SizeChange comp_chg) {
		final JComponentSupport _J_COMP_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP_SUPPORT != null){
			PlaneVector _size = comp_chg.getBoundComponent().getSize();

			final Dimension _PARENT_DIM; 
			Component	_parent = _J_COMP_SUPPORT.getSupportedComponent().getParentComponent();
			JComponentSupport	_jparent;
			if (_parent != null){
				_jparent = identitySwingComponentMap.get(_parent.getIdentity());
			}
			else{
				_jparent = null;
			}

			if ((_parent != null)&& (_jparent != null)) {
				_PARENT_DIM = _jparent.getSize();
			}
			else{
				_PARENT_DIM = windowFrame.getSize();
			}
			final Dimension _COMP_DIR = new Dimension(
					(int)(_PARENT_DIM.getWidth()* _size.getX()), 
					(int)(_PARENT_DIM.getHeight()* _size.getY()));
			invokeSwing(new Runnable() {public void run() {_J_COMP_SUPPORT.setSize(_COMP_DIR);}}, "SizeChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToBackgroundColorChange(final BackgroundColorChange comp_chg) {
		final JComponentSupport _J_COMP_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP_SUPPORT != null){
			SpaceVector _color = comp_chg.getChange();
			final float _COLOR_TAB[] = {_color.getX(), _color.getY(), _color.getZ(), _J_COMP_SUPPORT.getTransparency()};
			invokeSwing(new Runnable() {public void run() {_J_COMP_SUPPORT.setBackground(_COLOR_TAB);}}, "BackgroundColorChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToForegroundColorChange(ForegroundColorChange comp_chg) {
		final JComponentSupport _J_COMP_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP_SUPPORT != null){
			SpaceVector _color = comp_chg.getChange();
			final float _COLOR_TAB[] = {_color.getX(), _color.getY(), _color.getZ(), _J_COMP_SUPPORT.getTransparency()};
			invokeSwing(new Runnable() {public void run() {_J_COMP_SUPPORT.setForeground(_COLOR_TAB);}}, "ForegroundColorChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToFontColorChange(FontColorChange comp_chg) {
		//
		// in fact font color is the foreground color
		//
		final JComponentSupport _J_COMP_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		SpaceVector _color = comp_chg.getChange();
		final float _COLOR_TAB[] = {_color.getX(), _color.getY(), _color.getZ(), 0.0f};
		if (_J_COMP_SUPPORT != null){
			invokeSwing(new Runnable() {public void run() {_J_COMP_SUPPORT.setForeground(_COLOR_TAB);}}, "ForegroundColorChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToNameChange(final NameChange comp_chg) {
		final JComponentSupport _J_COMP_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP_SUPPORT != null){
			invokeSwing(new Runnable() {public void run() {
				_J_COMP_SUPPORT.setName(comp_chg.getChange().getDefaultValue());
			}}, "NameChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToDescriptionChange(final DescriptionChange comp_chg) {
		final JComponentSupport _J_COMP_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_COMP_SUPPORT != null){
			invokeSwing(new Runnable() {public void run() {_J_COMP_SUPPORT.setDescription(comp_chg.getChange().getDefaultValue());}}, "DescriptionChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToLabelChange(final LabelChange comp_chg) {
		final TextShowerComponentInterface _J_TEXT_SUPPORT = identityTextShowerComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_TEXT_SUPPORT != null){
			invokeSwing(new Runnable() {public void run() {_J_TEXT_SUPPORT.setText(comp_chg.getChange().getDefaultValue());}}, "LabelChange");
		}
	}

	@Override
	public void feedbackToFontTypeChange(final FontTypeChange comp_chg) {
		final JComponentSupport _J_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_SUPPORT != null){
			invokeSwing(new Runnable() {public void run() {_J_SUPPORT.setFont(comp_chg.getChange());}}, "FontTypeChange");
		}
	}

	@Override
	public void feedbackToFontStyleChange(final FontStyleChange comp_chg) {
		final JComponentSupport _J_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_SUPPORT != null){
			invokeSwing(new Runnable() {public void run() {_J_SUPPORT.setFontStyle(comp_chg.getChange());}}, "FontStyleChange");
		}
	}

	@Override
	public ComponentSupport treatFeedbackCreateStandardComponent(CreateStandardComponentChange comp_chg) throws CannotCreateSuchComponentException {

		JComponentSupport _res = createJComponentAndSupport(comp_chg);

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
		final JComponentSupport 	_JPARENT_SUPPORT;
		final JComponent			_ADDED_COMP = _res.getSwingComponent();
		Component			_parent = comp_chg.getBoundComponent().getParentComponent();
		if (_parent != null){
			_JPARENT_SUPPORT = identitySwingComponentMap.get(_parent.getIdentity());
		}
		else{
			_JPARENT_SUPPORT = getCurrentPanel();
		}
		if ((_JPARENT_SUPPORT != null) && (_res != getCurrentPanel())){
			invokeSwing(new Runnable() {public void run() {_JPARENT_SUPPORT.getSwingComponent().add(_ADDED_COMP);}}, "treatCreateStandardComponent");
		}

		return _res;
	}

	private JComponentSupport createJComponentAndSupport(CreateStandardComponentChange comp_chg) 
			throws CannotCreateSuchComponentException{
		JComponentSupport _res = null;

		Map<ComponentFamily.StandardComponentProperty, String> _prop_value_map = comp_chg.getPropertyInformationsMap();
		switch (comp_chg.getChange()){
		case PANEL : 
			JPanel _jpane = new JPanel();
			JPanelSupport _jpane_support = new JPanelSupport(_jpane, currentPanel, comp_chg.getBoundComponent());	
			setCurrentPanel(_jpane_support);
			_res = _jpane_support;
			break;
		case BUTTON :
			_res = new JButtonSupport(new JButton(), comp_chg.getBoundComponent());
			break;
		case CHECKBOX :
			JToggleButton _jtoggle = new JCheckBox();
			_res = new JToggleButtonSupport(_jtoggle, comp_chg.getBoundComponent());
			break;
			//case COMBO_BOX :
			//return new SwingSingleComponent(new JComboBox<E>);
			//break;
		case LABEL :
			JLabel _jlabel = new JLabel();
			_res = new JLabelSupport(_jlabel, comp_chg.getBoundComponent());
			break;
		case MENU :
			JMenu _jmenu = new JMenu();
			_res = new JMenuItemSupport(_jmenu, comp_chg.getBoundComponent());
			break;
		case MENU_BAR :
			_res = new JComponentSupport(new JMenuBar(), comp_chg.getBoundComponent());
			break;
		case MENU_ITEM :
			JMenuItem _jmenu_item = new JMenuItem();
			_res = new JMenuItemSupport(_jmenu_item, comp_chg.getBoundComponent());
			break;
		case SLIDER :
			JSlider _jslider = new JSlider();
			//identity
			_res = new JSliderSupport(_jslider, comp_chg.getBoundComponent(), _prop_value_map);
			break;
		case SPINNER :
			JSpinner _jspinner = new JSpinner();
			_res = new JSpinnerSupport(_jspinner, comp_chg.getBoundComponent());
			break;
		case TOOLBAR :
			_res = new JComponentSupport(new JToolBar(), comp_chg.getBoundComponent());
			break;
		case TREE :
			_res = new JComponentSupport(new JTree(), comp_chg.getBoundComponent());
			break;
		case INVISIBLE_CONTAINER :
			_res = new JPanelSupport(new JPanel(), getCurrentPanel(), comp_chg.getBoundComponent());
			//float [] _inv_color = {0f,0f,0f,0f};
			//_res.setForeground(_inv_color);
			//_res.setBackground(_inv_color);
			break;
		case TEXT_FIELD :
			_res = new JTextFieldSupport(new JTextField(), comp_chg.getBoundComponent());
			break;
		default:
			throw new CannotCreateSuchComponentException("Standard Component");
		}
		return _res;
	}

	@Override
	public ComponentSupport treatFeedbackCreateSpecialComponent(
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
	@Override
	public void feedbackToBeginSessionChange(BeginSessionChange comp_chg) {
		sessionChangeOpened = true;

	}
	@Override
	public void feedbackToEndSessionChange(EndSessionChange comp_chg) {
		sessionChangeOpened = false;
		invokeSwing(new Runnable() {public void run() {/*do nothing*/}}, "EndSessionChange");
	}
	@Override
	public void feedbackToSuppressComponentChange(
			SuppressComponentChange comp_chg) {
		final JComponentSupport _J_SUPPORT = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (_J_SUPPORT != null){
			invokeSwing(new Runnable() {public void run() {if (getCurrentPanel() != null){
				getCurrentPanel().getSwingComponent().remove(_J_SUPPORT.getSwingComponent());}}}, 
					"SuppressComponentChange");
		}
	}

}
