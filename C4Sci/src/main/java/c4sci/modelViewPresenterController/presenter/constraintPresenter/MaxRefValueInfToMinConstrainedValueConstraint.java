package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.singleValueModifiables.BooleanModifiable;
import c4sci.data.dataParameters.singleValueModifiables.FloatModifiable;
import c4sci.data.exceptions.CannotInstantiateDataException;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.exceptions.CannotInstantiateMVPCElement;
/**
 * This class defines a total order between two component :<br>
 * upper reference component value <= bottom constrained component value.<br>
 * <br>
 * 
 * @author jeanmarc.deniel
 *
 */
public final class MaxRefValueInfToMinConstrainedValueConstraint extends TwoComponentsConstraint {

	private GenericDataParameter<FloatModifiable>		fixedConstraintValue;
	private GenericDataParameter<BooleanModifiable>		hasFixedConstraint;

	public MaxRefValueInfToMinConstrainedValueConstraint(int ref_comp_id, int constr_comp_id) throws CannotInstantiateMVPCElement, CannotInstantiateDataException {
		super("infCoorConstr", 
				new InternationalizableTerm("Inferior Constraint"), 
				new InternationalizableTerm("A total relationship : reference sup < constrained inf"),
				ref_comp_id, constr_comp_id);

		try {
			fixedConstraintValue = new GenericDataParameter<FloatModifiable>(new FloatModifiable(), "fixedConstrValue",	
					new InternationalizableTerm("Fixed Constraint Value"), 
					new InternationalizableTerm("Fixed constaint value"));
			hasFixedConstraint = new GenericDataParameter<BooleanModifiable>(
					new BooleanModifiable(),
					"hasFixedConstraint", 
					new InternationalizableTerm("Has a fixed constraint"), 
					new InternationalizableTerm("Has a fixed constraint"));
		} catch (CannotInstantiateParameterException _e) {
			throw new CannotInstantiateMVPCElement(_e);
		}


		fixedConstraintValue.accesValue().setFloatValue(0.0f);
		hasFixedConstraint.accesValue().setBooleanValue(false);
	}

	/**
	 * Sets the constraint as a fixed value constraint.<br>
	 * After this method has been called, {@link #isFixed isFixed()} returns true
	 * @param constr_val new constraint value
	 */
	public void setAsFixedConstraint(float constr_val){
		fixedConstraintValue.accesValue().setFloatValue(constr_val);
		hasFixedConstraint.accesValue().setBooleanValue(true);
	}
	/**
	 * 
	 * @return the fixed constraint value.<br>
	 * <b>Warning : </b> If the constraint has not been fixed before, the return value is undefined.
	 */
	public float getFixedConstraint(){
		return fixedConstraintValue.accesValue().getFloatValue();
	}
	/**
	 * 
	 * @return true if the constraint got a fixed value
	 */
	public boolean isFixed(){
		return hasFixedConstraint.accesValue().getBooleanValue();
	}
	/**
	 * Removes any fixed constraint value.
	 */
	public void setUnfixed(){
		hasFixedConstraint.accesValue().setBooleanValue(false);
	}


}
