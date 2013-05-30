package c4sci.data;

import java.util.regex.Pattern;

import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class encapsulates simple data and access them through String values.<br>
 * <b>Important about string values :<b>
 * <ul>
 * 	<li> </li>
 * </ul>
 * 
 * Parameters are bound to be used in more complete classes.
 * @author jeanmarc.deniel
 *
 */
public abstract class DataParameter implements Modifiable {
	private String					paramToken;
	private InternationalizableTerm	paramName;
	private InternationalizableTerm	paramDescription;

	public DataParameter(){
		paramToken 			= "no token";
		paramName 			= new InternationalizableTerm("no name");
		paramDescription 	= new InternationalizableTerm("no description");
	}
	public DataParameter(String token_str, InternationalizableTerm name_term, InternationalizableTerm descr_term){
		paramToken			= token_str;
		paramName			= name_term;
		paramDescription	= descr_term;
	}
	/**
	 * @return It should a string of type [a-z]([A-Z][a-z]*)*
	 */
	public final String getParameterToken() {
		return paramToken;
	}
	/**
	 * Set the parameter token
	 * @param param_token The token associated with the parameter
	 */
	public final void setParameterToken(String param_token){
		paramToken = param_token;
	}
	/**
	 * 
	 * @return The name to associate with the parameter in containers
	 */
	public final InternationalizableTerm getParameterName() {
		return paramName;
	}
	/**
	 * Sets the parameter name.
	 * @param param_name The new parameter name.
	 */
	public final void setParameterName(InternationalizableTerm param_name){
		paramName = param_name;
	}
	/**
	 * 
	 * @return A string describing the parameter
	 */
	public final InternationalizableTerm getParameterDescription() {
		return paramDescription;
	}
	public final void setParameterDescription(InternationalizableTerm param_descr){
		paramDescription = param_descr;
	}
	/**
	 * Gives a string representation of the internal state of the {@link DataParameter}.
	 * @return A String that could be parsed to retrieve the real parameter value.
	 */
	public abstract String getValue();

	/** 
	 * Parses a String to set the {@link DataParameter} value.
	 * @throws DataValueParsingException if str_to_parse is null or cannot be parsed successfully
	 */
	public abstract void setValue(String str_to_parse) throws DataValueParsingException;
	/**
	 * Test whereas a string could be successfully parsed to set the DataParameter value.
	 * @param str_to_parse The string to parse.
	 * @return <i>true</i> if the string can be successfully parsed to set the DataParameter value. <br>
	 * <i>false</i> if the argument is null or {@link #setValue(String)} would throw a {@link DataValueParsingException}. 
	 */
	public boolean validatesRegularExpression(String str_to_parse){
		if (str_to_parse == null){
			return false;
		}
		return Pattern.matches(getRegExp(), str_to_parse);
	}
	/**
	 * Indicates the regular expression representing the acceptable string to parse to set the {@link DataParameter} inner state.
	 * @return The regular expression corresponding to the valid parameter entries.
	 */
	public abstract String getRegExp();
	/**
	 * <b>Pattern</b> This method is part of the Template Method GoF pattern.<br>
	 * Only DataParameter values must be copied from "this".
	 * @return a DataParameter of the same type then "this", with same DataParameter fields value.
	 */
	protected abstract DataParameter getSameDataParameterInstance() throws InstantiationException, IllegalAccessException;
	/**
	 * <b>Pattern</b> This method instantiates the Prototype GoF pattern.<br>
	 * <b>Pattern</b> This method uses the Template Method pattern<br>
	 * @see DataParameter#getSameDataParameterInstance()
	 * @return a clone of this, with same Parameter value
	 */
	public final DataParameter getClone() throws InstantiationException, IllegalAccessException{
		DataParameter _res = getSameDataParameterInstance();
		try {
			_res.setValue(getValue());
		} catch (DataValueParsingException _e) {
			// should never happen as getParameterValue() return value must be parsed by setParameterValue() 
			_e.printStackTrace();
		}
		return _res;
	}
}
