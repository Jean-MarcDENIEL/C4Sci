package c4sci.modelViewPresenterController.viewer.swingImplementation;

import java.util.Map;

import javax.swing.JSlider;

import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily.StandardComponentProperty;

/**
 * This class provides support for a {@link JSlider} that is able to work with integer or floating values.<br>
 * It uses {@link StandardComponentProperty} values in order to set minimum and maximum slider values.<br>
 * @warning if {@link StandardComponentProperty} string values cannot be parsed as integer values it will be considered that the slider works on float values.<br>
 * In the other case it will be considered that the slider works on integer values.
 * 
 * @author jeanmarc.deniel
 *
 */
public class JSliderSupport extends JComponentSupport implements IntegerBoundComponentInterface, 
	FloatBoundComponentInterface {

	private JSlider	sliderComponent; 
	private float 	minValue;
	private float	maxValue;
	
	private static final int RANGE_TICKS = 100;
	
	public JSliderSupport(JSlider slider_comp, Component supported_comp, Map<ComponentFamily.StandardComponentProperty, String> prop_map) {
		super(slider_comp, supported_comp);
		sliderComponent = slider_comp;
		processProperties(prop_map);
	}


	public void setFloatValue(float float_value) {
		int _position = (int)((float_value - minValue)/(maxValue - minValue));
		sliderComponent.setValue(_position);
	}

	public void setIntegerValue(int int_value) {
		sliderComponent.setValue(int_value);
	}

	public final void processProperties(Map<StandardComponentProperty, String> prop_values_map) {
		//
		// PARSES StandardComponentProperties to detect whereas they are floating or integer values.
		// The slider is then initialized according to the type and value of these properties.
		
		String  _min_str = prop_values_map.get(StandardComponentProperty.MIN_VALUE);	
		if (_min_str != null){
			try{
				sliderComponent.setMinimum(Integer.parseInt(_min_str));
			}
			catch(NumberFormatException _e){
				try{
					minValue = Float.parseFloat(_min_str);
					sliderComponent.setMinimum(0);
					sliderComponent.setMaximum(RANGE_TICKS);
				}
				catch(NumberFormatException _ee){
					return;
				}
			}
		}
		else{
			minValue = 0.0f;
			sliderComponent.setMinimum(0);
		}
		
		String _max_str = prop_values_map.get(StandardComponentProperty.MAX_VALUE);
		if (_max_str != null){
			try{
				sliderComponent.setMaximum(Integer.parseInt(_max_str));
			}
			catch(NumberFormatException _e){
				try{
					maxValue = Float.parseFloat(_max_str);
					sliderComponent.setMinimum(0);
					sliderComponent.setMaximum(RANGE_TICKS);
				}
				catch(NumberFormatException _ee){
					return;
				}
			}
		}
		else{
			maxValue = (float)RANGE_TICKS;
		}
	}

}
