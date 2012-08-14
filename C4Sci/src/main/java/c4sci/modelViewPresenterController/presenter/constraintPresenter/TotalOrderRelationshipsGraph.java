package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.HierarchicalData;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class defines a graph composed of total order relationships between pairs of components.<br>
 * The graph is acyclic. It doesn't have to be a tree.
 * 
 * @author jeanmarc.deniel
 *
 */
public class TotalOrderRelationshipsGraph<C extends TwoComponentsConstraint> extends HierarchicalData{

	/**
	 * The map's keys are constrained components ID's.
	 * The outer map keys are the constrained keys.
	 * The inner map keys are the reference keys.
	 */
	private Map<Integer, Map<Integer, C>>	relationshipMap;
	
	

	public TotalOrderRelationshipsGraph(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description){
		super(data_token, data_name, data_description);
		relationshipMap	= new ConcurrentHashMap<Integer, Map<Integer, C>>();
	}

	/**
	 * 
	 * @return null if the constrained component is not involved in any relationship.<br>
	 * A relationship map whose keys are reference component id's.
	 */
	public Map<Integer, C> getConstrainedComponentRelationships(int constr_comp_id){
		return relationshipMap.get(constr_comp_id);
	}
	public C getRelationship(int ref_comp_id, int constr_comp_id){
		Map<Integer, C> _constr_map = getConstrainedComponentRelationships(constr_comp_id);
		if (_constr_map != null){
			return _constr_map.get(ref_comp_id);
		}
		return null;
	}
	public void addRelationShip(C relation_ship) throws CyclicGraphNotAllowedException{
		if (wouldCreateACycle(relation_ship.getReferenceComponentID(), relation_ship.getConstrainedComponentID())){
			throw new CyclicGraphNotAllowedException(relation_ship.getReferenceComponentID(), relation_ship.getConstrainedComponentID());
		}
		Map<Integer, C> _constr_map = getConstrainedComponentRelationships(relation_ship.getConstrainedComponentID());
		if (_constr_map == null){
			_constr_map = new ConcurrentHashMap<Integer, C>();
			relationshipMap.put(Integer.valueOf(relation_ship.getConstrainedComponentID()), _constr_map);
		}
		_constr_map.put(Integer.valueOf(relation_ship.getReferenceComponentID()), relation_ship);
	}

	public boolean wouldCreateACycle(final int new_ref_comp, final int new_constrainted_comp){
		GraphVisitor<C> _visitor = new GraphVisitor<C>() {

			@Override
			public boolean visitEdge(C current_edge) {
				return current_edge.getReferenceComponentID() == new_constrainted_comp;
			}
		};
		
		return !acceptVisitor(_visitor, new_ref_comp);
	}
	public Collection<C> getEdgesToReferenceLeaves(int current_component){
		
		class RetrievingVisitor extends GraphVisitor<C>{

			// use a map to avoid having several edge sharing the same reference component 
			private Map<Integer, C> resultList;
			
			public RetrievingVisitor() {
				resultList = new ConcurrentHashMap<Integer, C>();
			}

			public boolean visitEdge(C current_edge) {
				if (getConstrainedComponentRelationships(current_edge.getReferenceComponentID()) == null){
					resultList.put(Integer.valueOf(current_edge.getReferenceComponentID()), current_edge);
				}
				return false;
			}
			public Map<Integer, C> getResult(){
				return resultList;
			}
		}	
		RetrievingVisitor _graph_visitor = new RetrievingVisitor();
		acceptVisitor(_graph_visitor, current_component);
		return _graph_visitor.getResult().values();
	}

	/**
	 * Will visit all edges and vertices accessible from a given vertex :
	 * <ol>
	 * <li>retrieves the edges for which the current vertex is the constrained component.</li>
	 * <li>for each of these edge :
	 * 		<ol>
	 * 		<li> appends the edge to the current path </li>
	 * 		<li> calls the visitor visitEdge</li>
	 * 		<li> if the visitor allows to continue the visiting process, then recursively call acceptVisitor on the current edge reference component</li>
	 * 		<li> else return false if visitor stops the visitor stopped the process or the recursive process has returned false</li>
	 * 		<li> removes the edge from the current path</li>
	 * 		</ol>
	 * <li> returns true</li>
	 * </ol>
	 * 
	 * <br>
	 * <b>Pattern : </b> This method implements the <b>Visitor</b> GoF pattern.
	 * 
	 * @param beginning_edge The constrained component ID from which the graph is explored.
	 * @param graph_visitor  The visitor invoked on each edge.
	 * @return true if none of the explored edge made the GraphVisitor.visitNode() returned true.
	 */
	public boolean acceptVisitor(GraphVisitor<C> graph_visitor, int current_vertex_id ){
		Map<Integer, C> _current_vertex_relationships = getConstrainedComponentRelationships(current_vertex_id);
		if (_current_vertex_relationships == null){
			return true;
		}
		Collection<C> _ref_ref_collection = _current_vertex_relationships.values();

		for (Iterator<C> _it = _ref_ref_collection.iterator(); _it.hasNext();){
			C _current_edge = _it.next();
			
			if (graph_visitor.visitEdge(_current_edge)){
				return false;
			}
			graph_visitor.appendEdge(_current_edge);
			if (!acceptVisitor(graph_visitor, _current_edge.getReferenceComponentID())){
				return false;
			}
			graph_visitor.removeLastEdge();
		}
		return true;
	}

}
