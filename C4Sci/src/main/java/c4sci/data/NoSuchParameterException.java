package c4sci.data;

public class NoSuchParameterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6560243073429138551L;
	private String parameterToken;
	public NoSuchParameterException(String param_token, String err_message){
		super(err_message);
		parameterToken = param_token;
	}
	public String getParameterToken(){
		return parameterToken;
	}
}
