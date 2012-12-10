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
	 * <li> performs visitor treatment on "this" data node : calls {@link HierarchicalDataVisitor#performTreatmentOn(HierarchicalData)} on "this".</li>
	 * <li> begins parameters treatment : 
	 * 	<ol type="a"> 
	 * 	<li> opens parameter session : calls {@link HierarchicalDataVisitor#beginDataParametersSession(HierarchicalData)}</li>
	 * 	<li> performs visitor treatment on "this" data node parameters : calls {@link HierarchicalDataVisitor#performTreatmentOn(HierarchicalData, DataParameter)}.</li>
	 * 	<li> ends parameter session : calls {@link HierarchicalDataVisitor#endDataParametersSession(HierarchicalData)}</li>
	 * 	</ol>
	 * <li> accepts visitor on sub data in random order : 
	 * 	<ol type="a">
	 * 	<li> opens sub data session : calls {@link HierarchicalDataVisitor#beginSubDataSession(HierarchicalData)}</li>
	 * 	<li> performs visitor treatment on sub data : calls {@link HierarchicalDataVisitor#performTreatmentOn(HierarchicalData)}.</li>
	 * 	<li> ends sub data session : calls {@link HierarchicalDataVisitor#endSubDataSession(HierarchicalData)}</li>
	 * </ol>
	 * <li> ends "this" treatment : call {@link HierarchicalDataVisitor#endTreatmentOn(HierarchicalData)} on "this".
	 * </ol>
	 * @param data_visitor The visitor of this node
	 */
	void acceptVisitor(HierarchicalDataVisitor data_visitor);
}
