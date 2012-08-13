package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import java.util.ArrayList;
import java.util.List;

public abstract class GraphVisitor<C extends TwoComponentsConstraint>{
	private ArrayList<C> edgePath;
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
	 * @return true if visit process should stop 
	 */
	public abstract boolean visitEdge(C current_edge);
}

