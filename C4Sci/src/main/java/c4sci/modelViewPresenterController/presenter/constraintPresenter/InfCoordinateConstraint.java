package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import c4sci.data.basicDataParameters.BooleanDataParameter;
import c4sci.data.basicDataParameters.FloatDataParameter;
import c4sci.data.internationalization.InternationalizableTerm;
/**
 * This class defines a total order between two component :<br>
 * upper reference component value <= bottom constrained component value.<br>
 * <br>
 * 
 * @author jeanmarc.deniel
 *
 */
public class InfCoordinateConstraint extends Constraint {

	private FloatDataParameter		fixedConstraintValue;
	private BooleanDataParameter	hasFixedConstraint;
	
	public InfCoordinateConstraint(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description,
			int ref_comp_id, int constr_comp_id) {
		super("infCoorConstr", 
				new InternationalizableTerm("Inferior Constraint"), 
				new InternationalizableTerm("A total relationship : reference sup < constrained inf"),
				ref_comp_id, constr_comp_id);
		
		fixedConstraintValue = new FloatDataParameter("fixedConstrValue",	
				new InternationalizableTerm("Fixed Constraint Value"), 
				new InternationalizableTerm("Fixed constaint value"));
		hasFixedConstraint = new BooleanDataParameter("hasFixedConstraint", 
				new InternationalizableTerm("Has a fixed constraint"), 
				new InternationalizableTerm("Has a fixed constraint"));
		
		fixedConstraintValue.setFloatValue(0.0f);
		hasFixedConstraint.setBooleanValue(false);
	}

}
