package c4sci.modelViewPresenterController.controllerModelInterface.modelChanges;

import c4sci.data.Modifiable;
import c4sci.modelViewPresenterController.jobs.Command;
/**
 * This class indicates that a {@link Modifiable} data is being modified.<br>
 * 
 * Depending on the {@link Command} direction, the meaning of the {@link #getModificationValue()} is  this :
 * <ul>
 * 	<li> "reactive" : the field will be modified with the given value. Its up to the model to update and process to depending updates.</li>
 * 	<li> "feedback" : the field has been modified. Its new value is given by {@link #getModificationValue()} that should be equal to the {@link Modifiable#getValue()} return value.</li>
 * </ul>
 * 
 * @author jeanmarc.deniel
 *
 */
public class ModifiableChange extends DataChange {

	private String	modificationValue;
	
	public ModifiableChange(Command parent_command, Modifiable modified_data, String modification_value) {
		super(parent_command, modified_data);
		modificationValue = modification_value;
	}
	
	public final String getModificationValue(){
		return modificationValue;
	}

}
