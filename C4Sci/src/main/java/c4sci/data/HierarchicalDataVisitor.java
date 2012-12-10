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
	 * @param data_node
	 */
	void performTreatmentOn(HierarchicalData data_node);
	/**
	 * Begins the treatment of the current node parameters
	 * @param current_node TODO
	 */
	void beginDataParametersSession(HierarchicalData current_node);
	/**
	 * Visits the current node parameters
	 * @param current_node TODO
	 * @param data_param
	 */
	void performTreatmentOn(HierarchicalData current_node, DataParameter data_param);
	/**
	 * Ends the treatment of the current node parameters
	 * @param current_node TODO
	 */
	void endDataParametersSession(HierarchicalData current_node);
	/**
	 * Begins the treatment of the sub data of the current node
	 * @param current_node
	 */
	void beginSubDataSession(HierarchicalData current_node);
	/**
	 * Ends the treatment of the current node sub data
	 * @param current_node
	 */
	void endSubDataSession(HierarchicalData current_node);
	/**
	 * Ends the treatment of the current node
	 * @param data_node
	 */
	void endTreatmentOn(HierarchicalData current_node);
}
