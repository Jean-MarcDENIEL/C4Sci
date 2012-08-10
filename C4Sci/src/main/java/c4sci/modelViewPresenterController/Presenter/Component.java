package c4sci.modelViewPresenterController.Presenter;

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
	
	public PlaneVector getComponentUpperLeftOrigin();
	public PlaneVector getComponentWidthHeightSize();

	public Component getParentComponent();
	public void setParentComponent(Component parentComponent);

	public Iterator<Component> getChildIterator();

	public boolean isVisible();
	
	int		getComponentID();
	void	setComponentID();
}
