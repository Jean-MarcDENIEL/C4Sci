package c4sci.data.exceptions;

import c4sci.data.HierarchialDataFactory;
import c4sci.data.HierarchicalData;

/**
 * This class corresponds to the {@link Exception} that is risen when a {@link HierarchialDataFactory} cannot instantiate a {@link HierarchicalData} subdata.
 * @author jeanmarc.deniel
 *
 */
public class CannotInstantiateDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4334268027426903255L;

	/**
	 * Create the {@link Exception}.
	 * @param parent_data The parent data whose sub data cannot be instantiated. May be null.
	 * @param subdata_token The token associated with the sub data.
	 */
	public CannotInstantiateDataException(HierarchicalData parent_data, String subdata_token, String explanation_message){
		super(presentParent(parent_data) + "subdata = " + subdata_token + " : " + explanation_message);
	}
	
	public CannotInstantiateDataException(HierarchicalData parent_data, String subdata_token, String explanation_message, Throwable previous_cause){
		super(presentParent(parent_data) + "subdata = " + subdata_token + " : " + explanation_message, previous_cause);
	}
	
	/**
	 * Converts from {@link HierarchicalData} to String representation. 
	 * @return A String presentation of the parent data.
	 */
	private static String presentParent(HierarchicalData parent_data){
		if (parent_data == null){
			return "No parent";
		}
		else{
			return parent_data.getClass().getName() + " as " + parent_data.getDataToken();
		}
	}
}
