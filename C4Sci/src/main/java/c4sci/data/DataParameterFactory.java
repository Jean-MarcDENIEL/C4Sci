package c4sci.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.exceptions.CannotInstantiateParameterException;

/**
 * This class creates DataParameter instances according to HierarchicalData and DataParameter tokens.<br> 
 * @author jeanmarc.deniel
 *
 */
@SuppressWarnings("rawtypes")
public class DataParameterFactory {
	private Map<String, Class> parameterTypeMap;

	public DataParameterFactory(){
		parameterTypeMap = new ConcurrentHashMap <String, Class>();
	}

	/**
	 * Links a DataParameter class to a parameter token.<br>
	 * @param parameter_token The HierarchicalData token
	 * @param prototype_class The prototype class able to create a {@link DataParameter} instance
	 */
	public void addFactoringAbility(String parameter_token, Class prototype_class){
		if ((parameter_token == null)|| (prototype_class == null)){
			return;
		}
		parameterTypeMap.put(parameter_token, prototype_class);

	}
	
	public DataParameter createDataParameter(String parameter_token) throws CannotInstantiateParameterException{
		Class _prototype = parameterTypeMap.get(parameter_token);
		if (_prototype == null){
			throw new CannotInstantiateParameterException(parameter_token, "unknown parameter token");
		}
		try{
			DataParameter _instance = (DataParameter) _prototype.newInstance();
			_instance.setParameterToken(parameter_token);
			return _instance;
		}
		catch (InstantiationException _e){
			throw new CannotInstantiateParameterException(parameter_token, "instantiation exception", _e);
		}
		catch(IllegalAccessException _e){
			throw new CannotInstantiateParameterException(parameter_token, "illegal acces", _e); 
		}
		catch(ClassCastException _e){
			throw new CannotInstantiateParameterException(parameter_token, _prototype.getName() + "does not inherit from DataParameter type", _e);
		}

	}
}
