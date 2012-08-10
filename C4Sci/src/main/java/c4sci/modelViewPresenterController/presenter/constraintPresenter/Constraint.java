package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import c4sci.data.HierarchicalData;
import c4sci.data.basicDataParameters.IntegerDataParameter;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class defines a relationship between two components.
 * @author jeanmarc.deniel
 *
 */
public class Constraint extends HierarchicalData {
	private IntegerDataParameter referenceComponentID;
	private IntegerDataParameter constrainedComponentID;
		
	public Constraint(String data_token, 
			InternationalizableTerm data_name,
			InternationalizableTerm data_description, int ref_comp_id, int constr_comp_id) {
		super(data_token, data_name, data_description);
		
		referenceComponentID = new IntegerDataParameter("refCompID", 
				new InternationalizableTerm("reference Component ID"),
				new InternationalizableTerm("ID of the component used as reference in the constrained relationship"));
		constrainedComponentID = new IntegerDataParameter("constrCompID", 
				new InternationalizableTerm("constrained component ID"), 
				new InternationalizableTerm("ID of the constrained component"));
				
		addDataParameter(referenceComponentID);
		addDataParameter(constrainedComponentID);
		
		setReferenceComponentID(ref_comp_id);
		setConstrainedComponentID(constr_comp_id);
	}

	public int getReferenceComponentID() {
		return referenceComponentID.getIntegerValue();
	}
	public void setReferenceComponentID(int ref_comp_id) {
		this.referenceComponentID.setIntegerValue(ref_comp_id);
	}
	public int getConstrainedComponentID() {
		return constrainedComponentID.getIntegerValue();
	}
	public void setConstrainedComponentID(int constr_comp_id) {
		this.constrainedComponentID.setIntegerValue(constr_comp_id);
	}

}
