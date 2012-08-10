package c4sci.modelViewPresenterController.presenter;

import java.util.Iterator;

import c4sci.math.geometry.plane.PlaneVector;

/**
 * Components correspond to visual components.
 * <br>
 * <b>Pattern : </b> As they can be recursively composed of other components, they use the <b>Composite</b> oF pattern. 
 * <br>
 * They are defined relatively to their parent component or to the window if they don't have any parent.
 * <br>
 * Coordinates are expressed as percents.
 * <br>
 * @author jeanmarc.deniel
 *
 */
public interface Component {
	
	PlaneVector getComponentUpperLeftOrigin();
	PlaneVector getComponentWidthHeightSize();

	Component getParentComponent();
	void setParentComponent(Component parentComponent);

	Iterator<Component> getChildIterator();

	boolean isVisible();
	
	int		getComponentID();
	void	setComponentID();
}
