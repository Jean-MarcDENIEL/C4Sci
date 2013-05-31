package c4sci.data.dataParameters.basicModifiables;

import c4sci.data.Modifiable;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * This class represents a double value manipulated through strings values.
 * @author jeanmarc.deniel
 *
 */
public class DoubleModifiable extends Modifiable {

	private double innerState;
	
	@Override
	public String getValue() {
		return Double.toString(innerState);
	}

	@Override
	public void setValue(String str_to_parse) throws DataValueParsingException {
		innerState = Double.parseDouble(str_to_parse);

	}

	@Override
	public String getRegExp() {
		// first : return "^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$"; what means that it's a beginning + end of a line
		return "[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?";
	}

	
	public synchronized double getDoubleValue(){
		return innerState;
	}
	public synchronized void setDoubleValue(final double fl_val){
		innerState = fl_val;
	}
}
