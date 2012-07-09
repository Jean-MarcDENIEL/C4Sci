package c4sci.data.internationalization;

import java.util.HashMap;
import java.util.Map;

/**
 * This class gives several translations of a same information
 * @author jeanmarc.deniel
 *
 */
public class InternationalizableTerm {
	public InternationalizableTerm(){
		valueMap = new HashMap<Language, String>;
	}
	/**
	 * 
	 * @return The default (English) term
	 */
	public String getDefaultValue(){
		try{
			return valueMap.get(Languages.getLanguage(Language.ENGLISH_SYMBOL));
		}
		catch (NoCorrespondingLanguageException _e){
			return "(No term)";
		}
	}
	
	/**
	 * Gets the term corresponding to the specified language
	 * @param express_language
	 * @return
	 * @throws NoCorrespondingLanguageException
	 */
	public String getValue(Language express_language) throws NoCorrespondingLanguageException{
		if (!valueMap.containsKey(express_language)){
			throw new NoCorrespondingLanguageException(express_language.getLanguageSymbol(), "No translation");
		}
		return valueMap.get(express_language);
	}
	/**
	 * Sets the term corresponding to the specified language
	 * @param express_language
	 * @param term_value
	 */
	public void setValue(Language express_language, String term_value){
		valueMap.put(express_language, term_value);
	}
	
	/**
	 * Language symbol / value
	 */
	private Map<Language, String>	valueMap;
}
