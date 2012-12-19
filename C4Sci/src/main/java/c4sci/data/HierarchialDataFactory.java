package c4sci.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.exceptions.CannotInstantiateDataException;

/**
 * This class is intended at creating {@link HierarchicalData} instances given certain tokens.<br>
 * This class is useful for {@link HierarchicalData} to create their sub data instances.<br>
 * This class accepts factory ability adding to support {@link HierarchicalData} inheritance. <br>
 * <br>
 * <b>Pattern</b> : To avoid access issues to constructors, this class uses the <b>Prototype</b> GOF pattern.
 * 
 * @author jeanmarc.deniel
 *
 */
@SuppressWarnings("rawtypes")
public class HierarchialDataFactory {

	private Map<String, PrototypeData> tokenSubdataMap;

	public HierarchialDataFactory(){
		tokenSubdataMap = new ConcurrentHashMap<String, PrototypeData>();
	}
	/**
	 * Creates a sub data and appends it to its parent data.
	 * @param parent_data The parent data.
	 * @param subdata_token The token corresponding to the sub data type.
	 * @return The sub data linked to its parent and vice versa.
	 * @throws CannotInstantiateDataException in the case the sub data type cannot be instantiated as convertible to {@link HierarchicalData}.
	 */
	public HierarchicalData produceData(HierarchicalData parent_data, String subdata_token) throws CannotInstantiateDataException{
		PrototypeData _prototype = tokenSubdataMap.get(subdata_token);
		if (_prototype == null){
			throw new CannotInstantiateDataException(parent_data, subdata_token, "No such token");
		}
		try{
		PrototypeData _instance = _prototype.newInstance();
		if (HierarchicalData.class.isInstance(_instance)){
			HierarchicalData _res = (HierarchicalData)_instance;
			_res.setDataToken(subdata_token);
			if (parent_data != null){
				parent_data.addSubData(_res);
			}
			return _res;
		}
		else{
			throw new CannotInstantiateDataException(parent_data, subdata_token, "Subdata cannot be converted to HierarchicalData");
		}
		}
		catch(CannotInstantiateDataException _e){
			throw new CannotInstantiateDataException(parent_data, subdata_token, "newInstance exception", _e);
		}
	}
	/**
	 * Adds the ability to create new kinds of data corresponding to certain token.
	 * @param subdata_prototype The data type to create.
	 * @param corresponding_token The token associated with the class type.
	 */
	public void addFactoringAbility(PrototypeData subdata_prototype, String corresponding_token){
		tokenSubdataMap.put(corresponding_token, subdata_prototype);
	}
	/**
	 * Appends all the factoring abilities of the parameter to this' abilities.
	 * @param data_factory A {@link HierarchialDataFactory} whose factoring abilities will be added to this.
	 */
	public void appendFactoringAbilities(HierarchialDataFactory data_factory){
		tokenSubdataMap.putAll(data_factory.tokenSubdataMap);
	}
}
