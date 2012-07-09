package c4sci.data.internationalization;

public class NoCorrespondingLanguageException extends Exception{
	public NoCorrespondingLanguageException(String lang_symbol){
		super(lang_symbol);
	}
}
