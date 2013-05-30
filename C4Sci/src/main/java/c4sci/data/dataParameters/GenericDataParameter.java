package c4sci.data.dataParameters;

import c4sci.data.DataParameter;
import c4sci.data.Modifiable;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

public class GenericDataParameter<M extends Modifiable> extends DataParameter {

	private M	innerState;
	
	
	public GenericDataParameter(M inner_value, 
			String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) {
		super(token_str, name_term, descr_term);
		innerState = inner_value;
	}
	
	@Override
	public String getValue() {
		return innerState.getValue();
	}

	@Override
	public void setValue(String str_to_parse) throws DataValueParsingException {
		innerState.setValue(str_to_parse);
	}

	@Override
	public String getRegExp() {
		return innerState.getRegExp();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected DataParameter getSameDataParameterInstance() throws InstantiationException, IllegalAccessException{
		return new GenericDataParameter(innerState.getClass().newInstance(), getParameterToken(), getParameterName(), getParameterDescription());
	}

}
