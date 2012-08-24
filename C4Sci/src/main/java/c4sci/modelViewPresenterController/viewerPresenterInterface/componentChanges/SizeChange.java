package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;

public class SizeChange extends TwoDimensionalChange {

	public SizeChange(DataIdentity comp_id, PlaneVector point_value) {
		super(comp_id, point_value);
	}

	@Override
	public ChangeID getChangeID() {
		return ComponentChange.ChangeID.SET_SIZE;
	}

}
