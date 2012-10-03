package c4sci.modelViewPresenterController.presenterControllerInterface;

import c4sci.data.Modifiable;

/**
 * This class permits data bindings between {@link StepElement StepElements} and {@link DataParameter DataParameters}.
 * 
 * @author jeanmarc.deniel
 *
 */
public class ElementBinding {
	private StepElement		boundElement;
	private Modifiable		boundParameter;
	@SuppressWarnings("unused")
	private ElementBinding(){}
	public ElementBinding(StepElement step_elt, Modifiable data_p){
		setBoundElement(step_elt);
		setBoundData(data_p);
	}
	public final StepElement getBoundElement() {
		return boundElement;
	}
	public final void setBoundElement(StepElement bound_element) {
		this.boundElement = bound_element;
	}
	public final Modifiable getBoundData() {
		return boundParameter;
	}
	public final void setBoundData(Modifiable bound_parameter) {
		this.boundParameter = bound_parameter;
	}
}