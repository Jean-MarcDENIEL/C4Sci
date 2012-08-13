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
public final class MaxRefValueInfToMinConstrainedValueConstraint extends TwoComponentsConstraint {

	private FloatDataParameter		fixedConstraintValue;
	private BooleanDataParameter	hasFixedConstraint;
	
	public MaxRefValueInfToMinConstrainedValueConstraint(int ref_comp_id, int constr_comp_id) {
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
	
	/**
	 * Sets the constraint as a fixed value constraint.<br>
	 * After this method has been called, {@link isFixed}() returns true
	 * @param constr_val new constraint value
	 */
	public void setAsFixedConstraint(float constr_val){
		fixedConstraintValue.setFloatValue(constr_val);
		hasFixedConstraint.setBooleanValue(true);
	}
	/**
	 * 
	 * @return the fixed constraint value.<br>
	 * <b>Warning : </b> If the constraint has not been fixed before, the return value is undefined.
	 */
	public float getFixedConstraint(){
		return fixedConstraintValue.getFloatValue();
	}
	/**
	 * 
	 * @return true if the constraint got a fixed value
	 */
	public boolean isFixed(){
		return hasFixedConstraint.getBooleanValue();
	}
	/**
	 * Removes any fixed constraint value.
	 */
	public void setUnfixed(){
		hasFixedConstraint.setBooleanValue(false);
	}
	

}
