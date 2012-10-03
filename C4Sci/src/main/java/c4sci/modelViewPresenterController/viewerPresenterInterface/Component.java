package c4sci.modelViewPresenterController.viewerPresenterInterface;

import java.util.Iterator;
import c4sci.data.DataIdentity;
import c4sci.math.geometry.plane.PlaneVector;
import c4sci.modelViewPresenterController.jobs.CannotPerformSuchChangeException;

/**
 * Components are visual elements composing the GUI.<br>
 * <br>
 * This abstract class is made to avoid coupling with GUI implementations.<br>
 * <br>
 * <b>Pattern : </b> This class instantiates the <b>Composite</b> GoF pattern.
 * 
 * @author jeanmarc.deniel
 *
 */
public abstract class Component {
	
	private Component 		parentComponent;
	private DataIdentity	componentIdentity;
	private PlaneVector		relativeUpperLeftCorner;
	private PlaneVector		relativeSize;
	
	public Component(){
		parentComponent 			= null;
		componentIdentity 			= new DataIdentity();
		relativeUpperLeftCorner		= new PlaneVector();
		relativeSize				= new PlaneVector();
	}

	/**
	 * 
	 * @return The parent Component or null if the component has no parent
	 */
	public Component	getParentComponent(){
		return parentComponent;
	}
	/**
	 * Sets the parent Component.
	 * @param comp_parent The Component to consider as the parent of this.
	 */
	public void		setParentComponent(Component comp_parent){
		parentComponent = comp_parent;
	}
	/**
	 * Retrieves coordinates expressed relatively to their parent Component.<br>
	 * The coordinates range from 0 to 1.<br>
	 * 
	 * @return The coordinates of the upper left corner of the Component. 
	 */
	public PlaneVector	getUpperLeftOrigin(){
		return new PlaneVector(relativeUpperLeftCorner);
	}

	/**
	 * Bounds the 2D point to [0-1]²
	 * @param coor_ The point to be modified if its coordinates are out of the [0-1] range.
	 */
	public void 	limitTo01Range(PlaneVector coor_){
		coor_.setX(Math.max(0.0f, Math.min(1.0f, coor_.getX())));
		coor_.setY(Math.max(0.0f, Math.min(1.0f, coor_.getY())));
	}
	
	/**
	 * Set the coordinates of the upper left component corner.<br>
	 * They are expressed relatively to their parent Component.<br>
	 * The coordinates range from 0 to 1.<br>
	 * @param corn_coor The coordinates bounded to [0-1]² range.
	 * */
	public void		setUpperLeftOrigin(final PlaneVector corn_coor){
		relativeUpperLeftCorner.opEquals(corn_coor);
		limitTo01Range(relativeUpperLeftCorner);
	}
	/**
	 * Retrieves the size of the Component as proportion to its parent's size.<br>
	 * The X coordinate corresponds to the width and the Y to the height of the Component.<br>
	 * @return The size ranging from 0 to 1, relatively of the parent Component dimensions.
	 */
	public PlaneVector getSize(){
		return new PlaneVector(relativeSize);
	}
	/**
	 * Retrieves the size of the Component as proportion to its parent's size.<br>
	 * The X coordinate corresponds to the width and the Y to the height of the Component.<br>
	 * The coordinates are bounded to the [0-1] range.
	 * */
	public void 		setSize(final PlaneVector comp_size){
		relativeSize.opEquals(comp_size);
		limitTo01Range(relativeSize);
	}
	/**
	 * Identifies the Component. <br>
	 * The uniqueness and the persistence of the component'ID is granted by the use of {@link c4sci.data.DataIdentity DataIdentity} methods.
	 * @return The Component identification number.
	 */
	public DataIdentity	getIdentity(){
		return componentIdentity;
	}
	/**
	 * Sets the identity of the Component.<br>
	 * It is recommended to use the {@link c4sci.data.DataIdentity#DataIdentity() new DataIdentity()} method 
	 * @param comp_id The identity of the Component
	 */
	public void		setIdentity(DataIdentity comp_id){
		componentIdentity = comp_id;
	}
	/**
	 * 
	 * @return An iterator that gives access to the child components.
	 */
	abstract Iterator<Component> getChildComponentIterator();
	/**
	 * Adds a child component depending on the actual component.
	 * @param child_comp The child component. Its {@link #setParentComponent(Component)} method will be called.
	 * @throws CannotPerformSuchChangeException in the case the actual component does not accept child components.
	 */
	abstract void addChildComponent(Component child_comp) throws CannotPerformSuchChangeException;
	
	/**
	 * Describes whether the Component can have interactions with the user.
	 * @return true if the Component can interact with the user. False otherwise.
	 */
	//abstract boolean isActive();
	/**
	 * Sets the Component to be able to interact with the user.
	 * @param act_ true to make the Component active. False otherwise.
	 */
	//abstract void setActivity(boolean act_);


	
}
