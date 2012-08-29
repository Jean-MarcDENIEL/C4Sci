package c4sci.modelViewPresenterController.presenterControllerInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import c4sci.data.internationalization.InternationalizableTerm;

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

	public class NoSuchScaleExistsException extends Exception{

		public NoSuchScaleExistsException() {
			super();
		}

		public NoSuchScaleExistsException(String message_, Throwable cause_,
				boolean enable_suppression, boolean writable_stack_trace) {
			super(message_, cause_, enable_suppression, writable_stack_trace);
		}

		public NoSuchScaleExistsException(String message_, Throwable cause_) {
			super(message_, cause_);
		}

		public NoSuchScaleExistsException(String message_) {
			super(message_);
		}

		public NoSuchScaleExistsException(Throwable cause_) {
			super(cause_);
		}

		private static final long serialVersionUID = 5233375344647683986L;

		
	}
	
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
		return abbreviationMap.values().toArray(new String[0]);
	}

	public final void addScale(String scale_abbrev, InternationalizableTerm scale_name, float scale_factor){
		nameMap.put(scale_abbrev, scale_name);
		scaleMap.put(scale_abbrev, Float.valueOf(scale_factor));
		abbreviationMap.put(Float.valueOf(scale_factor), scale_abbrev);
	}
	
	public final float getScalingFactor(String scale_abbrev) throws NoSuchScaleExistsException {
		Float _scaling = scaleMap.get(scale_abbrev);
		if (_scaling == null){
			throw new NoSuchScaleExistsException(scale_abbrev);
		}
		return _scaling.floatValue();
	}

}
