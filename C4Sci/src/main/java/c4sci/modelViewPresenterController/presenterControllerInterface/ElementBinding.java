package c4sci.modelViewPresenterController.presenterControllerInterface;

import c4sci.data.DataParameter;

/**
 * This interface permit data bindings between {@link StepElement StepElements} and {@link DataParameter DataParameters}. 
 * @author jeanmarc.deniel
 *
 */
public class ElementBinding {
	private StepElement		boundElement;
	
	@SuppressWarnings("unused")
	private ElementBinding(){}
	
	public ElementBinding(StepElement step_elt){
		setBoundElement(step_elt);
	}

	public final StepElement getBoundElement() {
		return boundElement;
	}

	public final void setBoundElement(StepElement bound_element) {
		this.boundElement = bound_element;
	}
	
}
