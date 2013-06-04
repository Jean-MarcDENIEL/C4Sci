package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import c4sci.data.HierarchicalData;
import c4sci.data.PrototypeData;
import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.singleValueModifiables.IntegerModifiable;
import c4sci.data.exceptions.CannotInstantiateDataException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class defines constraint on single elements
 * @author jeanmarc.deniel
 *
 */
public class OneComponentConstraint extends HierarchicalData {

	private GenericDataParameter<IntegerModifiable>	constrainedComponentID;
	
	/**
	 * @param data_token
	 * @param data_name
	 * @param data_description
	 */
	public OneComponentConstraint(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description, int constr_comp_id) {
		super(data_token, data_name, data_description);
		
		constrainedComponentID = new GenericDataParameter<IntegerModifiable>(
				new IntegerModifiable(),
				"constrCompID", 
				new InternationalizableTerm("Constrained Component ID"), 
				new InternationalizableTerm("Constrained Component ID"));
		
		constrainedComponentID.accesValue().setIntegerValue(constr_comp_id);
		
		addDataParameter(constrainedComponentID);
	}
	
	public int getConstrainedComponentID(){
		return constrainedComponentID.accesValue().getIntegerValue();
	}
	public void setConstrainedComponentID(int int_val){
		constrainedComponentID.accesValue().setIntegerValue(int_val);
	}

	@Override
	public PrototypeData newInstance() throws CannotInstantiateDataException {
		return new OneComponentConstraint(NO_TOKEN, NO_NAME, NO_DESCRIPTION, 0);
	}
	

}
