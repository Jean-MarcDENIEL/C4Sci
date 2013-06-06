package c4sci.data.dataParameters;

import java.lang.reflect.Array;
import java.util.regex.Pattern;

import c4sci.data.DataParameter;
import c4sci.data.Modifiable;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This generic class is intended are representing arrays of non null {@link Modifiable}s. <br>
 * <b>Warning : </b> In value strings, elements are separated by white spaces. That's why {@link Modifiable} elements should not have white spaces in their value strings, to avoid disorders.<br>
 * <b>Warning : </b> This class's regular expression takes its inner elements count into value.
 * @author jeanmarc.deniel
 *
 */
public class GenericArrayDataParameter<M extends Modifiable> extends DataParameter {

	private static final String		INTEGER_REGEXP 		= "(\\+|-)?\\d+" ;
	private static final String		WHITESPACE_REGEXP	= "\\s";
	private static final String		WHITESPACE_SEQ		= " ";
	
	
	private M[]			innerArray;
	private Class<M>	supportedClass;
	
	/**
	 * Creates the parameter array and ensures that the array has no null element. Its internal state will be c copy of the passed parameter
	 * @param inner_value The array of {@link Modifiable} elements. {@link #getClone()} and {@link #getSameDataParameterInstance()} results will be of that array length.
	 * @param token_str
	 * @param name_term
	 * @param descr_term
	 * @throws CannotInstantiateParameterException
	 */
	@SuppressWarnings("unchecked")
	public GenericArrayDataParameter(M[] inner_value, 
			String token_str,
			InternationalizableTerm name_term,
			InternationalizableTerm descr_term) throws CannotInstantiateParameterException {
		super(token_str, name_term, descr_term);

		supportedClass	= (Class<M>) inner_value.getClass().getComponentType();
		
		innerArray = (M[])Array.newInstance(getSupportedClass(), inner_value.length);
		for (int _i=0; _i<innerArray.length; _i++){
			try {
				innerArray[_i] = getSupportedClass().newInstance();
				if (inner_value[_i] != null){
					innerArray[_i].setValue(inner_value[_i].getValue());
				}
			} catch (InstantiationException  _e) {
				throw new CannotInstantiateParameterException(token_str, "Cannot instantiate " + getSupportedClass().getName(), _e);
			} catch (IllegalAccessException _e) {
				throw new CannotInstantiateParameterException(token_str, "Cannot access " + getSupportedClass().getName(), _e);
			} catch (DataValueParsingException _e) {
				throw new CannotInstantiateParameterException(token_str, "Cannot parse prototype " + getSupportedClass().getName(), _e);
			}
		}
	}
	
	/**
	 * @return an instance with same array size. The array is initialized so that it has no null element.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected DataParameter getSameDataParameterInstance() throws CannotInstantiateParameterException{
		return new GenericArrayDataParameter<M>((M[])Array.newInstance(getSupportedClass(), innerArray.length), 
				getParameterToken(), getParameterName(), getParameterDescription());
	}

	@Override
	public String getValue() {

		String _res = "" + innerArray.length;
		for (M _element : innerArray){
			_res += WHITESPACE_SEQ + _element.getValue();
		}
		return _res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(String str_to_parse) throws DataValueParsingException {
		try{
			
			Pattern _whitespace_pattern = Pattern.compile(WHITESPACE_REGEXP);
			String[] _sequences_tab = _whitespace_pattern.split(str_to_parse, 2);
			
			int _array_length = Integer.parseInt(_sequences_tab[0]);
			if (innerArray.length != _array_length){
				innerArray = (M[]) Array.newInstance(getSupportedClass(), _array_length);
				for (int _i=0; _i<innerArray.length; _i++){
					innerArray[_i] = getSupportedClass().newInstance();
				}
			}

			String _read_str = str_to_parse.substring(_sequences_tab[0].length()); 
			//System.out.println("array value string =" + _read_str);
			for (int _i=0; _i<_array_length; _i++){
				String _element_regexp = WHITESPACE_REGEXP + innerArray[_i].getRegExp();
				
				_sequences_tab = separateExpressions(_element_regexp, _read_str);
				innerArray[_i].setValue(_sequences_tab[0].substring(1));
				if (_sequences_tab.length>1){
					_read_str = _sequences_tab[1];
				}
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
	
	private String[] separateExpressions(String first_term_reg_exp, String str_to_parse) throws DataValueParsingException{
		String[] _separated_terms = Pattern.compile(first_term_reg_exp).split(str_to_parse, 2);
		
		switch(_separated_terms.length){
		case 0 :
			throw new DataValueParsingException(first_term_reg_exp, str_to_parse, "no matching", null);
		case 1 : 
			return new String[] {_separated_terms[0], ""};
		case 2 :
			int _first_term_length = str_to_parse.length() - _separated_terms[1].length();
			return new String[] {
					str_to_parse.substring(0, _first_term_length),
					_separated_terms[1]
			};
		default:
			throw new DataValueParsingException(first_term_reg_exp, str_to_parse, "splitting error", null);
		}
	}

	@Override
	public String getRegExp() {
		if (innerArray.length == 0){
			return "0";
		}
		else{
			return INTEGER_REGEXP + "(" + WHITESPACE_REGEXP + "(" + innerArray[0].getRegExp() + ")){"+ innerArray.length +"}";
			//return INTEGER_REGEXP + WHITESPACE_REGEXP + "(" + WHITESPACE_REGEXP + innerArray[0].getRegExp() + "){"+ innerArray.length +"}";
		}
	}
	
	/**
	 * Grants read/write access to the internal {@link Modifiable} array.
	 * @return the internal {@link Modifiable} array.
	 */
	public M accessElement(int elt_index){
		return innerArray[elt_index];
	}

	private Class<M> getSupportedClass(){
		return supportedClass;
	}
}
