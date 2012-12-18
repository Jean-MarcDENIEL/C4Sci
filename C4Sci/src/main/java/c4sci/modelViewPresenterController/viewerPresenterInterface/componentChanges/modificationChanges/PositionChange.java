package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.TwoDimensionalChange;

public class PositionChange extends TwoDimensionalChange {

	public PositionChange(Component comp_, PlaneVector point_value, Command parent_cmd) {
		super(comp_, point_value, parent_cmd);
	}

}
