package c4sci.data;

import java.util.regex.Pattern;

import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class encapsulates simple data and access them through String values.
 * Parameters are bound to be used in more complete classes.
 * @author jeanmarc.deniel
 *
 */
public abstract class DataParameter {
	private String					paramToken;
	private InternationalizableTerm	paramName;
	private InternationalizableTerm	paramDescription;

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
	 * 
	 * @return The name to associate with the parameter in containers
	 */
	public final InternationalizableTerm getParameterName() {
		return paramName;
	}
	/**
	 * 
	 * @return A string describing the parameter
	 */
	public final InternationalizableTerm getParameterDescription() {
		return paramDescription;
	}
	/**
	 * 
	 * @return A String that could be parsed to retrieve the real parameter value.
	 */
	public abstract String getParameterValue();

	/** 
	 * Parses a String to set the value.
	 * @throws DataValueParsingException if str_to_parse is null or cannot be parsed successfully
	 */
	public abstract void setParameterValue(String str_to_parse) throws DataValueParsingException;
	/**
	 * Test whereas a string could be successfully parsed to set the DataParameter value.
	 * @param str_to_parse The string to parse.
	 * @return <i>true</i> if the string can be successfully parsed to set the DataParameter value. <br>
	 * <i>false</i> if the argument is null or {@link #setParameterValue(String)} would throw a {@link DataValueParsingException}. 
	 */
	public boolean validatesRegularExpression(String str_to_parse){
		if (str_to_parse == null){
			return false;
		}
		return Pattern.matches(getRegExp(), str_to_parse);
	}
	/**
	 * 
	 * @return The regular expression corresponding to the valid parameter entries.
	 */
	public abstract String getRegExp();
	/**
	 * <b>Pattern</b> This method is part of the Template Method GoF pattern.<br>
	 * Only DataParameter values must be copied from "this".
	 * @return a DataParameter of the same type then "this", with same DataParameter fields value.
	 */
	protected abstract DataParameter getSameDataParameterInstance();
	/**
	 * <b>Pattern</b> This method instantiates the Prototype GoF pattern.<br>
	 * <b>Pattern</b> This method uses the Template Method pattern<br>
	 * @see DataParameter#getSameDataParameterInstance()
	 * @return a clone of this, with same Parameter value
	 */
	public final DataParameter getClone(){
		DataParameter _res = getSameDataParameterInstance();
		try {
			_res.setParameterValue(getParameterValue());
		} catch (DataValueParsingException _e) {
			// should never happen as getParameterValue() return value must be parsed by setParameterValue() 
			_e.printStackTrace();
		}
		return _res;
	}
}
