package c4sci.data.internationalization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class gives several translations of a same information
 * @author jeanmarc.deniel
 *
 */
public class InternationalizableTerm {
	/**
	 * Language symbol / value
	 */
	private Map<Language, String>	valueMap;
	
	@SuppressWarnings("unused")
	private InternationalizableTerm(){}
	public InternationalizableTerm(String default_en_value){
		valueMap = new ConcurrentHashMap<Language,String>();
		try {
			valueMap.put(Languages.getLanguage(Language.ENGLISH_SYMBOL), default_en_value);
		} catch (NoCorrespondingLanguageException _e) {
			// this should never happen
		}
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
			// this should never happen
			return "no term";
		}
	}
	
	/**
	 * Gets the term corresponding to the specified language
	 * @param express_language
	 * @return the term corresponding to the specified language
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
	

}
