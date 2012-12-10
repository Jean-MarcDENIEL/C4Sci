package c4sci.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.exceptions.CannotInstantiateDataException;

/**
 * This class is intended at creating {@link HierarchicalData} instances given certain tokens.<br>
 * This class is useful for {@link HierarchicalData} to create their sub data instances.<br>
 * This class accepts factory ability adding to support {@link HierarchicalData} inheritance. 
 * @author jeanmarc.deniel
 *
 */
@SuppressWarnings("rawtypes")
public class HierarchialDataFactory {
	
	private Map<String, Class> tokenSubdataMap;
	
	public HierarchialDataFactory(){
		tokenSubdataMap = new ConcurrentHashMap<String, Class>();
	}
	/**
	 * Creates a sub data and appends it to its parent data.
	 * @param parent_data The parent data.
	 * @param subdata_token The token corresponding to the sub data type.
	 * @return The sub data linked to its parent and vice versa.
	 * @throws CannotInstantiateDataException in the case the sub data type cannot be instantiated as convertible to {@link HierarchicalData}.
	 */
	public HierarchicalData produceData(HierarchicalData parent_data, String subdata_token) throws CannotInstantiateDataException{
		Class _producer = tokenSubdataMap.get(subdata_token);
		if (_producer == null){
			throw new CannotInstantiateDataException(parent_data, subdata_token, "No such token");
		}
		try {
			HierarchicalData _instance = (HierarchicalData)(_producer.newInstance());
			if (HierarchicalData.class.isInstance(_instance)){
				HierarchicalData _res = (HierarchicalData)_instance;
				parent_data.addSubData(_res);
				return _res;
			}
			else{
				throw new CannotInstantiateDataException(parent_data, subdata_token, "Subdata cannot be converted to HierarchicalData");
			}
		} catch (InstantiationException _e) {
			throw new CannotInstantiateDataException(parent_data, subdata_token, "Cannot instantiate", _e);
		} catch (IllegalAccessException _e) {
			throw new CannotInstantiateDataException(parent_data, subdata_token, "Illegal access", _e);
		}
	}
	/**
	 * Adds the ability to create new kinds of data corresponding to certain token.
	 * @param subdata_class The data type to create.
	 * @param corresponding_token The token associated with the class type.
	 */
	public void addFactoringAbility(Class subdata_class, String corresponding_token){
		tokenSubdataMap.put(corresponding_token, subdata_class);
	}
	/**
	 * Appends all the factoring abilities of the parameter to this' abilities.
	 * @param data_factory A {@link HierarchialDataFactory} whose factoring abilities will be added to this.
	 */
	public void appendFactoringAbilities(HierarchialDataFactory data_factory){
		tokenSubdataMap.putAll(data_factory.tokenSubdataMap);
	}
}
