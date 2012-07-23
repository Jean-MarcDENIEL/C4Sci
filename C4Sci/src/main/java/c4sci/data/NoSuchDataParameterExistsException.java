package c4sci.data;

public class NoSuchDataParameterExistsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8986582878921130894L;

	public NoSuchDataParameterExistsException(String data_token, String parameter_token, Throwable exception_cause){
		super("Data : "+data_token + " Parameter : "+parameter_token, exception_cause);
	}
}
