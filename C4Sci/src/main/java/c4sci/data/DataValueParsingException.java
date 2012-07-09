package c4sci.data;

public class DataValueParsingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -235729879274861932L;
	private String		expectedValueType;
	private String		stringToParse;
	
	@SuppressWarnings("unused")
	private DataValueParsingException() {}
	
	public DataValueParsingException(String expected_value, String string_to_parse, String err_msg){
		super(err_msg);
		expectedValueType 	= expected_value;
		stringToParse		= string_to_parse;
		
	}
	public String getExpectedValueInformation(){
		return expectedValueType;
	}
	public String getStringToParse(){
		return stringToParse;
	}
	
}
