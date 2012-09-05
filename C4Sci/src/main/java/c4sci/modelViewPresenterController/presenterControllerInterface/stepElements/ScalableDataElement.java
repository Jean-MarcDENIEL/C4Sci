package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import c4sci.modelViewPresenterController.presenterControllerInterface.ElementBinding;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

/**
 * ScalableDataElements encapsulate {@link SingleDataStepElement} and add the possibility to 
 * affect a scale to this {@link SingleDataStepElement}.
 * 
 * All other service requests are transmitted to the encapsulated {@link SingleDataStepElement}. 
 * 
 * @author jeanmarc.deniel
 *
 */
public class ScalableDataElement extends SingleDataStepElement {

	private UnitScales				unitScales;
	private SingleDataStepElement	scaledElement;
	
	public ScalableDataElement(SingleDataStepElement single_data, UnitScales units_) {
		setUnitScales(units_);
		scaledElement = single_data;
	}

	@Override
	public final UnitScales getUnits() {
		return unitScales;
	}
	/**
	 * Sets the units corresponding to the encapsulated {@link SingleDataStepElement}
	 * @param unit_scales The unit scale to affect.
	 */
	public final void setUnitScales(UnitScales unit_scales) {
		this.unitScales = unit_scales;
	}

	@Override
	public ElementBinding getSingleBinding() {
		return scaledElement.getSingleBinding();
	}

	@Override
	public boolean isInternallyCoherent() {
		return scaledElement.isInternallyCoherent();
	}

	@Override
	public void ensureCoherentInternalState() {
		scaledElement.ensureCoherentInternalState();
	}

	@Override
	public boolean isEditable() {
		return scaledElement.isEditable();
	}

}
