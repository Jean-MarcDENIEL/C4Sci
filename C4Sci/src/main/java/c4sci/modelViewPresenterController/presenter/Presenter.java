package c4sci.modelViewPresenterController.presenter;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.JobProcessor;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementActivatedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementAddedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementInactivatedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementSuppressedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepBackwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepForwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.BooleanElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.FloatElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.IntegerElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.InternationalizableTermElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.ThreeDimensionalElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.elementValueChanges.TwoDimensionalElementModificationStepChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.StringValueChange;

public abstract class Presenter implements MvpcLayer<ComponentChange, StepChange>{

	private Map<Component, StepElement> componentElementMap;
	private Map<StepElement, Component> elementComponentMap;


	public Presenter(){
		componentElementMap = new ConcurrentHashMap<Component, StepElement>();
		elementComponentMap	= new ConcurrentHashMap<StepElement, Component>();
	}

	/**
	 * 
	 * @throws NoSuchElementException in the case there is no correspondence
	 */
	public Component getCorrespondingComponent(StepElement step_elt){
		Component _res= elementComponentMap.get(step_elt);
		if (_res == null){
			throw new NoSuchElementException();
		}
		return _res;
	}

	public void setCorrespondence(Component comp_, StepElement step_elt){
		componentElementMap.put(comp_, step_elt);
		elementComponentMap.put(step_elt, comp_);
	}

	/**
	 * 
	 * @throws NoSuchElementException in the case there is no correspondence
	 */
	public StepElement getCorrespondingElement(Component comp_){
		StepElement _res = componentElementMap.get(comp_);
		if (_res == null){
			throw new NoSuchElementException();
		}
		return _res;
	}


	public JobProcessorFactory<ComponentChange, StepChange> getReactiveJobProcessorFactory() {
		JobProcessorFactory<ComponentChange, StepChange> 	_reactive_job_processor_factory = new JobProcessorFactory<ComponentChange, StepChange>();

		_reactive_job_processor_factory.addChangePerformingAbility(BooleanValueChange.class, new JobProcessor<ComponentChange, StepChange>() {
			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				if (BooleanValueChange.class.isInstance(processing_cmd)){
					return reactionToBooleanValueChange((BooleanValueChange)processing_cmd);
				}
				else{
					return null;
				}
			}
		});
		_reactive_job_processor_factory.addChangePerformingAbility(FloatValueChange.class, new JobProcessor<ComponentChange, StepChange>() {
			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				if (FloatValueChange.class.isInstance(processing_cmd)){
					return reactionToFloatValueChange((FloatValueChange)processing_cmd);
				}
				else{
					return null;
				}
			}
		});
		_reactive_job_processor_factory.addChangePerformingAbility(IntegerValueChange.class, new JobProcessor<ComponentChange, StepChange>() {
			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				if(IntegerValueChange.class.isInstance(processing_cmd)){
					return reactionToIntegerValueChange((IntegerValueChange)processing_cmd);
				}
				else{
					return null;
				}
			}
		});
		_reactive_job_processor_factory.addChangePerformingAbility(StringValueChange.class, new JobProcessor<ComponentChange, StepChange>() {

			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				if (StringValueChange.class.isInstance(processing_cmd)){
					return reactionToStringValueChange((StringValueChange)processing_cmd);
				}
				else{
					return null;
				}

			}
		});

		return _reactive_job_processor_factory;
	}

	public JobProcessorFactory<StepChange, ComponentChange> getFeedbackJobProcessorFactory() {
		JobProcessorFactory<StepChange, ComponentChange> _feedback_job_processor_factory = new JobProcessorFactory<StepChange, ComponentChange>();

		return _feedback_job_processor_factory;
	}

	/********************* FEEDBACK METHODS ***************************************************/
	public abstract List<ComponentChange> feedbackToElementActivatedStepChange(ElementActivatedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementAddedStepChange(ElementAddedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementSuppressedStepChange(ElementSuppressedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToStepBackwardStepChange(StepBackwardStepChange step_chg);
	public abstract List<ComponentChange> feedbackToStepForwardStepChange(StepForwardStepChange step_chg);
	public abstract List<ComponentChange> feedbackToElementInactivatedStepChange(ElementInactivatedStepChange step_chg);
	public abstract List<ComponentChange> feedbackToBooleanElementModificationStepChange(BooleanElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToFloatElementModificationStepChange(FloatElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToIntegerElementModificationStepChange(IntegerElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToInternationalizableTermElementModificationStepChange(InternationalizableTermElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToThreeDimensionalElementModificationStepChange(ThreeDimensionalElementModificationStepChange step_chg);
	public abstract List<ComponentChange> feedbackToTwoDimensionElementModificationStepChange(TwoDimensionalElementModificationStepChange step_chg);

	/********************* REACTION METHODS **************************************************/
	public abstract List<StepChange> reactionToBooleanValueChange(BooleanValueChange comp_chg);
	public abstract List<StepChange> reactionToFloatValueChange(FloatValueChange comp_chg);
	public abstract List<StepChange> reactionToIntegerValueChange(IntegerValueChange comp_chg);
	public abstract List<StepChange> reactionToStringValueChange(StringValueChange comp_chg);

	/********************* FACTORY METHODS ***************************************************/
	/**
	 * 
	 * Computes the {@link Command} sequence corresponding to the creation of the {@link StepElement}.<br>
	 * 
	 * @param corresp_component the {@link Component} corresponding to the {@link StepElement} argument.
	 * @param step_elt The {@link StepElement} to analyze.
	 * @param previous_change The {@link Command} that is previous all {@link Command commands} of the result
	 * @return a list of {@link ComponentChange}
	 */
	public abstract List<ComponentChange> computeCreationSequence(Component corresp_component, StepElement step_elt, ComponentChange previous_change);
}
