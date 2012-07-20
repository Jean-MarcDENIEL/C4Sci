package c4sci.data.internationalization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Languages {
	private Languages(){}
	private static final  Map<String, Language>LANGUAGES_BASIC_MAP = createBasicLanguages();
	
	private static Map<String, Language> createBasicLanguages(){
		Map<String, Language> _res = new ConcurrentHashMap<String, Language>();
		for (Language _l : Language.TAB_BASIC_LANGUAGES){
			_res.put(_l.getLanguageSymbol(), _l);
		}
		return _res;
	}


	public static void addNewLanguage(Language l_to_add){
		LANGUAGES_BASIC_MAP.put(l_to_add.getLanguageSymbol(), l_to_add);
	}
	
	/**
	 * 
	 * @param lang_symbol "EN", "FR" ....
	 * @throws NoCorrespondingLanguageException if there is no correspondence between lang_symbol and a known language
	 */
	public static Language getLanguage(String lang_symbol) throws NoCorrespondingLanguageException{
		if (!LANGUAGES_BASIC_MAP.containsKey(lang_symbol)){
			throw new NoCorrespondingLanguageException(lang_symbol, "No corresponding language!");
		}
		return LANGUAGES_BASIC_MAP.get(lang_symbol);
	}
}
