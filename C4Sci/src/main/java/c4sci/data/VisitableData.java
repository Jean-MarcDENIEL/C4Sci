package c4sci.data;

/**
 * This interface is intended at avoiding suspicious dependency cycling between 
 * classes involved in a VISITOR pattern.<br><br>
 * 
 * <b>Pattern : </b>This class is part of a GoF Visitor pattern.
 * 
 * @author jeanmarc.deniel
 *
 */
public interface VisitableData {
	void acceptVisitor(DataVisitor data_visitor);
}
