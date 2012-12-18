package c4sci.data;

/**
 * This class aims at performing some treatment on HierarchicalData, including DataParameters and sub data recursively.<br><br>
 * 
 * <b>Pattern</b> This class is part of a GoF Visitor pattern.
 * 
 * @author jeanmarc.deniel
 *
 */
public interface HierarchicalDataVisitor {
	/**
	 * Begins visit session of a data node
	 * @param current_data
	 */
	void performTreatmentOn(HierarchicalData current_data);
	/**
	 * Begins the treatment of the current node parameters
	 * @param current_data TODO
	 */
	void beginDataParametersSession(HierarchicalData current_data);
	/**
	 * Visits the current node parameters
	 * @param current_data TODO
	 * @param data_param
	 */
	void performTreatmentOn(HierarchicalData current_data, DataParameter data_param);
	/**
	 * Ends the treatment of the current node parameters
	 * @param current_data TODO
	 */
	void endDataParametersSession(HierarchicalData current_data);
	/**
	 * Begins the treatment of the sub data of the current node
	 * @param current_data
	 */
	void beginSubDataSession(HierarchicalData current_data);
	/**
	 * Ends the treatment of the current node sub data
	 * @param current_data
	 */
	void endSubDataSession(HierarchicalData current_data);
	/**
	 * Ends the treatment of the current node
	 * @param data_node
	 */
	void endTreatmentOn(HierarchicalData current_data);
}
