package c4sci.io.serial;

public class SerialStateParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5539219428447207524L;
	public SerialStateParsingException(String excp_msg){
		super(excp_msg);
	}
	public SerialStateParsingException(String excp_msg, Throwable exception_cause){
		super(excp_msg, exception_cause);
	}

}
