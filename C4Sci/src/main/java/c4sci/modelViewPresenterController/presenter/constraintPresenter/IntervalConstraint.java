package c4sci.modelViewPresenterController.presenter.constraintPresenter;

import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class defines an interval constraint. <br>
 * The constraint value is a relative boundary relatively to a fixed value.<br>
 * <br>
 * It is the responsibility of the user to define precisely the meaning of this boundary constraint.<br>
 * 
 * @author jeanmarc.deniel
 *
 */
public class IntervalConstraint extends OneComponentConstraint {

	public IntervalConstraint(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description, int constr_comp_id) {
		super(data_token, data_name, data_description, constr_comp_id);
	}

}
