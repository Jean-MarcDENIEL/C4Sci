package c4sci.data.basicDataParameters;

import c4sci.data.DataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class StringDataParameter extends DataParameter {

	private String	stringData;
	
	public StringDataParameter(String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) {
		super(token_str, name_term, descr_term);
		stringData = "";
	}

	@Override
	public String getParameterValue() {
		return stringData;
	}

	@Override
	public void setParameterValue(String str_to_parse)
			throws DataValueParsingException {
		stringData = str_to_parse;
	}

	@Override
	public DataParameter getClone() {
		return new StringDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
	}

}
