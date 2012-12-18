package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.OneDimensionalChange;

/**
 * Transparency is ranged between 0.0 (opaque) and 1.0 (purely transparent).
 * @author jeanmarc.deniel
 *
 */
public class TransparencyChange extends OneDimensionalChange {
	/**
	 * 
	 * @param comp_id The component identity to change transparency to.
	 * @param transp_value Transparency value in the [0-1] range
	 */
	public TransparencyChange(Component comp_, float transp_value, Command parent_cmd) {
		super(comp_, transp_value, parent_cmd);
	}

}
