package c4sci.data.internationalization;

import java.util.HashMap;

public class Languages {
	
	private static HashMap<String, Language>languagesMap = createBasicLanguages();
	
	private static HashMap<String, Language> createBasicLanguages(){
		HashMap<String, Language> _res = new HashMap<String, Language>();
		for (Language _l : Language.TAB_BASIC_LANGUAGES){
			_res.put(_l.getLanguageSymbol(), _l);
		}
		return _res;
	}
	private String languageSymbol;
	private String englishName;
	private String localName;
	
	public final String getLanguageSymbol(){
		return languageSymbol;
	}
	public final String getEnglishName(){
		return englishName;
	}
	public final String getLocalName(){
		return localName;
	}
	
	public static void addNewLanguage(Language l_to_add){
		languagesMap.put(l_to_add.getLanguageSymbol(), l_to_add);
	}
	
	/**
	 * 
	 * @param lang_symbol "EN", "FR" ....
	 * @throws NoCorrespondingLanguageException if there is no correspondence between lang_symbol and a known language
	 */
	public static Language getLanguage(String lang_symbol) throws NoCorrespondingLanguageException{
		if (!languagesMap.containsKey(lang_symbol)){
			throw new NoCorrespondingLanguageException(lang_symbol, "No corresponding language!");
		}
		return languagesMap.get(lang_symbol);
	}
}
