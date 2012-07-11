package c4sci.data;

import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class is provided to contain and integer type parameter.
 * @author jeanmarc.deniel
 *
 */
public class IntegerDataParameter implements DataParameter {

	private int						paramValue;
	private String					paramToken;
	private InternationalizableTerm	paramName;
	private InternationalizableTerm	paramDescription;
	public IntegerDataParameter(String token_str, InternationalizableTerm name_term, InternationalizableTerm descr_term){
		paramValue			= 0;
		paramToken			= token_str;
		paramName			= name_term;
		paramDescription	= descr_term;
	}
	
	public String getParameterValue() {
		return Integer.toString(paramValue);
	}

	public String getParameterToken() {
		return paramToken;
	}

	public InternationalizableTerm getParameterName() {
		return paramName;
	}

	public InternationalizableTerm getParameterDescription() {
		return paramDescription;
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
