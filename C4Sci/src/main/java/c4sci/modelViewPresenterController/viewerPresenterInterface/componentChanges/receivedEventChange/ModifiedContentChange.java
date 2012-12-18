package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.receivedEventChange;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

/**
 * This class indicates that the content of the component has been modified.<br>
 * This class must then sub classed in order to take into account each case of content upgrade.
 * 
 * @author jeanmarc.deniel
 *
 */
public class ModifiedContentChange extends ComponentChange {

	public ModifiedContentChange(Component comp_, Command parent_cmd) {
		super(comp_, parent_cmd);
	}

}
