package c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges;

import java.awt.Font;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.IntegerChange;
/**
 * This kind of change is based on the AWT definition of {@link Font} style.
 * @author jeanmarc.deniel
 *
 */
public class FontStyleChange extends IntegerChange {

	public FontStyleChange(Component comp_, int font_style, Command parent_cmd) {
		super(comp_, font_style, parent_cmd);
	}

}
