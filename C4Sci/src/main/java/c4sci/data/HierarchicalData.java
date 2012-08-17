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


	private static Map<DataIdentity, HierarchicalData>	identityDataMap = new ConcurrentHashMap<DataIdentity, HierarchicalData>();
	
	/**
	 * 
	 * @param data_identity
	 * @return the HierarchicalData whose identity has been passed as argument, or null if no HierarchialData can be found
	 */
	public static HierarchicalData getIdentifiedData(DataIdentity data_identity){
		return identityDataMap.get(data_identity);
	}
	
	private DataIdentity createDataIdentity(){
		DataIdentity _ret = new DataIdentity();
		identityDataMap.put(_ret, this);
		return _ret;
	}
	
	public final void setDataIdentity(DataIdentity data_identity){
		identityDataMap.remove(dataIdentity);
		dataIdentity = data_identity;
		identityDataMap.put(dataIdentity, this);
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
		identityDataMap.remove(getDataIdentity());
	}
	
	@SuppressWarnings("unused")
	private HierarchicalData(){}
	public	HierarchicalData(String data_token, InternationalizableTerm data_name, InternationalizableTerm data_description){
		dataIdentity 	= createDataIdentity();
		dataToken		= data_token;
		dataName		= data_name;
		dataDescription	= data_description;
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
		_data_param.setParameterValue(value_to_parse);
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
		return _data_param.getParameterValue();
	}
	
	public void addSubData(HierarchicalData child_data){
		synchronized (subDataSet) {
			subDataSet.add(child_data);		
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
	
	/**
	 * <b>Pattern : </b> GoF Visitor pattern<br><br>
	 * 
	 * In this order :
	 * <ol>
	 * <li> performs visitor treatment on the current data node
	 * <li> performs visitor treatment on the current data node parameters
	 * <li> accepts visitor on sub data in random order
	 * </ol>
	 * @param data_visitor
	 */
	public void acceptVisitor(DataVisitor data_visitor){
		data_visitor.performTreatmentOn(this);
		for (Iterator<DataParameter> _it = parameterMap.values().iterator(); _it.hasNext();){
			data_visitor.performTreatmentOn(_it.next());
		}
		for (Iterator<HierarchicalData> _it = subDataSet.iterator(); _it.hasNext();){
			_it.next().acceptVisitor(data_visitor);
		}
	}
	
	
	
}
