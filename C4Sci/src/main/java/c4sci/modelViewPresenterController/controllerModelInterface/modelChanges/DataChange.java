package c4sci.modelViewPresenterController.controllerModelInterface.modelChanges;

import c4sci.modelViewPresenterController.controllerModelInterface.ModelChange;
import c4sci.modelViewPresenterController.jobs.Command;
/**
 * This class indicates a data modification.<br>
 * 
 * Subclasses give the meaning of the change.
 * 
 * @author jeanmarc.deniel
 *
 */
public class DataChange extends ModelChange {

	private Object modifiedData;
	
	public DataChange(Command parent_command, Object modified_data) {
		super(parent_command);
		setModifiedData(modified_data);
	}

	public final Object getModifiedData() {
		return modifiedData;
	}

	public final void setModifiedData(Object modified_data) {
		this.modifiedData = modified_data;
	}

}
