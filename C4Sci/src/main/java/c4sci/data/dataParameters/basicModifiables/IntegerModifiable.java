package c4sci.data.dataParameters.basicModifiables;

import c4sci.data.Modifiable;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * This class represents an integer value manipulated through strings values.
 * @author jeanmarc.deniel
 *
 */
public class IntegerModifiable extends Modifiable {

	private int	innerState;
	
	@Override
	public String getValue() {
		return Integer.toString(innerState);
	}

	@Override
	public void setValue(String str_to_parse) throws DataValueParsingException {
		innerState = Integer.parseInt(str_to_parse);
	}

	@Override
	public String getRegExp() {
		// at first "^(\\+|-)?\\d+$"; meaning a beginning + end of line
		return "(\\+|-)?\\d+";
		// too : ^[-+]?\d*$ for empty strings
	}
	
	public synchronized int getIntegerValue(){
		return innerState;
	}
	public synchronized void setIntegerValue(final int int_val){
		innerState = int_val;
	}
}
