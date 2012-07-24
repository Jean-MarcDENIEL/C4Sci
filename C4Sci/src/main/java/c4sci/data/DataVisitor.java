package c4sci.data;

/**
 * This class aims at performing some treatment on HierarchicalData, including DataParameters and sub data recursively.<br><br>
 * 
 * <b>Pattern</b> This class is part of a GoF Visitor pattern.
 * 
 * @author jeanmarc.deniel
 *
 */
public interface DataVisitor {
	void performTreatmentOn(HierarchicalData data_node);
	void performTreatmentOn(DataParameter data_param);
}
