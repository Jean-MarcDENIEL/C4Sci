/**
 * 
 */
package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.math.geometry.space.SpaceVector;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.ThreeDimensionalChange;

/**
 *
 * Colors are expressed in the [0-1] range :<ul><li>X means Red</li><li>Y means Green</li><li>Z means Blue.</li>
 * @author jeanmarc.deniel
 *
 */
public class BackgroundColorChange extends ThreeDimensionalChange {

	private BackgroundColorChange() {
		super(null, null, null);
	}
	public BackgroundColorChange(Component comp_, SpaceVector back_color, Command parent_cmd){
		super(comp_, back_color, parent_cmd);
	}
}
