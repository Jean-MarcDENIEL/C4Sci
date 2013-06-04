package c4sci.data.dataParameters.basicModifiables;

import c4sci.data.Modifiable;
import c4sci.data.dataParameters.RegularExpressions;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * This class represents a float value manipulated through strings values.
 * @author jeanmarc.deniel
 *
 */
public final class FloatModifiable extends Modifiable {

	private float	innerState;
	
	@Override
	public synchronized String getValue() {
		return Float.toString(innerState);
	}

	@Override
	public synchronized void setValue(String str_to_parse) throws DataValueParsingException {
		try{
			innerState = Float.parseFloat(str_to_parse);
		}
		catch(NumberFormatException _e){
			throw new DataValueParsingException("a float value", str_to_parse, "cannot parse", _e);
		}
	}

	@Override
	public String getRegExp() {
		// first : return "^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$"; what means that it's a beginning + end of a line
		return RegularExpressions.FLOAT_REGEXP;
	}

	public synchronized float getFloatValue(){
		return innerState;
	}
	public synchronized void setFloatValue(final float fl_val){
		innerState = fl_val;
	}
}
