package c4sci.data.basicDataParameters;

import c4sci.data.DataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.geometry.space.SpaceVector;

public class SpaceVectorDataParameter extends DataParameter {

	private SpaceVector	spaceVector;
	
	public SpaceVectorDataParameter(String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) {
		super(token_str, name_term, descr_term);
		spaceVector = new SpaceVector();
	}

	@Override
	public String getParameterValue() {
		return spaceVector.toString();
	}

	@Override
	public void setParameterValue(String str_to_parse)
			throws DataValueParsingException {
		try{
			spaceVector.opEquals(SpaceVector.parseVector(str_to_parse));
		}
		catch(NumberFormatException _e){
			throw new DataValueParsingException(" x(float) y(float) z(float) w(float)", str_to_parse, "bad string", _e);
		}
	}

	@Override
	protected DataParameter getSameDataParameterInstance() {
		SpaceVectorDataParameter _res = new SpaceVectorDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
		_res.spaceVector.opEquals(spaceVector);
		return _res;
	}

}
