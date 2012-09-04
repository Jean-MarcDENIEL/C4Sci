package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.DataParameterDataElements;

import c4sci.data.DataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.SingleDataStepElement;

public abstract class DataParameterDataElement extends SingleDataStepElement {
	private DataParameter	dataParameter;
	
	public final DataParameter getDataParameter() {
		return dataParameter;
	}
	public final void setDataParameter(DataParameter data_parameter) {
		this.dataParameter = data_parameter;
	}
	
	public DataParameterDataElement(DataParameter data_p) {
		setDataParameter(data_p);
	}

}
