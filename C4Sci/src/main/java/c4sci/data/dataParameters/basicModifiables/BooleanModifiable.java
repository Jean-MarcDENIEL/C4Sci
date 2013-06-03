package c4sci.data.dataParameters.basicModifiables;

import c4sci.data.Modifiable;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * This class represents a boolean value manipulated through strings values. 
 * Only {@link #TRUE_STR}={@value #TRUE_STR} and {@link #FALSE_STR}={@value #FALSE_STR} are recognized.
 * @author jeanmarc.deniel
 *
 */
public final class BooleanModifiable extends Modifiable {

	private boolean	innerState;
	
	public static final String	TRUE_STR	= "true";
	public static final String	FALSE_STR	= "false";
	
	public synchronized String getValue() {
		return Boolean.toString(innerState);
	}

	public synchronized void setValue(String str_to_parse) throws DataValueParsingException {
		innerState = str_to_parse.startsWith(TRUE_STR);
	}

	public String getRegExp() {
		return TRUE_STR+"|"+FALSE_STR;
	}
	
	public synchronized void setBooleanValue(final boolean b_val){
		innerState = b_val;
	}
	public synchronized boolean getBooleanValue(){
		return innerState;
	}

}
