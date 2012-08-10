package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.HierarchicalData;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class defines a graph composed of total order relationships between pairs of components.
 * 
 * @author jeanmarc.deniel
 *
 */
public class TotalOrderRelationshipsGraph<C extends TwoComponentsConstraint> extends HierarchicalData{

	/**
	 * The map's keys are Component ID's
	 */
	private Map<Integer, C>	relationshipMap;
	
	public TotalOrderRelationshipsGraph(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description){
		super(data_token, data_name, data_description);
		
		relationshipMap	= new ConcurrentHashMap<Integer, C>();
	}

}
