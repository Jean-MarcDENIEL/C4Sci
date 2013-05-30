package c4sci.data;

import java.util.regex.Pattern;

import c4sci.data.exceptions.DataValueParsingException;
/**
 * This interface describes data which value can be read and written through String expressions.
 * 
 * @author jeanmarc.deniel
 *
 */
public abstract class Modifiable {
	/**
	 * 
	 * @return A String that could be parsed to retrieve the real parameter value.
	 */
	public abstract String getValue();
	/**
	 * Change the inner state by parsing the String argument.
	 * @param str_to_parse that should not be null.
	 * @throws DataValueParsingException
	 */
	public abstract void setValue(String str_to_parse) throws DataValueParsingException;
	
	/** 
	 * Parses a String to set the value.
	 * @return the length of the useful part of the string parameter to set state.
	 * @throws DataValueParsingException if str_to_parse is null or cannot be parsed successfully
	 */
	public int parseValue(String str_to_parse) throws DataValueParsingException {
		if (str_to_parse == null){
			throw new DataValueParsingException("Boolean", "(null)", "parsing error : null argument", null);
		}
		setValue(str_to_parse);
		return Pattern.compile(getRegExp()).split(str_to_parse)[0].length();
	}
	/**
	 * Test whereas a string could be successfully parsed to set the DataParameter value.
	 * @param str_to_parse The string to parse.
	 * @return <i>true</i> if the string can be successfully parsed to set the DataParameter value. <br>
	 * <i>false</i> if the argument is null or {@link #parseValue(String)} would throw a {@link DataValueParsingException}. 
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
}
