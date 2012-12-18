package c4sci.modelViewPresenterController.controllerModelInterface.modelChanges;

import c4sci.modelViewPresenterController.jobs.Command;

/**
 * This class indicates that the internal state of a data has been changed.
 * @author jeanmarc.deniel
 *
 */
public class DataUpdatedChange extends DataChange {

	public DataUpdatedChange(Command parent_command, Object updated_data) {
		super(parent_command, updated_data);
	}

}
