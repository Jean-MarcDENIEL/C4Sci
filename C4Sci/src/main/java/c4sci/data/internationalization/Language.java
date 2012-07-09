package c4sci.data.internationalization;

public class Language {
	public Language(String lang_symb, String english_name, String local_name){
		languageSymbol	=	lang_symb;
		englishName		= 	english_name;
		localName		= 	local_name;
	}
	private String languageSymbol;
	private String englishName;
	private String localName;
	
	public static final Language TAB_BASIC_LANGUAGES[] ={
		new Language("EN", "english", "english"), 
		new Language("FR", "french", "français")}; 

	public final String getLanguageSymbol(){
		return languageSymbol;
	}
	public final String getEnglishName(){
		return englishName;
	}
	public final String getLocalName(){
		return localName;
	}
	
	public static final String ENGLISH_SYMBOL = "EN";
}
