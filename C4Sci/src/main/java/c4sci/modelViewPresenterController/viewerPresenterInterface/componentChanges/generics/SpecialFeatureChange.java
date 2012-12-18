package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;

/**
 * This class indicates a change in a special feature of the modified component.<br>
 * The feature to change is identified by an integer value and is dependent on the modified component.<br>
 * This class must be sub classed in order to retrieve the special feature change parameters.
 * @author jeanmarc.deniel
 *
 */
public class SpecialFeatureChange extends IntegerChange {

	public SpecialFeatureChange(Component comp_, int feature_val, Command parent_cmd) {
		super(comp_, feature_val, parent_cmd);
	}

}
