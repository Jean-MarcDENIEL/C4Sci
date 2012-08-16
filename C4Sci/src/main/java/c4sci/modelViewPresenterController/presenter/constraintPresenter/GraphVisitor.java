package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import java.util.ArrayList;
import java.util.List;
/**
 * this class is used in the <b>visitor</b> GoF pattern.
 * @author jeanmarc.deniel
 *
 * @param <C>
 */
public abstract class GraphVisitor<C extends TwoComponentsConstraint>{
	private List<C> edgePath;
	public GraphVisitor(){
		edgePath = new ArrayList<C>();
	}
	public void appendEdge(C new_edge){
		edgePath.add(new_edge);
	}
	public void removeLastEdge(){
		edgePath.remove(edgePath.size()-1);
	}
	public List<C> getPath(){
		return edgePath;
	}
	/**
	 * 
	 * @return true if the visiting process should stop, otherwise returns false.
	 */
	public abstract boolean visitEdge(C current_edge);
}

