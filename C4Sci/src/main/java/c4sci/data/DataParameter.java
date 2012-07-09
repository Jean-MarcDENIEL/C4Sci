package c4sci.data;

import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class encapsulates simple data and access them through String values.
 * Parameters are bound to be used in more complete classes.
 * @author jeanmarc.deniel
 *
 */
public interface DataParameter {
	String getParameterValue();
	/**
	 * @return It should a string of type [a-z]([A-Z][a-z]*)*
	 */
	String getParameterToken();
	/**
	 * The name to associate with the parameter in containers
	 * @return
	 */
	InternationalizableTerm getParameterName();
	/**
	 * 
	 * @return A string describing the parameter
	 */
	InternationalizableTerm getParameterDescription();
	/**
	 * Parses a String to set the value.
	 * @throws DataValueParsingException
	 */
	void setParameterValue(String str_to_parse) throws DataValueParsingException;
}
