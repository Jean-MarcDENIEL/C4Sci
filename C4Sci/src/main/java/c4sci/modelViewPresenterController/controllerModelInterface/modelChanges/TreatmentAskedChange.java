package c4sci.modelViewPresenterController.controllerModelInterface.modelChanges;

import c4sci.modelViewPresenterController.controllerModelInterface.ModelChange;
import c4sci.modelViewPresenterController.jobs.Command;

public class TreatmentAskedChange extends ModelChange {

	private Object	treatedData;
	private String	treatmentName;
	public TreatmentAskedChange(Command parent_command, Object treated_data, String treatment_name) {
		super(parent_command);
		treatedData		= treated_data;
		treatmentName	= treatment_name;
	}
	public final Object getTreatedData(){
		return treatedData;
	}
	public final String getTreatmentName(){
		return treatmentName;
	}

}
