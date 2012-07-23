
package c4sci.data;

import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * @author jeanmarc.deniel
 *
 */
public class BooleanDataParameter extends DataParameter {

	private boolean paramValue;
	
	/**
	 * Constructs a BooleanDataParaeter whose value is false 
	 */
	public BooleanDataParameter(String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) {
		super(token_str, name_term, descr_term);
		paramValue = false;
	}


	@Override
	public String getParameterValue() {
		return Boolean.toString(paramValue);
	}


	@Override
	public void setParameterValue(String str_to_parse)
			throws DataValueParsingException {
		if (str_to_parse == null){
			throw new DataValueParsingException("Boolean", "(null)", "parsing error : null argument", null);
		}
		paramValue = Boolean.parseBoolean(str_to_parse);
	}
	
	public void setBooleanValue(final boolean b_val){
		paramValue = b_val;
	}
	public boolean getBooleanValue(){
		return paramValue;
	}


	@Override
	public DataParameter getClone() {
		return new BooleanDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
	}

}
