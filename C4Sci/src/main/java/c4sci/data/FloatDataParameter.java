package c4sci.data;

import c4sci.data.internationalization.InternationalizableTerm;

public class FloatDataParameter extends DataParameter {

	private float paramValue;
	
	public FloatDataParameter(String token_str, InternationalizableTerm name_term, InternationalizableTerm descr_term) {
		super(token_str, name_term, descr_term);
		
	}

	public String getParameterValue() {
		return Float.toString(paramValue);
	}

	public void setParameterValue(String str_to_parse)
			throws DataValueParsingException {
		try{
		paramValue = Float.parseFloat(str_to_parse);
		}
		catch (NumberFormatException _e){
			throw new DataValueParsingException("float", str_to_parse, "parsing error", _e);
		}

	}

}
