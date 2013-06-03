package c4sci.data.dataParameters;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.regex.Pattern;

import c4sci.data.DataParameter;
import c4sci.data.Modifiable;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This generic class is intended are representing arrays of non null {@link Modifiable}s. <br>
 * In value strings, elements are separated by white spaces. That's why {@link Modifiable} elements should not have white spaces in their value strings, to avoid disorders.
 * @author jeanmarc.deniel
 *
 */
public class GenericArrayDataParameter<M extends Modifiable> extends DataParameter {

	private static final String		INTEGER_REGEXP = "(\\+|-)?\\d+" ;
	
	
	private M[]		innerArray;
	
	/**
	 * Creates the parameter array and ensures that the array has no null element.
	 * @param inner_value The array of {@link Modifiable} elements. {@link #getClone()} and {@link #getSameDataParameterInstance()} results will be of that array length.
	 * @param token_str
	 * @param name_term
	 * @param descr_term
	 * @throws CannotInstantiateParameterException
	 */
	public GenericArrayDataParameter(M[] inner_value, 
			String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) throws CannotInstantiateParameterException {
		super(token_str, name_term, descr_term);
		innerArray = inner_value;
		
		Class<M> _m_type = getSupportedClass();
		
		for (int _i=0; _i<innerArray.length; _i++){
			if (innerArray[_i] == null){
				try {
					innerArray[_i] = _m_type.newInstance();
				} catch (InstantiationException  _e) {
					throw new CannotInstantiateParameterException(token_str, "Cannot instantiate " + _m_type.getName(), _e);
				} catch (IllegalAccessException _e) {
					throw new CannotInstantiateParameterException(token_str, "Cannot instantiate " + _m_type.getName(), _e);
				}
			}
		}
	}
	
	/**
	 * @return an instance with same array size. The array is initialized so that it has no null element.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected DataParameter getSameDataParameterInstance() throws CannotInstantiateParameterException{
		return new GenericArrayDataParameter<M>((M[])Array.newInstance(innerArray.getClass(), innerArray.length), 
				getParameterToken(), getParameterName(), getParameterDescription());
	}

	@Override
	public String getValue() {

		String _res = "" + innerArray.length;
		for (M _element : innerArray){
			_res = " " + _element.getValue();
		}
		return _res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(String str_to_parse) throws DataValueParsingException {
		try{
			int _read_index = Pattern.compile(INTEGER_REGEXP).matcher(str_to_parse).end();
			int _array_length = Integer.parseInt(str_to_parse.substring(0, _read_index));
			
			String _element_regexp = getSupportedClass().newInstance().getRegExp();
			
			if (innerArray.length != _array_length){
				innerArray = (M[]) Array.newInstance(getSupportedClass(), _array_length);
				for (int _i=0; _i<innerArray.length; _i++){
					innerArray[_i] = getSupportedClass().newInstance();
				}
			}
			String _read_str = str_to_parse; 
			for (int _i=0; _i<_array_length; _i++){
				_read_str = _read_str.substring(_read_index);
				innerArray[_i].setValue(_read_str);
				_read_index = Pattern.compile(_element_regexp).matcher(_read_str).end();
			}

		}
		catch(NumberFormatException _e){
			throw new DataValueParsingException("an integer value : ", str_to_parse, "cannot parse the array length", _e);
		}
		catch(IllegalAccessException _e){
			throw new DataValueParsingException("illegal access", str_to_parse, "Cannot create a new instance of supported data type", _e);
		}
		catch(InstantiationException _e){
			throw new DataValueParsingException("instantiation", str_to_parse, "Cannot create a new instance of supported data type", _e);
		}
	}

	@Override
	public String getRegExp() {
		if (innerArray.length == 0){
			return "0";
		}
		else{
			return INTEGER_REGEXP + " (" + innerArray[0].getRegExp() + "){"+ innerArray.length +"}";
		}
	}
	
	/**
	 * Grants read/write access to the internal {@link Modifiable} array.
	 * @return the internal {@link Modifiable} array.
	 */
	public M[] accessArray(){
		return innerArray;
	}

	@SuppressWarnings("unchecked")
	private Class<M> getSupportedClass(){
		ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<M>) superClass.getActualTypeArguments()[0];
	}
}
