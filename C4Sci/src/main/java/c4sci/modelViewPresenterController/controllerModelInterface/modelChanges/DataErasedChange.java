package c4sci.modelViewPresenterController.controllerModelInterface.modelChanges;

import c4sci.modelViewPresenterController.jobs.Command;
/**
 * This class indicates that a data has been removed a should not be considered anymore.
 * 
 * @author jeanmarc.deniel
 *
 */
public class DataErasedChange extends DataChange {

	public DataErasedChange(Command parent_command, Object erased_data) {
		super(parent_command, erased_data);
	}

}
