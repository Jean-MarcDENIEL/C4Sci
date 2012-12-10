package c4sci.data.basicDataParameters;

import c4sci.data.DataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.math.geometry.plane.PlaneVector;
/**
 * This class contains a PlaneVector parameter.
 * @author jeanmarc.deniel
 *
 */
public class PlaneVectorDataParameter extends DataParameter {

	private PlaneVector 	planeVector;
	public PlaneVectorDataParameter(){
		planeVector = new PlaneVector();
	}
	public PlaneVectorDataParameter(String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) {
		super(token_str, name_term, descr_term);
		planeVector = new PlaneVector();
	}

	@Override
	public synchronized String getValue() {
		return planeVector.toString();
	}

	@Override
	public synchronized void setValue(String str_to_parse)
			throws DataValueParsingException {
		try{
		planeVector.opEquals(PlaneVector.parseVector(str_to_parse));
		}
		catch (NumberFormatException _e){
			throw new DataValueParsingException("x(float) y(float)", str_to_parse, "bad string", _e);
		}
	}
	
	public synchronized PlaneVector getPlaneVectorValue(){
		return new PlaneVector(planeVector);
	}
	
	public synchronized void setPlaneVectorValue(final PlaneVector other_vec){
		planeVector.opEquals(other_vec);
	}

	@Override
	protected DataParameter getSameDataParameterInstance() {
		PlaneVectorDataParameter _res = new PlaneVectorDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
		_res.planeVector.opEquals(planeVector);
		return _res;
	}

	@Override
	public String getRegExp() {
		return "^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)? [+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$";
	}
}
