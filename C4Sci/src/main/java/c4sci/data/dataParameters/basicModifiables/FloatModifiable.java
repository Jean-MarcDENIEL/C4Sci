package c4sci.data.dataParameters.basicModifiables;

import c4sci.data.Modifiable;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * This class represents a float value manipulated through strings values.
 * @author jeanmarc.deniel
 *
 */
public class FloatModifiable extends Modifiable {

	private float	innerState;
	
	@Override
	public String getValue() {
		return Float.toString(innerState);
	}

	@Override
	public void setValue(String str_to_parse) throws DataValueParsingException {
		innerState = Float.parseFloat(str_to_parse);
	}

	@Override
	public String getRegExp() {
		// first : return "^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$"; what means that it's a beginning + end of a line
		return "[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?";
	}

}
