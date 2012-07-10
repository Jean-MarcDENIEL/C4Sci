package c4sci.data;

/**
 * This class aims at performing some treatment on HierarchicalData, including DataParameters and sub data recursively.
 * @author jeanmarc.deniel
 *
 */
public interface DataVisitor {
	void performTreatmentOn(HierarchicalData data_node);
	void performTreatmentOn(DataParameter data_param);
}
