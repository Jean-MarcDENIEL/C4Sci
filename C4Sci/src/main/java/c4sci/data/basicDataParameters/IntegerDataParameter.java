package c4sci.data.basicDataParameters;

import c4sci.data.DataParameter;
import c4sci.data.exceptions.DataValueParsingException;
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
		if (str_to_parse == null){
			throw new DataValueParsingException("Integer", "(null)", "parsing error : null argument", null);
		}
		try{
		paramValue = Integer.parseInt(str_to_parse);
		}
		catch (NumberFormatException _e){
			throw new DataValueParsingException("Integer value", str_to_parse, "Integer parsing error", _e);
		}
	}

	public int getIntegerValue(){
		return paramValue;
	}
	public void setIntegerValue(final int int_val){
		paramValue = int_val;
	}

	@Override
	protected DataParameter getSameDataParameterInstance() {
		return new IntegerDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
	}
}