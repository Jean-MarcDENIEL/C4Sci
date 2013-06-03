package c4sci.data.dataParameters.basicModifiables;

import c4sci.data.Modifiable;
import c4sci.data.exceptions.DataValueParsingException;

/**
 * This class represents a string value manipulated through strings values.
 * The inner state does not contain any white space. These are replaced by underscores.
 * 
 * Conversion to/from string containing white spaces are possible through dedicated methods.
 * In these cases, underscores in inner state are considered as white spaces.
 * @author jeanmarc.deniel
 *
 */
public final class NoWhiteSpaceStringModifiable extends Modifiable {

	/**
	 * Converts a string that may contain white spaces to a string that is compatible with the class inner state and {@link DataParameter} functionalities.
	 * @param a_str_containing_white_spaces A string that can contain white spaces 
	 * @return a string corresponding to the parameter, where every white space has been turned to an underscore.
	 */
	public static String convertToNowhiteSpaceString(String a_str_containing_white_spaces){
		StringBuffer _buffer = new StringBuffer(a_str_containing_white_spaces);
		for (int _i=0; _i<_buffer.length(); _i++){
			if (_buffer.charAt(_i) == ' '){
				_buffer.setCharAt(_i, '_');
			}
		}
		return _buffer.toString();
	}
	
	public static String convertsFromNoWhiteSpaceString(String a_string_withtout_white_space){
		StringBuffer _buffer = new StringBuffer(a_string_withtout_white_space);
		for (int _i=0; _i<_buffer.length(); _i++){
			if (_buffer.charAt(_i) == '_'){
				_buffer.setCharAt(_i, ' ');
			}
		}
		return _buffer.toString();
	}
	
	private String innerState;
	private static final String		DEFAULT_VALUE = "no value";
	
	public NoWhiteSpaceStringModifiable(){
		innerState = DEFAULT_VALUE;
	}
	
	@Override
	public synchronized String getValue() {
		return innerState;
	}

	@Override
	/**
	 * Beware : the string parameter should not contain any whitespace otherwise disorders may happen.
	 */
	public synchronized void setValue(String str_to_parse) throws DataValueParsingException {
		innerState = str_to_parse;
	}

	@Override
	public String getRegExp() {
		return "[^\\s]*";
	}
	
	/**
	 * 
	 * @return A string that may contain white spaces converted from underscores.
	 */
	public synchronized String getStringValue(){
		return convertsFromNoWhiteSpaceString(innerState);
	}
	/**
	 * 
	 * @param str_val a non null string. It may contain white spaces that will be converted to underscores.
	 */
	public synchronized void setStringValue(String str_val){
		innerState = convertToNowhiteSpaceString(str_val);
	}

}
