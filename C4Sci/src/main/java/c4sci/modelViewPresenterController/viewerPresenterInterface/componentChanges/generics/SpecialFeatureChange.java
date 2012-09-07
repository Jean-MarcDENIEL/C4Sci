package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.data.DataIdentity;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class indicates a change in a special feature of the modified component.<br>
 * The feature to change is identified by an integer value and is dependent on the modified component.<br>
 * This class must be sub classed in order to retrieve the special feature change parameters.
 * @author jeanmarc.deniel
 *
 */
public class SpecialFeatureChange extends IntegerChange {

	public SpecialFeatureChange(DataIdentity comp_id, int feature_val, Command parent_cmd) {
		super(comp_id, feature_val, parent_cmd);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_SPECIAL_FEATURE;
	}

}
