package c4sci.data;

import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class encapsulates simple data and access them through String values.<br>
 * <b>Important about string values :<b>
 * <ul>
 * 	<li> </li>
 * </ul>
 * 
 * Parameters are bound to be used in more complete classes.
 * @author jeanmarc.deniel
 *
 */
public abstract class DataParameter extends Modifiable {
	private String					paramToken;
	private InternationalizableTerm	paramName;
	private InternationalizableTerm	paramDescription;

	public DataParameter(){
		paramToken 			= "no token";
		paramName 			= new InternationalizableTerm("no name");
		paramDescription 	= new InternationalizableTerm("no description");
	}
	public DataParameter(String token_str, InternationalizableTerm name_term, InternationalizableTerm descr_term){
		paramToken			= token_str;
		paramName			= name_term;
		paramDescription	= descr_term;
	}
	/**
	 * @return It should a string of type [a-z]([A-Z][a-z]*)*
	 */
	public final String getParameterToken() {
		return paramToken;
	}
	/**
	 * Set the parameter token
	 * @param param_token The token associated with the parameter
	 */
	public final void setParameterToken(String param_token){
		paramToken = param_token;
	}
	/**
	 * 
	 * @return The name to associate with the parameter in containers
	 */
	public final InternationalizableTerm getParameterName() {
		return paramName;
	}
	/**
	 * Sets the parameter name.
	 * @param param_name The new parameter name.
	 */
	public final void setParameterName(InternationalizableTerm param_name){
		paramName = param_name;
	}
	/**
	 * 
	 * @return A string describing the parameter
	 */
	public final InternationalizableTerm getParameterDescription() {
		return paramDescription;
	}
	public final void setParameterDescription(InternationalizableTerm param_descr){
		paramDescription = param_descr;
	}

	/**
	 * <b>Pattern</b> This method is part of the Template Method GoF pattern.<br>
	 * Only DataParameter values must be copied from "this".
	 * @return a DataParameter of the same type then "this", with same DataParameter fields value.
	 */
	protected abstract DataParameter getSameDataParameterInstance() throws CannotInstantiateParameterException;
	/**
	 * <b>Pattern</b> This method instantiates the Prototype GoF pattern.<br>
	 * <b>Pattern</b> This method uses the Template Method pattern<br>
	 * @see DataParameter#getSameDataParameterInstance()
	 * @return a clone of this, with same Parameter value
	 */
	public final DataParameter getClone() throws CannotInstantiateParameterException{
		DataParameter _res = getSameDataParameterInstance();
		try {
			_res.setValue(getValue());
		} catch (DataValueParsingException _e) {
			// should never happen as getParameterValue() return value must be parsed by setParameterValue() 
			_e.printStackTrace();
		}
		return _res;
	}
}
