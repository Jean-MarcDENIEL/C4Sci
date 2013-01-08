package c4sci.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.exceptions.CannotInstantiateDataException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.exceptions.CannotInstantiateParameterException;
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
 * <li>appending new parameter types associated to data parameter token</li>
 * <li>ensuring low coupling with algorithms manipulating {@link HierarchicalData} like reading and writing data : simply append new factoring abilities representing accepted sub data</li>
 * </ul> <br>
 * <b>Warnings: </b><br>
 * When sub classing it is necessary that :
 * <ul>
 * <li>the new type parameters be created in its constructors</li>
 * <li>the new parameters be added through the {@link #addDataParameter(DataParameter)} method call</li> in all constructors
 * <li>the {@link #getSubdataFactory()} method be overridden : it enriches the super.{@link #getSubdataFactory()} return value</li>
 * <li>the {@link #newInstance()} method be implemented or overridden to return an instance of the subclass.
 * </ul>
 * It is unnecessary that :
 * <ul>
 * <li>the {@link #getParameterFactory()} method be overridden.</li>
 * </ul>
 * Several children data can share the same token : there is no mapping.
 * @author jeanmarc.deniel
 *
 */
public class HierarchicalData implements VisitableData, PrototypeData{

	public static final String 					NO_TOKEN 		= "(no-token)";
	public static final InternationalizableTerm	NO_NAME 		= new InternationalizableTerm("no name");
	public static final InternationalizableTerm	NO_DESCRIPTION 	= new InternationalizableTerm("no description");
	
	private DataIdentity					dataIdentity;
	private String							dataToken;
	private InternationalizableTerm			dataName;
	private InternationalizableTerm			dataDescription;
	private Map<String,DataParameter>		parameterMap;
	private Set<HierarchicalData>			subDataSet;
	private HierarchicalData				parentData;


	private static final Map<DataIdentity, HierarchicalData>	IDENTITY_DATA_MAP = new ConcurrentHashMap<DataIdentity, HierarchicalData>();

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
		try{
			IDENTITY_DATA_MAP.remove(dataIdentity);
		}
		catch(Exception _e){}
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

	public HierarchicalData(){
		dataIdentity 	= createDataIdentity();
		dataToken		= NO_TOKEN;
		dataName		= NO_NAME;
		dataDescription	= NO_DESCRIPTION;
		parentData		= null;
		parameterMap	= new ConcurrentHashMap<String, DataParameter>();
		subDataSet		= new HashSet<HierarchicalData>();		
	}
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

	public final void setParameterValue(String data_token, String value_to_parse) throws DataValueParsingException,  CannotInstantiateParameterException{
		DataParameter _data_param = parameterMap.get(data_token);
		if (_data_param == null){
			throw new CannotInstantiateParameterException(data_token, "in "+getDataName());
		}
		_data_param.setValue(value_to_parse);
	}
	/**
	 * Access a DataParameter given its token<br><br>
	 * <b>Pattern : </b> GoF Decorator pattern
	 */
	public final String getParameterValue(String data_token) throws CannotInstantiateParameterException{
		DataParameter _data_param = parameterMap.get(data_token);
		if (_data_param == null){
			throw new CannotInstantiateParameterException(data_token, "in "+getDataName());
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
		data_visitor.beginDataParametersSession(this);
		for (Iterator<DataParameter> _it = parameterMap.values().iterator(); _it.hasNext();){
			data_visitor.performTreatmentOn(null, _it.next());
		}
		data_visitor.endDataParametersSession(null);
		data_visitor.beginSubDataSession(this);
		for (Iterator<HierarchicalData> _it = subDataSet.iterator(); _it.hasNext();){
			_it.next().acceptVisitor(data_visitor);
		}
		data_visitor.endSubDataSession(this);
		data_visitor.endTreatmentOn(this);
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

	public void setDataToken(String data_token) {
		this.dataToken = data_token;
	}

	/**
	 * @return A {@link HierarchialDataFactory} that is able to instantiate "this" sub data given some tokens. 
	 */
	public HierarchialDataFactory getSubdataFactory(){
		return new HierarchialDataFactory();
	}

	/**
	 * Collects all data parameters and adds the corresponding factoring ability to the result.
	 * @return
	 */
	public DataParameterFactory getParameterFactory(){
		DataParameterFactory _res = new DataParameterFactory();
		
		String[] _param_tokens_tab = parameterMap.values().toArray(null);
		for (String _param_token : _param_tokens_tab){
			DataParameter _param = parameterMap.get(_param_token);
			_res.addFactoringAbility(_param.getParameterToken(), _param.getClass());
		}
		return _res;
	}
	
	/*public PrototypeData newInstance(){
		return new HierarchicalData();
	}*/
	
	public void setDataName(InternationalizableTerm data_name) {
		this.dataName = data_name;
	}

	public void setDataDescription(InternationalizableTerm data_description) {
		this.dataDescription = data_description;
	}

	public PrototypeData newInstance() throws CannotInstantiateDataException {
		throw new CannotInstantiateDataException(null, getDataToken(), "cannot instantiate HierarchicalData root class");
	}
}
