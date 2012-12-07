package c4sci.data;

/**
 * This interface is intended at avoiding suspicious dependency cycling between 
 * classes involved in a VISITOR GoF pattern.<br><br>
 * 
 * <b>Pattern : </b>This class is part of a GoF Visitor pattern.
 * 
 * @author jeanmarc.deniel
 *
 */
public interface VisitableData {
	/**
	 * <b>Pattern : </b> GoF Visitor pattern<br><br>
	 * 
	 * In this order :
	 * <ol>
	 * <li> performs visitor treatment on "this" data node : call {@link HierarchicalDataVisitor#performTreatmentOn(HierarchicalData)} on "this".</li>
	 * <li> performs visitor treatment on "this" data node parameters : call {@link HierarchicalDataVisitor#performTreatmentOn(DataParameter)}.</li>
	 * <li> closes parameters treatment : call {@link HierarchicalDataVisitor#closeTretmentOnDataParameters()}.</li>
	 * <li> accepts visitor on sub data in random order : call {@link HierarchicalDataVisitor#performTreatmentOn(HierarchicalData)}.</li>
	 * <li> closes "this" treatment : call {@link HierarchicalDataVisitor#closeTreatmentOn(HierarchicalData)} on "this".
	 * </ol>
	 * @param data_visitor The visitor of this node
	 */
	void acceptVisitor(HierarchicalDataVisitor data_visitor);
}
