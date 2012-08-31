package c4sci.modelViewPresenterController.presenterControllerInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import c4sci.modelViewPresenterController.presenterControllerInterface.scales.UnitScales;

/**
 * StepElements are elements of the application that are concerned at a certain application step.<br>
 * <br>
 * <b>Resource/depending relationship : </b><br>
 * Some elements are linked together by a dependence and coherence relationship :<br>
 * <ul>
 * 	<li> an A element is a resource for a B element : if A is updated, B must be updated,
 *  <li> an A element is a resource for a B element : B won't be coherent if A isn't,
 *  <li> an element is not coherent if one or more of its sub elements are not coherent.  
 * </ul>
 * <b>Warning :</b> It is the responsibility of the API user not to introduce cycles through these relationships.<br>
 * <br>
 * <b>Sub elements</b><br>
 * An element can contain sub elements. These are referenced by integer value which meaning is up to "this" element.<br>
 * <br>
 * <b>Pattern : </b> StepElement structure is based on the <b>Composite</b> GoF pattern.
 * <br>
 * @author jeanmarc.deniel
 *
 */
public abstract class StepElement {

	
	private List<StepElement>			resourceElements;		// elements from which "this" depends
	private List<StepElement>			dependentElements;		// elements that depend on "this"

	
	public StepElement() {
		resourceElements 	= Collections.synchronizedList(new ArrayList<StepElement>());
		dependentElements	= Collections.synchronizedList(new ArrayList<StepElement>());
	}
	/**
	 * Tests whether an element as an internal state that is coherent, independently from<br>
	 * resource and sub elements.
	 * @return true if the internal state of the element is coherent, false otherwise.
	 */
	public abstract boolean isInternallyCoherent(); 
	/**
	 * Tests the global coherence form the point of view of "this" element, 
	 * considering "this" {@link #isInternallyCoherent()}, 
	 * its resource and sub elements {@link #isOverallCoherent()}.
	 * @return true if "this", resource and sub elements all answer true to {@link #isInternallyCoherent()} and {@link #isOverallCoherent()} methods respectively. False otherwise.
	 */
	public final boolean isOverallCoherent(){
		if (!isInternallyCoherent()){
			return false;
		}
		for (Iterator<StepElement> _it=resourceElements.iterator(); _it.hasNext();){
			if (!_it.next().isOverallCoherent()){
				return false;
			}
		}
		for (Iterator<StepElement> _it=getSubElementsIterator(); _it.hasNext();){
			if (!_it.next().isOverallCoherent()){
				return false;
			}
		}
		return true;
	}

	/**
	 * Sets the internal state of "this" element so that {@link #isInternallyCoherent()} answers true.<br>
	 * This method is especially useful when {@link #isInternallyCoherent()} answers false at first time.<br>
	 * This method does not ensure any state, except that it is internally coherent. 
	 */
	public abstract void ensureCoherentInternalState();
	/**
	 * 
	 * @return an iterator that gives access to sub elements.
	 */
	public abstract Iterator<StepElement> getSubElementsIterator();


	/**
	 * 
	 * @return an iterator that gives access to resource elements.
	 */
	public final Iterator<StepElement> getResourcesIterator(){
		return resourceElements.iterator();
	}
	/**
	 * 
	 * @return an iterator that gives access to dependent elements.
	 */
	public final Iterator<StepElement> getDependentsIterator(){
		return dependentElements.iterator();
	}
	/**
	 * 
	 * @return true if the element corresponds to a data that can be modified through an interaction with the element.
	 */
	public abstract boolean isEditable();
	/**
	 * @return The bindings to the data that are directly necessary for the StepElement to work. 
	 */
	public abstract List<ElementBinding> getBindings();
	/**
	 * @return The {@link UnitScales units} in which the StepElement is expressed. Or null if there is no unit to apply to.  
	 */
	public UnitScales getUnits(){
		return null;
	}
}