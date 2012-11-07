package c4sci.modelViewPresenterController.presenterControllerInterface.stepElements;

import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

/**
 * ScalableDataElements encapsulate {@link LogicalSingleDataStepElement} and add the possibility to 
 * affect a scale to this {@link LogicalSingleDataStepElement}.
 * 
 * All other service requests are transmitted to the encapsulated {@link LogicalSingleDataStepElement}. <br>
 * 
 * Sub elements contain the scalable {@link StepElement}.
 * 
 * @author jeanmarc.deniel
 *
 */
public class ScalableDataElement extends LogicalSingleDataStepElement {

	private UnitScales				unitScales;
	
	public ScalableDataElement(SingleDataStepElement single_data, UnitScales units_) {
		super(single_data);
		setUnitScales(units_);
	}
	
	public final SingleDataStepElement getScaledElement(){
		return getSingleElement();
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
}
