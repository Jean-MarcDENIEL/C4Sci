package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c4sci.NoChildIterator;
import c4sci.data.DataParameter;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;
/**
 * This class makes a link between a {@link LogicalSingleDataStepElement} and a {@link DataParameter}.
 * 
 * @author jeanmarc.deniel
 *
 */
public abstract class DataParameterDataElement extends SingleDataStepElement {
	@Override
	/**
	 * As a default behavior it has no unit.
	 */
	public UnitScales getUnits() {
		return null;
	}
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
	
	@Override
	/**
	 * As a default behavior, it is editable.
	 */
	public boolean isEditable() {
		return true;
	}
	@Override
	public List<ElementBinding> getBindings() {
		List<ElementBinding> _res= new ArrayList<ElementBinding>();
		_res.add(getSingleBinding());
		return _res;
	}
	
	
}
