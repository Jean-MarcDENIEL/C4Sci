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
		
		ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
		Class<M> type = (Class<M>) superClass.getActualTypeArguments()[0];
		
		for (int _i=0; _i<innerArray.length; _i++){
			if (innerArray[_i] == null){
				try {
					innerArray[_i] = type.newInstance();
				} catch (InstantiationException  _e) {
					throw new CannotInstantiateParameterException(token_str, "Cannot instantiate " + type.getName(), _e);
				} catch (IllegalAccessException _e) {
					throw new CannotInstantiateParameterException(token_str, "Cannot instantiate " + type.getName(), _e);
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

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(String str_to_parse) throws DataValueParsingException {
		try{
			int _array_length = Integer.parseInt(str_to_parse.substring(0, Pattern.compile(INTEGER_REGEXP).matcher(str_to_parse).end()));
			ICI
		}
		catch(NumberFormatException _e){
			throw new DataValueParsingException("an integer value : ", str_to_parse, "cannot parse the array length", _e);
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

}
