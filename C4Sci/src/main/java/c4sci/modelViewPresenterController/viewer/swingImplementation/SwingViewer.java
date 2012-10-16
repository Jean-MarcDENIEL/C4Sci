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

		final JPanel jpanel = (JPanel) current_panel.getSwingComponent();

		invokeSwing(new Runnable() {public void run() {
			jpanel.setLayout(null);
			jpanel.setBackground(Color.red);
			jpanel.setSize(windowFrame.getSize());
			jpanel.setVisible(true);
			windowFrame.add(jpanel);
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
		final JComponentSupport jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcomp != null){
			invokeSwing(new Runnable() {public void run() {jcomp.setEnabled(comp_chg.getChange());}}, "ActivityChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToVisibilityChange(final VisibilityChange comp_chg) {
		final JComponentSupport jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcomp != null){
			invokeSwing(new Runnable() {public void run() {jcomp.setVisible(comp_chg.getChange());}}, "VisibilityChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}

	}

	@Override
	public void feedbackToBooleanValueChange(final BooleanValueChange comp_chg) {
		final BooleanBoundComponentInterface jtoggle = identityBooleanBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (jtoggle != null){
			invokeSwing(new Runnable() {public void run() {jtoggle.setBooleanValue(comp_chg.getChange());}}, "BooleanValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToFloatValueChange(final FloatValueChange comp_chg) {
		final FloatBoundComponentInterface jfloatcomp = identityFloatBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (jfloatcomp != null){
			invokeSwing(new Runnable() {public void run() {jfloatcomp.setFloatValue(comp_chg.getChange());}}, "FloatValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToIntegerValueChange(final IntegerValueChange comp_chg) {
		final IntegerBoundComponentInterface jintegercomp = identityIntegerBoundComponentMap.get(comp_chg.getComponentIdentity());
		if (jintegercomp != null){
			invokeSwing(new Runnable() {public void run() {jintegercomp.setIntegerValue(comp_chg.getChange());}}, "IntgerValueChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToFocusOrderChange(final FocusOrderChange comp_chg) {
		final JComponentSupport jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcomp != null){
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
		final JComponentSupport jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcomp != null){
			invokeSwing(new Runnable() {public void run() {jcomp.setTransparency(comp_chg.getChange());}}, "TransparencyChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToFontSizeChange(final FontSizeChange comp_chg) {
		final JComponentSupport jcomp = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcomp != null){
			invokeSwing(new Runnable() {public void run() {jcomp.setFontSize((int)comp_chg.getChange());}}, "FontSizeChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToPositionChange(PositionChange comp_chg) {
		Component _comp = comp_chg.getBoundComponent();
		Component _parent = _comp.getParentComponent();
		final JComponentSupport jcompsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcompsupport != null){
			final int x, y;
			if (_parent != null){
				JComponentSupport _jparent = identitySwingComponentMap.get(_parent.getIdentity());
				if (_jparent != null){
					Dimension _parent_dim = _jparent.getSize();
					x = (int) (((float)(_parent_dim.width)) * comp_chg.getChange().getX());
					y = (int) (((float)(_parent_dim.height)) * comp_chg.getChange().getY());
				}
				else{
					x = (int) (((float)(windowFrame.getWidth())) *comp_chg.getChange().getX());
					y = (int) (((float)(windowFrame.getHeight())) *comp_chg.getChange().getY());
				}
			}
			else{
				x = (int) (((float)(windowFrame.getWidth())) *comp_chg.getChange().getX());
				y = (int) (((float)(windowFrame.getHeight())) *comp_chg.getChange().getY());
			}
			invokeSwing(new Runnable() {public void run() {jcompsupport.setLocation(new Point(x,y));}}, "PositionChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToSizeChange(SizeChange comp_chg) {
		final JComponentSupport jcompsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcompsupport != null){
			final Dimension parentdim; 
			Component	_parent = jcompsupport.getSupportedComponent().getParentComponent();
			JComponentSupport	_jparent;
			if (_parent != null){
				_jparent = identitySwingComponentMap.get(_parent.getIdentity());
			}
			else{
				_jparent = null;
			}



			if ((_parent != null)&& (_jparent != null)) {
				parentdim = _jparent.getSize();
			}
			else{
				parentdim = windowFrame.getSize();
			}
			final Dimension compdim = new Dimension(
					(int)(parentdim.getWidth()*comp_chg.getChange().getX()), 
					(int)(parentdim.getHeight()*comp_chg.getChange().getY()));
			invokeSwing(new Runnable() {public void run() {jcompsupport.setSize(compdim);}}, "SizeChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToBackgroundColorChange(final BackgroundColorChange comp_chg) {
		final JComponentSupport jcompsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcompsupport != null){
			SpaceVector _color = comp_chg.getChange();
			final float colortab[] = {_color.getX(), _color.getY(), _color.getZ(), jcompsupport.getTransparency()};
			invokeSwing(new Runnable() {public void run() {jcompsupport.setBackground(colortab);}}, "BackgroundColorChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToForegroundColorChange(ForegroundColorChange comp_chg) {
		final JComponentSupport jcompsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcompsupport != null){
			SpaceVector _color = comp_chg.getChange();
			final float colortab[] = {_color.getX(), _color.getY(), _color.getZ(), jcompsupport.getTransparency()};
			invokeSwing(new Runnable() {public void run() {jcompsupport.setForeground(colortab);}}, "ForegroundColorChange");
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
		final JComponentSupport jcompsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		SpaceVector _color = comp_chg.getChange();
		final float colortab[] = {_color.getX(), _color.getY(), _color.getZ(), 0.0f};
		if (jcompsupport != null){
			invokeSwing(new Runnable() {public void run() {jcompsupport.setForeground(colortab);}}, "ForegroundColorChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToNameChange(final NameChange comp_chg) {
		final JComponentSupport jcompsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcompsupport != null){
			invokeSwing(new Runnable() {public void run() {
				jcompsupport.setName(comp_chg.getChange().getDefaultValue());
			}}, "NameChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToDescriptionChange(final DescriptionChange comp_chg) {
		final JComponentSupport jcompsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jcompsupport != null){
			invokeSwing(new Runnable() {public void run() {jcompsupport.setDescription(comp_chg.getChange().getDefaultValue());}}, "DescriptionChange");
		}
		else{
			treatUnableToProcessCommand(comp_chg);
		}
	}

	@Override
	public void feedbackToLabelChange(final LabelChange comp_chg) {
		final TextShowerComponentInterface jtextsupport = identityTextShowerComponentMap.get(comp_chg.getComponentIdentity());
		if (jtextsupport != null){
			invokeSwing(new Runnable() {public void run() {jtextsupport.setText(comp_chg.getChange().getDefaultValue());}}, "LabelChange");
		}
	}

	@Override
	public void feedbackToFontTypeChange(final FontTypeChange comp_chg) {
		final JComponentSupport jsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jsupport != null){
			invokeSwing(new Runnable() {public void run() {jsupport.setFont(comp_chg.getChange());}}, "FontTypeChange");
		}
	}

	@Override
	public void feedbackToFontStyleChange(final FontStyleChange comp_chg) {
		final JComponentSupport jsupport = identitySwingComponentMap.get(comp_chg.getComponentIdentity());
		if (jsupport != null){
			invokeSwing(new Runnable() {public void run() {jsupport.setFontStyle(comp_chg.getChange());}}, "FontStyleChange");
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
		final JComponentSupport 	jparentsupport;
		final JComponent			addedcomp = _res.getSwingComponent();
		Component			_parent = comp_chg.getBoundComponent().getParentComponent();
		if (_parent != null){
			jparentsupport = identitySwingComponentMap.get(_parent.getIdentity());
		}
		else{
			jparentsupport = null;
		}
		if ((_parent!= null) && (jparentsupport != null) && (jparentsupport != getCurrentPanel())){
			invokeSwing(new Runnable() {public void run() {jparentsupport.getSwingComponent().add(addedcomp);}}, "treatCreateStandardComponent");
		}
		else{
			//
			// _res is added to the current panel (if it's not itself the current panel)
			//
			if (getCurrentPanel() != _res){
				if (getCurrentPanel() != null){
					getCurrentPanel().getSwingComponent().add(_res.getSwingComponent());
				}
				else{
					treatUnableToProcessCommand(comp_chg);
				}
			}
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

}
