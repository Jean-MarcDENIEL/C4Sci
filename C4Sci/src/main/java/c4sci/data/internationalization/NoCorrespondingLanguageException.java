package c4sci.data.internationalization;

public class NoCorrespondingLanguageException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6793506537647595361L;

	public NoCorrespondingLanguageException(String lang_symbol, String err_msg){
		super(err_msg);
		languageSymbol	= lang_symbol;
	}
	private String languageSymbol;
	
	public String getLanguageSymbol(){
		return languageSymbol;
	}
	
	public String getMessage(){
		return super.getMessage() + " with language " + getLanguageSymbol();
	}
}
