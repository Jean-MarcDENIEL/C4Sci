package c4sci.data.dataParameters.basicModifiables;

import c4sci.data.Modifiable;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * This class represents a boolean value manipulated through strings values. 
 * @author jeanmarc.deniel
 *
 */
public class BooleanModifiable extends Modifiable {

	private boolean	innerState;
	
	public String getValue() {
		return Boolean.toString(innerState);
	}

	public void setValue(String str_to_parse) throws DataValueParsingException {
		innerState = Boolean.parseBoolean(str_to_parse);
	}

	public String getRegExp() {
		return "true|false";
	}
	
	public synchronized void setBooleanValue(final boolean b_val){
		innerState = b_val;
	}
	public synchronized boolean getBooleanValue(){
		return innerState;
	}

}
