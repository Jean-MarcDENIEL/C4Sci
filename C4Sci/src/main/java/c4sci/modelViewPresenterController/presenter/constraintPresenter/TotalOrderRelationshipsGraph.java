package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

	public boolean wouldCreateACycle(int ref_comp, int constrained_comp){
		List<Integer> _current_path = new ArrayList<Integer>();
		_current_path.add(Integer.valueOf(constrained_comp));
		return wouldCreateACycle(ref_comp, constrained_comp, _current_path);
	}
	
	private boolean wouldCreateACycle(int ref_comp, int constrained_comp, List<Integer> current_path){
		if (current_path.contains(Integer.valueOf(ref_comp))){
			return true;
		}
		current_path.add(Integer.valueOf(ref_comp));
		Map<Integer, C> _ref_relationships = getConstrainedComponentRelationships(ref_comp);
		if (_ref_relationships == null){
			return false;
		}
		Set<Integer> _ref_ref_collection = _ref_relationships.keySet();
		for (Iterator<Integer> _it = _ref_ref_collection.iterator(); _it.hasNext();){
			if (wouldCreateACycle(_it.next().intValue(), ref_comp, current_path)){
				return true;
			}	
		}
		current_path.remove(current_path.size()-1);
		return false;
	}


}
