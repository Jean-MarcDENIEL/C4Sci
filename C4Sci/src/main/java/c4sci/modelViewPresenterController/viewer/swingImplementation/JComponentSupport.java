package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JComponent;

import c4sci.modelViewPresenterController.viewer.ComponentSupport;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
/**
 * This class is a Swing implementation of the {@link ComponentSupport} facade.
 * 
 * @author jeanmarc.deniel
 *
 */
public class JComponentSupport extends ComponentSupport {

	private static final int R_INDEX = 0;
	private static final int G_INDEX = 1;
	private static final int B_INDEX = 2;
	private static final int A_INDEX = 3;
	
	private JComponent swingComponent;
	
	private String		toolTipText;
	private String		nameText;
	private String		descriptionText;
	
	public JComponentSupport( JComponent swing_component, Component supported_component) {
		super(supported_component);
		swingComponent = swing_component;
		toolTipText		= "(no tip)";
		nameText		= "(no name)";
		descriptionText	= "(no description)";
	}

	public JComponent getSwingComponent(){
		return swingComponent;
	}
	
	/**
	 * Sets the background color with 4 [0-1] channel values : R G B alpha.
	 * @param rvba_01 The 4 channel float values in the 0.0 - 1.0 range.
	 */
	public void setBackground(float[] rvba_01){
		swingComponent.setBackground(new Color(rvba_01[R_INDEX], rvba_01[G_INDEX], rvba_01[B_INDEX], rvba_01[A_INDEX]));
	}
	/**
	 * Sets whether the component can receive user inputs.
	 * @param is_enabled
	 */
	public void setEnabled(boolean is_enabled){
		swingComponent.setEnabled(is_enabled);
	}
	/**
	 * Sets the font used by the component.
	 * @param font_name as defined in the {@link Font} class
	 * @param font_style as defined in the {@link Font} class
	 * @param font_point_size as defined in the {@link Font} class
	 */
	public void setFont(String font_name){
		Font _old_font = swingComponent.getFont(); 
		swingComponent.setFont(new Font(font_name, _old_font.getStyle(), _old_font.getSize()));
	}
	public void setFontSize(int font_point_size){
		Font _old_font = swingComponent.getFont(); 
		swingComponent.setFont(new Font(_old_font.getName(), _old_font.getStyle(), font_point_size));
	}
	public void setFontStyle(int font_style){
		Font _old_font = swingComponent.getFont();
		swingComponent.setFont(new Font(_old_font.getName(), font_style, _old_font.getSize()));
	}
	/**
	 * Sets the foreground color with 4 [0-1] channel values : R G B alpha.
	 * @param rvba_01 The 4 channel float values in the 0.0 - 1.0 range.
	 */
	public void setForeground(float[] rvba_01){
		swingComponent.setForeground(new Color(rvba_01[R_INDEX], rvba_01[G_INDEX], rvba_01[B_INDEX], rvba_01[A_INDEX]));
	}
	/**
	 * Sets the text to be displayed as an help about the component.
	 * @param tool_tip The text that is appended to the {@link JComponent#getName()} value.
	 */
	public void setToolTipText(String tool_tip){
		toolTipText = tool_tip;
		formatToolTipText();
	}
	private void formatToolTipText(){
		swingComponent.setToolTipText(
						"<b>"+nameText + "</b> : <br>"+ 
						"<b>Description:</b> :<br>" + descriptionText +"<br>" +
						"<b>Tip</b> :<br>" + toolTipText);
	}
	/**
	 * Sets the component to be visible or not.
	 * @param is_visible <i>true</i> if the component should be visible. 
	 */
	public void setVisible(boolean is_visible){
		swingComponent.setVisible(is_visible);
	}
	/**
	 * 
	 * @param alpha_01 ranges form 0 (transparent) or 1.0 (opaque)
	 */
	public void setTransparency(float alpha_01){
		float[] _backgrd = swingComponent.getBackground().getRGBComponents(null);
		float[] _foregrd = swingComponent.getForeground().getRGBComponents(null);
		
		_backgrd[A_INDEX] = alpha_01;
		_foregrd[A_INDEX] = alpha_01;
		
		setBackground(_backgrd);
		setForeground(_foregrd);
	}
	/**
	 * 
	 * @return transparency value ranging form 0 (transparent) or 1.0 (opaque)
	 */
	public float getTransparency(){
		Color _back = swingComponent.getBackground();
		final float _MAX_INT_VALUE = 255.0f;
		return ((float)_back.getAlpha())/_MAX_INT_VALUE;
	}
	/**
	 * Sets the component location relatively to its parent coordinate space.
	 * @param loc_pt as defined in {@link Point}
	 */
	public void setLocation(Point loc_pt){
		swingComponent.setLocation(loc_pt);
	}
	/**
	 * 
	 * @return The location of the component relatively to its parent coordinate space.
	 */
	public Point getLocation(){
		return swingComponent.getLocation();
	}
	/**
	 * Sets the size of the component.
	 * @param comp_dim The dimension of the component
	 */
	public void setSize(Dimension comp_dim){
		swingComponent.setSize(comp_dim);
	}
	/**
	 *  @return The dimension of the component.
	 */
	public Dimension getSize(){
		return swingComponent.getSize();
	}
	
	public void setName(String comp_name){
		swingComponent.setName(comp_name);
		nameText = comp_name;
		formatToolTipText();
	}
	
	public void setDescription(String comp_descr){
		descriptionText = comp_descr;
		formatToolTipText();
	}
	
}
