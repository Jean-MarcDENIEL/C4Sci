package c4sci.modelViewPresenterController.presenterControllerInterface;

import c4sci.data.DataParameter;


/**
 * This class permits data bindings between {@link StepElement StepElements} and {@link DataParameter DataParameters}. 
 * @author jeanmarc.deniel
 *
 */
public class ElementBinding {
	private StepElement		boundElement;
	private DataParameter	boundParameter;
	
	@SuppressWarnings("unused")
	private ElementBinding(){}
	
	public ElementBinding(StepElement step_elt, DataParameter data_p){
		setBoundElement(step_elt);
		setBoundParameter(data_p);
	}

	public final StepElement getBoundElement() {
		return boundElement;
	}

	public final void setBoundElement(StepElement bound_element) {
		this.boundElement = bound_element;
	}

	public final DataParameter getBoundParameter() {
		return boundParameter;
	}

	public final void setBoundParameter(DataParameter bound_parameter) {
		this.boundParameter = bound_parameter;
	}
	
}