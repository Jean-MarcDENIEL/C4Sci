package c4sci.data.dataParameters.basicModifiables;

import c4sci.data.Modifiable;
import c4sci.data.dataParameters.RegularExpressions;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * This class represents a double value manipulated through strings values.
 * @author jeanmarc.deniel
 *
 */
public final class DoubleModifiable extends Modifiable {

	private double innerState;
	
	@Override
	public synchronized String getValue() {
		return Double.toString(innerState);
	}

	@Override
	public synchronized void setValue(String str_to_parse) throws DataValueParsingException {
		try{
		innerState = Double.parseDouble(str_to_parse);
		}
		catch(NumberFormatException _e){
			throw new DataValueParsingException("a double value", str_to_parse, "cannot parse", _e);
		}

	}

	@Override
	public String getRegExp() {
		// first : return "^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$"; what means that it's a beginning + end of a line
		return RegularExpressions.DOUBLE_REGEXP;
	}

	
	public synchronized double getDoubleValue(){
		return innerState;
	}
	public synchronized void setDoubleValue(final double fl_val){
		innerState = fl_val;
	}
}
