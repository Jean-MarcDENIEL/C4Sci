package c4sci.data.dataParameters.singleValueModifiables;

import c4sci.data.Modifiable;
import c4sci.data.dataParameters.RegularExpressions;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * This class represents an integer value manipulated through strings values.
 * @author jeanmarc.deniel
 *
 */
public class IntegerModifiable extends Modifiable {

	private int	innerState;
	
	@Override
	public synchronized String getValue() {
		return Integer.toString(innerState);
	}

	@Override
	public synchronized void setValue(String str_to_parse) throws DataValueParsingException {
		try{
			innerState = Integer.parseInt(str_to_parse);
		}
		catch(NumberFormatException _e){
			throw new DataValueParsingException("an integer value", str_to_parse, "cannot parse", _e);
		}
	}

	@Override
	public String getRegExp() {
		// at first "^(\\+|-)?\\d+$"; meaning a beginning + end of line
		return RegularExpressions.INTEGER_REGEXP;
		// too : ^[-+]?\d*$ for empty strings
	}
	
	public synchronized int getIntegerValue(){
		return innerState;
	}
	public synchronized void setIntegerValue(final int int_val){
		innerState = int_val;
	}
}
