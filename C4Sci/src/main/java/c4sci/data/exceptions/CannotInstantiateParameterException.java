package c4sci.data.exceptions;

public class CannotInstantiateParameterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6560243073429138551L;
	private String parameterToken;
	public CannotInstantiateParameterException(String param_token, String err_message){
		super(err_message);
		parameterToken = param_token;
	}
	public CannotInstantiateParameterException(String param_token, String err_message, Throwable cause_exception){
		super(err_message, cause_exception);
		parameterToken = param_token;
	}
	public String getParameterToken(){
		return parameterToken;
	}
}
