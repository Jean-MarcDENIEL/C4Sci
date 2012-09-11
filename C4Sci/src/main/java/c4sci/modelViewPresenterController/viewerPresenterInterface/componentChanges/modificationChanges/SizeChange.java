package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.TwoDimensionalChange;

public class SizeChange extends TwoDimensionalChange {

	public SizeChange(DataIdentity comp_id, PlaneVector point_value, Command parent_cmd) {
		super(comp_id, point_value, parent_cmd);
	}

}
