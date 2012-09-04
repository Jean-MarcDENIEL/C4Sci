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
		super(single_data.getDataParameter());
		setUnitScales(units_);
		setScaledElement(single_data);
	}

	@Override
	public UnitScales getUnits() {
		return unitScales;
	}
	/**
	 * Sets the units corresponding to the encapsulated {@link SingleDataStepElement}
	 * @param unit_scales The unit scale to affect.
	 */
	public void setUnitScales(UnitScales unit_scales) {
		this.unitScales = unit_scales;
	}
	/**
	 * 
	 * @return The {@link SingleDataStepElement} that has been encapsulated.
	 */
	public SingleDataStepElement getScaledElement() {
		return scaledElement;
	}
	/**
	 * @param scaledElement The {@link SingleDataStepElement} to encapsulate.
	 */
	private void setScaledElement(SingleDataStepElement scaledElement) {
		this.scaledElement = scaledElement;
	}

	@Override
	protected ElementBinding getSingleBinding() {
		return getScaledElement().getSingleBinding();
	}

	@Override
	public boolean isInternallyCoherent() {
		return getScaledElement().isInternallyCoherent();
	}

	@Override
	public void ensureCoherentInternalState() {
		getScaledElement().ensureCoherentInternalState();
	}

	@Override
	public boolean isEditable() {
		return getScaledElement().isEditable();
	}

}
