package c4sci.modelViewPresenterController.presenterControllerInterface.scales;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.algebra.Floatings;

/**
 * This class represents a metric at different scales.<br>
 * <br>
 * This class gives facilities to convert form a scale to another.
 * @author jeanmarc.deniel
 *
 */
public class UnitScales {
	private Map<String, InternationalizableTerm>	nameMap;						// scales are referred by their abbreviation
	private Map<String, Float>						scaleMap;						// scaling factor (*) to pass from a given scale to the main scale
	private Map<Float, String>						abbreviationMap;				// abbreviation sorted by ascending scale factor
	/**
	 * Creates a UnitScale which main scale has a scaling factor of 1.0.
	 * @param main_scale_abbreviation The main scale abbreviation.
	 * @param main_scale_name The main scale name.
	 */
	public UnitScales(String main_scale_abbreviation, InternationalizableTerm main_scale_name) {
		nameMap						= new ConcurrentSkipListMap<String, InternationalizableTerm>();
		scaleMap					= new ConcurrentSkipListMap<String, Float>();
		abbreviationMap				= new ConcurrentSkipListMap<Float, String>();
		addScale(main_scale_abbreviation, main_scale_name, 1.0f);
	}
	/**
	 * 
	 * @return The abbreviations sorted in ascending scaling factor order.
	 */
	public final String[]	getSortedAbbreviations(){
		return abbreviationMap.values().toArray(new String[1]);
	}
	/**
	 * Adds a new scale to the structure. A 0.0 scale will have no addition effect on the UnitScales.
	 * @param scale_abbrev scale abbreviation, e.g "cm" for centimeter
	 * @param scale_name scale name, e.g "centimeter"
	 * @param scale_factor scaling factor. e.g 0.01 for centimeter relatively to meter. 
	 */
	public final void addScale(String scale_abbrev, InternationalizableTerm scale_name, float scale_factor){
		if (scale_factor == 0.0){
			return;
		}
		nameMap.put(scale_abbrev, scale_name);
		scaleMap.put(scale_abbrev, Float.valueOf(scale_factor));
		abbreviationMap.put(Float.valueOf(scale_factor), scale_abbrev);
	}
	/**
	 * 
	 * @param scale_abbrev the scale abbreviation
	 * @return the scaling factor corresponding to the abbreviation argument
	 * @throws NoSuchScaleExistsException
	 */
	public final float getScalingFactor(String scale_abbrev) throws NoSuchScaleExistsException {
		if (scale_abbrev == null){
			throw new NoSuchScaleExistsException("null");
		}
		Float _scaling = scaleMap.get(scale_abbrev);
		if (_scaling == null){
			throw new NoSuchScaleExistsException("no scale for abbreviation : " +scale_abbrev);
		}
		return _scaling.floatValue();
	}
	public final String getAbbreviation(float scaling_factor) throws NoSuchScaleExistsException{
		String _abbrev = abbreviationMap.get(Float.valueOf(scaling_factor));
		if (_abbrev == null){
			throw new NoSuchScaleExistsException("no abbreviation for scale : "+scaling_factor);
		}
		return _abbrev;
	}
	/**
	 * Computes the scale that is the most adapted to represent an argument value.<br>
	 * <br>
	 * The choice process is the following :<br>
	 * <ol>
	 * 		<li>_v is the value in main scale</li>
	 * 		<li>for each scale, we compute _scaled_value = _v/scale</li>
	 * 		<li>we compute the _dist to 1.0 like this :
	 * 		<ul>
	 * 			<li> if _scale_v > 1.0 then _dist = _scaled_v</li>
	 * 			<li> else _dist = 1.0/_scaled_v
	 * 		</ul></li>
	 * 		<li> we choose scale so that |_dist-1.0| is the smallest
	 * </ol>
	 * 
	 * @param main_scale_value The value to choose the best fitted scale to. It is expressed in the main scale.
	 * @return the best fitted scale abbreviation or the smallest if 0.0 is passed as argument.
	 */
	public final String chooseBestFittedScale(float main_scale_value){
		if (Floatings.isEqual(main_scale_value, 0.0f)){
			return getSortedAbbreviations()[0];
		}
		float _abs_main_scale_value = Math.abs(main_scale_value);
		float _min_dist = _abs_main_scale_value >= 1.0 ? Math.abs(_abs_main_scale_value - 1.0f) : Math.abs(1.0f/_abs_main_scale_value - 1.0f);
		String[] _abbrev_tab = getSortedAbbreviations();
		try {
			String _res = getAbbreviation(1.0f);
			for (String _abbrev : _abbrev_tab){
				float _scale = getScalingFactor(_abbrev);
				float _scaled_value = _abs_main_scale_value/_scale; // number of units at _abbrev scale
				float _dist;
				if (_scaled_value >= 1.0f){
					_dist = _scaled_value - 1.0f;
				}
				else{
					_dist = 1.0f / _scaled_value - 1.0f;
				}
				if (_dist < _min_dist){
					_res = _abbrev;
					_min_dist = _dist;
				}
			}
			return _res;
		} catch (NoSuchScaleExistsException _e) {
			// should not throw here
			_e.printStackTrace();
			return null;
		}
	}
	
	public static final float MILLIMETER_SCALE	= 0.001f;
	public static final float CENTIMETER_SCALE	= 0.01f;
	public static final float DECIMETER_SCALE	= 0.1f;
	public static final float DECAMETER_SCALE	= 10f;
	public static final float KILOMETER_SCALE	= 1000f;
	public static UnitScales createMeterUnitSCales(){
		UnitScales _res = new UnitScales("m", new InternationalizableTerm("meter"));	
	
		_res.addScale("mm",	new InternationalizableTerm("millimeter"),	MILLIMETER_SCALE);
		_res.addScale("cm",	new InternationalizableTerm("centimeter"),	CENTIMETER_SCALE);
		_res.addScale("dm", new InternationalizableTerm("decimeter"),	DECIMETER_SCALE);
		_res.addScale("dam",new InternationalizableTerm("decameter"), 	DECAMETER_SCALE);
		_res.addScale("km", new InternationalizableTerm("kilometer"),	KILOMETER_SCALE);
		return _res;
	}
}
