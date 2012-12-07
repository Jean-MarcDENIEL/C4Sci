package c4sci.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.exceptions.NoSuchParameterException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class contains data and represents hierarchical structures at the same time.<br>
 * <ul>
 * <li>Internal data are DataParameters.
 * <li>Child Data are HierarchicalData.
 * </ul>
 * <br>
 * It is interesting to subclass {@link HierarchicalData} in order to ensure certain behaviors :
 * <ul>
 * <li>appending new data types associated to sub data tokens</li>
 * <li>ensuring low coupling with algorithms manipulating {@link HierarchicalData} like reading and writing data : simply append new factoring abilities representing accepted sub data</li>
 * </ul> 
 * Several children data can share the same token : there is no mapping.
 * @author jeanmarc.deniel
 *
 */
public class HierarchicalData implements VisitableData{

	private DataIdentity					dataIdentity;
	private String							dataToken;
	private InternationalizableTerm			dataName;
	private InternationalizableTerm			dataDescription;
	private Map<String,DataParameter>		parameterMap;
	private Set<HierarchicalData>			subDataSet;
	private HierarchicalData				parentData;


	private static Map<DataIdentity, HierarchicalData>	IDENTITY_DATA_MAP = new ConcurrentHashMap<DataIdentity, HierarchicalData>();

	/**
	 * 
	 * @param data_identity
	 * @return the HierarchicalData whose identity has been passed as argument, or null if no HierarchialData can be found
	 */
	public static HierarchicalData getIdentifiedData(DataIdentity data_identity){
		return IDENTITY_DATA_MAP.get(data_identity);
	}

	private DataIdentity createDataIdentity(){
		DataIdentity _ret = new DataIdentity();
		IDENTITY_DATA_MAP.put(_ret, this);
		return _ret;
	}

	public final void setDataIdentity(DataIdentity data_identity){
		IDENTITY_DATA_MAP.remove(dataIdentity);
		dataIdentity = data_identity;
		IDENTITY_DATA_MAP.put(dataIdentity, this);
	}

	public final DataIdentity getDataIdentity(){
		return dataIdentity;
	}

	/**
	 * Removes from the HierarchicalData retrieving mechanism.<br>
	 * This method can be called for every data that is not intended to be persistent. 
	 * Calling this method is necessary to achieve HierarchicalData garbage collecting.
	 */
	public final void forgetIdentity(){
		IDENTITY_DATA_MAP.remove(getDataIdentity());
	}

	@SuppressWarnings("unused")
	private HierarchicalData(){}
	public	HierarchicalData(String data_token, InternationalizableTerm data_name, InternationalizableTerm data_description){
		dataIdentity 	= createDataIdentity();
		dataToken		= data_token;
		dataName		= data_name;
		dataDescription	= data_description;
		parentData		= null;
		parameterMap	= new ConcurrentHashMap<String, DataParameter>();
		subDataSet		= new HashSet<HierarchicalData>();
	}
	public HierarchicalData(String data_token, InternationalizableTerm data_name, InternationalizableTerm data_description, HierarchicalData parent_data){
		dataIdentity 	= createDataIdentity();
		dataToken		= data_token;
		dataName		= data_name;
		dataDescription	= data_description;
		parentData		= parent_data;
		parameterMap	= new ConcurrentHashMap<String, DataParameter>();
		subDataSet		= new HashSet<HierarchicalData>();
	}

	/**
	 * 
	 * @return one word or continuous expression representing the data.<br>
	 * e.g soundQuality or vector_size
	 */
	public final String getDataToken(){
		return dataToken;
	}
	/**
	 * @return The complete name of the data.	
	 */
	public final InternationalizableTerm getDataName(){
		return dataName;
	}
	/**
	 * 
	 * @return The complete description of the data
	 */
	public final InternationalizableTerm getDataDescription(){
		return dataDescription;
	}

	/**
	 * Decorates the HierarchicalData with a new DataParameter<br><br>
	 * <b>Pattern : </b> GoF Decorator pattern
	 */
	protected final void addDataParameter(DataParameter data_param){
		parameterMap.put(data_param.getParameterToken(), data_param);
	}
	/**
	 * Access a DataParameter given its token<br><br>
	 * <b>Pattern : </b> GoF Decorator pattern
	 */

	public final void setParameterValue(String data_token, String value_to_parse) throws DataValueParsingException,  NoSuchParameterException{
		DataParameter _data_param = parameterMap.get(data_token);
		if (_data_param == null){
			throw new NoSuchParameterException(data_token, "in "+getDataName());
		}
		_data_param.setValue(value_to_parse);
	}
	/**
	 * Access a DataParameter given its token<br><br>
	 * <b>Pattern : </b> GoF Decorator pattern
	 */
	public final String getParameterValue(String data_token) throws NoSuchParameterException{
		DataParameter _data_param = parameterMap.get(data_token);
		if (_data_param == null){
			throw new NoSuchParameterException(data_token, "in "+getDataName());
		}
		return _data_param.getValue();
	}
	/**
	 * Adds the parameter as a sub data and declares this as being its parent data
	 * @param child_data
	 */
	public void addSubData(HierarchicalData child_data){
		synchronized (subDataSet) {
			subDataSet.add(child_data);	
			child_data.setParentData(this);
		}

	}
	/**
	 * Removes <b>all</b> data children corresponding to the argument.
	 */
	public void removeSubData(HierarchicalData child_data){
		synchronized (subDataSet) {
			subDataSet.remove(child_data);
		}
	}


	public void acceptVisitor(HierarchicalDataVisitor data_visitor){
		data_visitor.performTreatmentOn(this);
		for (Iterator<DataParameter> _it = parameterMap.values().iterator(); _it.hasNext();){
			data_visitor.performTreatmentOn(_it.next());
		}
		data_visitor.closeTretmentOnDataParameters();
		for (Iterator<HierarchicalData> _it = subDataSet.iterator(); _it.hasNext();){
			_it.next().acceptVisitor(data_visitor);
		}
		data_visitor.closeTreatmentOn(this);
	}
	/**
	 * 
	 * @return The parent data or null if there's no one.
	 */
	public final HierarchicalData getParentData() {
		return parentData;
	}
	/**
	 * Sets the parent data without modifying it.
	 * @param parent_data
	 */
	public final void setParentData(HierarchicalData parent_data) {
		this.parentData = parent_data;
	}




}
