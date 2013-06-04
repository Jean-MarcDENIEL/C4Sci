package c4sci.data.dataParameters.composedModifiables;

import c4sci.data.DataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.geometry.space.SpaceVector;

public class SpaceVectorDataParameter extends DataParameter {

	private SpaceVector	spaceVector;
	public SpaceVectorDataParameter(){
		spaceVector = new SpaceVector();
	}
	public SpaceVectorDataParameter(String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) {
		super(token_str, name_term, descr_term);
		spaceVector = new SpaceVector();
	}

	@Override
	public synchronized String getValue() {
		return spaceVector.toString();
	}

	@Override
	public synchronized void setValue(String str_to_parse)
			throws DataValueParsingException {
		try{
			spaceVector.opEquals(SpaceVector.parseVector(str_to_parse));
		}
		catch(NumberFormatException _e){
			throw new DataValueParsingException(" x(float) y(float) z(float) w(float)", str_to_parse, "bad string", _e);
		}
	}
	
	public synchronized SpaceVector getSpaceVectorValue(){
		return new SpaceVector(spaceVector);
	}
	
	public synchronized void setSpaceVectorValue(final SpaceVector other_vec){
		spaceVector.opEquals(other_vec);
	}

	@Override
	protected DataParameter getSameDataParameterInstance() {
		SpaceVectorDataParameter _res = new SpaceVectorDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
		_res.spaceVector.opEquals(spaceVector);
		return _res;
	}
	@Override
	public String getRegExp() {
		return "^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)? [+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)? [+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)? [+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$";
	}
}