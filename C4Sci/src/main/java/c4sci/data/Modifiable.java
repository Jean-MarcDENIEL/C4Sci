package c4sci.data;

import c4sci.data.exceptions.DataValueParsingException;
/**
 * This interface describes data which value can be read and written through String expressions.
 * 
 * @author jeanmarc.deniel
 *
 */
public interface Modifiable {
	/**
	 * 
	 * @return A String that could be parsed to retrieve the real parameter value.
	 */
	String getValue();
	/** 
	 * Parses a String to set the value.
	 * @throws DataValueParsingException if str_to_parse is null or cannot be parsed successfully
	 */
	void setValue(String str_to_parse) throws DataValueParsingException;
	/**
	 * Test whereas a string could be successfully parsed to set the DataParameter value.
	 * @param str_to_parse The string to parse.
	 * @return <i>true</i> if the string can be successfully parsed to set the DataParameter value. <br>
	 * <i>false</i> if the argument is null or {@link #setValue(String)} would throw a {@link DataValueParsingException}. 
	 */
	boolean validatesRegularExpression(String str_to_parse);
	/**
	 * 
	 * @return The regular expression corresponding to the valid parameter entries.
	 */
	String getRegExp();
}
