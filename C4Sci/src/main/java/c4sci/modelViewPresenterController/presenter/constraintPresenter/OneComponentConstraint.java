package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import c4sci.data.HierarchicalData;
import c4sci.data.basicDataParameters.IntegerDataParameter;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class defines constraint on single elements
 * @author jeanmarc.deniel
 *
 */
public class OneComponentConstraint extends HierarchicalData {

	IntegerDataParameter	constrainedComponentID;
	
	/**
	 * @param data_token
	 * @param data_name
	 * @param data_description
	 */
	public OneComponentConstraint(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description, int constr_comp_id) {
		super(data_token, data_name, data_description);
		
		constrainedComponentID = new IntegerDataParameter("constrCompID", 
				new InternationalizableTerm("Constrained Component ID"), 
				new InternationalizableTerm("Constrained Component ID"));
		
		constrainedComponentID.setIntegerValue(constr_comp_id);
		
		addDataParameter(constrainedComponentID);
	}

}
