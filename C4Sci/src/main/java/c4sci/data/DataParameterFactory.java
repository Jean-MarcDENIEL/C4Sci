package c4sci.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class creates DataParameter instances according to HierarchicalData and DataParameter tokens.<br>
 * <b>Pattern</b> This class uses the GoF Prototype pattern.  
 * @author jeanmarc.deniel
 *
 */
public class DataParameterFactory {
	private Map<String, Map<String, DataParameter>> dataFactoryMap;

	public DataParameterFactory(){
		dataFactoryMap = new ConcurrentHashMap<String, Map<String, DataParameter>>();
	}

	/**
	 * Links a DataParameter prototype to data and parameter tokens.<br>
	 *
	 * @see DataParameter#getClone() DataParameter cloning ability
	 * @param data_token The HierarchicalData token
	 * @param prototype_parameter The prototype to clone. It contains the parameter token associated with the data
	 */
	public void addDataParameterPrototype(String data_token, DataParameter prototype_parameter){
		if ((data_token == null)||
				(prototype_parameter == null)){
			return;
		}
		Map<String, DataParameter> _param_map;

		_param_map = dataFactoryMap.get(data_token);
		if (_param_map == null){
			_param_map = new ConcurrentHashMap<String, DataParameter>();
			dataFactoryMap.put(data_token, _param_map);
		}
		_param_map.put(prototype_parameter.getParameterToken(), prototype_parameter);

	}
	public DataParameter createDataParameter(String data_token, String parameter_token) throws NoSuchDataParameterExistsException{
		Map<String, DataParameter> _param_map;

		_param_map = dataFactoryMap.get(data_token);

		if (_param_map == null){
			throw new NoSuchDataParameterExistsException(data_token, parameter_token, null);
		}
		DataParameter _prototype = _param_map.get(parameter_token);
		if (_prototype == null){
			throw new NoSuchDataParameterExistsException(data_token, parameter_token, null);
		}
		return _prototype.getClone();
	}
}
