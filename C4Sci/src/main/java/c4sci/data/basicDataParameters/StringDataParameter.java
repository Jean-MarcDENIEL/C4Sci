package c4sci.data.basicDataParameters;

import c4sci.data.DataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class StringDataParameter extends DataParameter {

	private String	stringData;
	public StringDataParameter(){}
	public StringDataParameter(String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) {
		super(token_str, name_term, descr_term);
		stringData = "";
	}

	@Override
	public synchronized String getValue() {
		return stringData;
	}

	@Override
	public synchronized void setValue(String str_to_parse)
			throws DataValueParsingException {
		stringData = str_to_parse;
	}

	public synchronized String getStringValue(){
		return stringData;
	}
	public synchronized void setStringValue(String str_val){
		stringData = str_val;
	}
	
	@Override
	protected DataParameter getSameDataParameterInstance() {
		return new StringDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
	}
	@Override
	public String getRegExp() {
		//return "\\w*|\\W*";
		return ".*";
	}
}