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
 *  <li> an element is not overall coherent if one or more of its sub elements are not coherent.  
 * </ul>
 * <b>Warning :</b> It is the responsibility of the API user not to introduce cycles through these relationships.<br>
 * <br>
 * <b>Sub elements</b><br>
 * An element can contain sub elements.<br>
 * <br>
 * <b>Importance</b><br>
 * Each StepElement is assigned an importance value, that can be useful for the presenter to improve the application usability.<br>
 * Importance ranges from 0.0 (least important) to 1.0 (most important). Default value is 1.0/<br>
 * Importance is defined relatively to the parent importance. <br>
 * E.g if parent's importance is 0.5 and current element's importance is 0.35,  
 * then it's computed importance compared to other elements is 0.5*0.35=0.175.
 * <br>
 * <b>Pattern : </b> StepElement structure is based on the <b>Composite</b> GoF pattern.
 * <br>
 * @author jeanmarc.deniel
 *
 */
public abstract class StepElement {
	private List<StepElement>			resourceElements;		// elements from which "this" depends
	private List<StepElement>			dependentElements;		// elements that depend on "this"
	private float						elementImportance;		// [0-1]
	public StepElement() {
		resourceElements 	= Collections.synchronizedList(new ArrayList<StepElement>());
		dependentElements	= Collections.synchronizedList(new ArrayList<StepElement>());
		setImportance(1.0f);
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
	 * Indicates whether the {@link StepElement} contains a value that is not included in a sub 
	 * element.
	 *   
	 * @return <i>true</i> if the {@link StepElement} contains this kind of value. 
	 */
	public abstract boolean containsProperValue();
	/**
	 * 
	 * @return a String representation of the proper value included in the current {@link StepElement} or <i>null</i> if there's no such proper value.
	 */
	public abstract String	getProperValue();
	/**
	 * Sets the proper value of the StepElement by parsing the argument.<br>
	 * Has no effect in the case the argument cannot be successfully parsed.
	 * @param str_value the String to parse.
	 */
	public abstract void 	setProperValue(String str_value);
	
	/**
	 * @return an iterator that gives access to sub elements.
	 */
	public abstract Iterator<StepElement> getSubElementsIterator();
	/**
	 * @return an iterator that gives access to resource elements.
	 */
	public final Iterator<StepElement> getResourcesIterator(){
		return resourceElements.iterator();
	}
	/**
	 * @return an iterator that gives access to dependent elements.
	 */
	public final Iterator<StepElement> getDependentsIterator(){
		return dependentElements.iterator();
	}
	/**
	 * Modifies the argument StepElements to create a resource/dependent relation ship between to StepElements.<br>
	 * 
	 * @param resource_elt The element considered as a resource for the other.
	 * @param dependant_elt The element considered as depending from the other.
	 */
	public static final void createResourceDependentRelationship(StepElement resource_elt, StepElement dependant_elt){
		resource_elt.dependentElements.add(dependant_elt);
		dependant_elt.resourceElements.add(resource_elt);
	}
	
	/**
	 * 
	 * @return true if the element corresponds to a data that can be modified through an interaction with the element.
	 */	
	public abstract boolean isEditable();
	/**
	 * @return The bindings to the data that are directly necessary for the StepElement to work.<br>
	 * If there is no element to work on, then the {@link List} will be empty (but not null). 
	 */
	public abstract List<ElementBinding> getBindings();
	/**
	 * @return The {@link UnitScales units} in which the StepElement is expressed. Or null if there is no unit to apply to.  
	 */
	public UnitScales getUnits(){
		return null;
	}
	public final float getImportance() {
		return elementImportance;
	}
	/**
	 * Importance will be limited to the [0.0-1.0] range.
	 * @param element_importance the importance to affect to the element.
	 */
	public final void setImportance(float element_importance) {
		if (element_importance > 1.0f){
			elementImportance = 1.0f;
		}
		else{
			if (element_importance < 0.0f){
				elementImportance = 0.0f;
			}
			else{
				this.elementImportance = element_importance;
			}
		}
	}
}