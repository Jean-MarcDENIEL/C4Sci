package c4sci.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.exceptions.CannotInstantiateParameterException;

/**
 * This class creates DataParameter instances according to HierarchicalData and DataParameter tokens.<br> 
 * <b>Pattern</b> This class exploits the GoF Prototype pattern.
 * @author jeanmarc.deniel
 *
 */
public class DataParameterFactory {
	private Map<String, DataParameter> parameterPrototypeMap;

	public DataParameterFactory(){
		parameterPrototypeMap = new ConcurrentHashMap <String, DataParameter>();
	}

	/**
	 * Links a DataParameter class to a parameter token.<br>
	 * @param parameter_token The HierarchicalData token
	 * @param prototype_instance The prototype class able to create a {@link DataParameter} instance
	 */
	public void addFactoringAbility(String parameter_token, DataParameter prototype_instance){
		if ((parameter_token == null)|| (prototype_instance == null)){
			return;
		}
		parameterPrototypeMap.put(parameter_token, prototype_instance);

	}

	public DataParameter createDataParameter(String parameter_token) throws CannotInstantiateParameterException{
		DataParameter _prototype = parameterPrototypeMap.get(parameter_token);
		if (_prototype == null){
			throw new CannotInstantiateParameterException(parameter_token, "unknown parameter token");
		}

		DataParameter _instance = (DataParameter) _prototype.getSameDataParameterInstance();
		_instance.setParameterToken(parameter_token);
		return _instance;
	}
}
