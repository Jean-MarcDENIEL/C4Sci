package c4sci.data;

import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class is provided to contain and integer type parameter.
 * @author jeanmarc.deniel
 *
 */
public class IntegerDataParameter extends DataParameter {

	private int paramValue;
	
	public IntegerDataParameter(String token_str, InternationalizableTerm name_term, InternationalizableTerm descr_term){
		super(token_str, name_term, descr_term);
		paramValue			= 0;
	}
	
	public String getParameterValue() {
		return Integer.toString(paramValue);
	}

	public void setParameterValue(String str_to_parse)
			throws DataValueParsingException {
		try{
		paramValue = Integer.parseInt(str_to_parse);
		}
		catch (NumberFormatException _e){
			throw new DataValueParsingException("Integer value", str_to_parse, "Integer parsing error", _e);
		}
	}

}
