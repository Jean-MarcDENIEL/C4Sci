package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import c4sci.data.HierarchicalData;
import c4sci.data.PrototypeData;
import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.basicModifiables.IntegerModifiable;
import c4sci.data.exceptions.CannotInstantiateDataException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class defines a relationship between two components : a reference one and a constrained one.<br>
 * <br>
 * 
 * 
 * @author jeanmarc.deniel
 *
 */
public class TwoComponentsConstraint extends HierarchicalData {
	private GenericDataParameter<IntegerModifiable> referenceComponentID;
	private GenericDataParameter<IntegerModifiable> constrainedComponentID;
		
	public TwoComponentsConstraint(String data_token, 
			InternationalizableTerm data_name,
			InternationalizableTerm data_description, int ref_comp_id, int constr_comp_id) {
		super(data_token, data_name, data_description);
		
		referenceComponentID = new GenericDataParameter<IntegerModifiable>(
				new IntegerModifiable(), 
				"refCompID", 
				new InternationalizableTerm("reference Component ID"),
				new InternationalizableTerm("ID of the component used as reference in the constrained relationship"));
		constrainedComponentID = new GenericDataParameter<IntegerModifiable>(
				new IntegerModifiable(), 
				"constrCompID", 
				new InternationalizableTerm("constrained component ID"), 
				new InternationalizableTerm("ID of the constrained component"));
				
		addDataParameter(referenceComponentID);
		addDataParameter(constrainedComponentID);
		
		setReferenceComponentID(ref_comp_id);
		setConstrainedComponentID(constr_comp_id);
	}

	public final int getReferenceComponentID() {
		return referenceComponentID.accesValue().getIntegerValue();
	}
	public final void setReferenceComponentID(int ref_comp_id) {
		this.referenceComponentID.accesValue().setIntegerValue(ref_comp_id);
	}
	public final int getConstrainedComponentID() {
		return constrainedComponentID.accesValue().getIntegerValue();
	}
	public final void setConstrainedComponentID(int constr_comp_id) {
		this.constrainedComponentID.accesValue().setIntegerValue(constr_comp_id);
	}

	@Override
	public PrototypeData newInstance() throws CannotInstantiateDataException {
		return new TwoComponentsConstraint(NO_TOKEN, NO_NAME, NO_DESCRIPTION, 0, 0);
	}

}
