package c4sci.modelViewPresenterController.controllerModelInterface.modelChanges;

import c4sci.modelViewPresenterController.jobs.Command;

/**
 * This class indicates that a data has been replaced by another in the model.<br>
 * The {@link #getModifiedData()} object should be considered anymore. It has been replaced by the {@link #getReplacementData()} object.
 * @author jeanmarc.deniel
 *
 */
public class DataReplacedChange extends DataChange {

	private Object	replacementData;
	
	public DataReplacedChange(Command parent_command, Object modified_data, Object replacement_data) {
		super(parent_command, modified_data);
		replacementData = replacement_data;
	}

	public final Object getReplacementData(){
		return replacementData;
	}
}
