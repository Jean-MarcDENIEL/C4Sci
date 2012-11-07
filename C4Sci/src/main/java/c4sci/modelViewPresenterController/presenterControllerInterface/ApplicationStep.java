package c4sci.modelViewPresenterController.presenterControllerInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class describes a certain step in a application.<br>
 * It contains some linked data and treatments.<br><br>
 * <b>Step sequence:</b><br>
 * ApplicationSteps can have previous & next steps.<br>
 * <br>
 * <b>Hierarchy of steps:</b><br>
 * ApplicationStep can also be subdivided in sub steps.<br>
 * In this case, sub steps may create a previous/next relationship sequence.<br>
 * <br>
 * <b>Step contents</b><br>
 * Each application step is about a certain number of {@link StepElement elements}. E.g calculations or data.<br>
 * These elements are referenced by integer values whose meaning only depends on the current {@link ApplicationStep}.
 * 
 * @author jeanmarc.deniel
 *
 */
public final class ApplicationStep {

	private ApplicationStep 		nextStep;
	private ApplicationStep 		previousStep;
	private ApplicationStep 		parentStep;
	private List<ApplicationStep>	subSteps;
	
	private InternationalizableTerm	stepName;
	private InternationalizableTerm	stepDescription;
	
	private Map<Integer, StepElement>		stepElementsMap;
	
	public ApplicationStep(InternationalizableTerm step_name, InternationalizableTerm step_descr) {
		nextStep		= null;
		previousStep	= null;
		setParentStep(null);
		subSteps		= Collections.synchronizedList(new ArrayList<ApplicationStep>());
		
		stepName		= step_name;
		stepDescription	= step_descr;
		
		stepElementsMap	= new ConcurrentHashMap<Integer, StepElement>();
	}

	public ApplicationStep getNextStep() {
		return nextStep;
	}

	/**
	 * Creates a next/previous relationship between "this" and the passed argument. <br>
	 * <b>Side effect :</b> "this" and the passed argument are modified.
	 * @param next_step The step to be considered as the next step after "this". Can be <i>null</i>.
	 */
	public void setNextStep(ApplicationStep next_step) {
		this.nextStep = next_step;
		if (next_step != null){
		next_step.previousStep = this;
		}
	}

	public ApplicationStep getPreviousStep() {
		return previousStep;
	}
	/**
	 * Creates a next/previous relationship between "this" and the passed argument.<br>
	 * <b>Side effect :</b> "this" and the passed argument are modified. 
	 * @param previous_step The step to be considered as the previous step before "this". Can be <i>null</i>
	 */
	public void setPreviousStep(ApplicationStep previous_step) {
		this.previousStep = previous_step;
		if (previous_step != null){
			previous_step.nextStep = this;
		}
	}
	/**
	 * Creates a parent / sub step relation ship and ensure a previous/next relationship with the last entered sub steps.<br>
	 * <b>Side effect :</b> "this" and the passed argument are modified as well as existing sub steps.
	 * @param sub_step The sub step to be append as the last sub step, and link to the actual last existing one.
	 */
	public void appendSubStep(ApplicationStep sub_step){
		sub_step.setParentStep(this);
		ApplicationStep _previous_sub_step = null;
		if (subSteps.size() > 0){
			_previous_sub_step = subSteps.get(subSteps.size()-1);
		}
		subSteps.add(sub_step);
		if (_previous_sub_step != null){
			_previous_sub_step.setNextStep(sub_step);
			sub_step.setPreviousStep(_previous_sub_step);
		}
	}
	/**
	 * Creates a parent / sub step relation ship without any previous/next relationship with the last entered sub steps.<br>
	 * <b>Side effect :</b> "this" and the passed argument are modified.
	 * @param sub_step
	 */
	public void addSubStep(ApplicationStep sub_step){
		sub_step.setParentStep(this);
		sub_step.setPreviousStep(null);
		ApplicationStep _previous_sub_step = null;
		if (subSteps.size() > 0){
			_previous_sub_step = subSteps.get(subSteps.size()-1);
			_previous_sub_step.setNextStep(null);
		}
		subSteps.add(sub_step);
	}
	/**
	 * The sub steps are sorted to respect previous/next relationships between them.
	 * @return the sub steps of the current step.
	 */
	public List<ApplicationStep> getSubSteps(){
		return subSteps;
	}
	/**
	 * 
	 * @return The ApplicationStep "this" is sub step or null if there is no one.
	 */
	public ApplicationStep getParentStep() {
		return parentStep;
	}
	/**
	 * Set the passed argument as being the parent step of "this" step.
	 * @param parent_step is not modified.
	 */
	public void setParentStep(ApplicationStep parent_step) {
		this.parentStep = parent_step;
	}
	/**
	 * 
	 * @return The name of the step.
	 */
	public InternationalizableTerm getStepName() {
		return stepName;
	}
	/**
	 * 
	 * @return The description of the step.
	 */
	public InternationalizableTerm getStepDescription() {
		return stepDescription;
	}
	/**
	 * 
	 * @return an iterator to access the elements concerned by "this" step.
	 */
	public Iterator<StepElement> getElementsIterator(){
		return stepElementsMap.values().iterator();
	}
	/**
	 * 
	 * @param elt_key The element key.
	 * @return The element referenced by the given key or null if there is no element referenced by the argument key.
	 */
	public StepElement getElement(int elt_key){
		return stepElementsMap.get(Integer.valueOf(elt_key));
	}
	/**
	 * Adds a {@link StepElement} in "this" ApplicationStep.
	 * @param elt_key The element reference.
	 * @param added_elt The added element.
	 */
	public void putElement(int elt_key, StepElement added_elt){
		stepElementsMap.put(Integer.valueOf(elt_key), added_elt);
	}

}
