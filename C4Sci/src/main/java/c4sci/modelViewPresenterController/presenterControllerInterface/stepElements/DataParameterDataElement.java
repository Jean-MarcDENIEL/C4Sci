package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.Iterator;

import c4sci.NoChildIterator;
import c4sci.data.DataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
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

	public Iterator<StepElement> getSubElementsIterator(){
		return new NoChildIterator<StepElement>();
	}

	@Override
	public boolean containsProperValue() {
		return true;
	}
	@Override
	public String getProperValue() {
		return getDataParameter().getValue();
	}
	@Override
	public void setProperValue(String str_value) {
		try {
			getDataParameter().setValue(str_value);
		} catch (DataValueParsingException _e) {
			// does nothing
		}		
	}


}
