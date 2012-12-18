package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.OneDimensionalChange;

public class FontSizeChange extends OneDimensionalChange {

	/**
	 * 
	 * @param comp_id The modified component identity.
	 * @param font_size The font size in point.
	 */
	public FontSizeChange(Component comp_, float font_size, Command parent_cmd) {
		super(comp_, font_size, parent_cmd);
	}

}
