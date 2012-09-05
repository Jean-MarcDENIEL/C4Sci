package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements;

import c4sci.data.DataParameter;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.SingleDataStepElement;
/**
 * This class makes a link between a {@link SingleDataStepElement} and a {@link DataParameter}
 * @author jeanmarc.deniel
 *
 */
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
