package c4sci.data;

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
	 * The name to associate with the parameter in containers
	 * @return
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
	abstract public String getParameterValue();

	/** 
	 * Parses a String to set the value.
	 * @throws DataValueParsingException
	 */
	abstract void setParameterValue(String str_to_parse) throws DataValueParsingException;

}
