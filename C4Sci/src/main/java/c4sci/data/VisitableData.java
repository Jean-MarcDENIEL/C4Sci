package c4sci.data;


public interface VisitableData {
	void acceptVisitor(DataVisitor data_visitor);
}
