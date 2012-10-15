package c4sci.modelViewPresenterController.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.DataIdentity;
import c4sci.data.internationalization.InternationalizableTerm;
import c4sci.modelViewPresenterController.MvpcLayer;
import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.JobProcessor;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.StepElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementActivatedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementAddedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementInactivatedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementReactiveModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementSuppressedStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepBackwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.StepForwardStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueChanges.BooleanElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueChanges.FloatElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueChanges.IntegerElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueChanges.InternationalizableTermElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueChanges.ThreeDimensionalElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepChanges.ElementValueChanges.TwoDimensionalElementModificationStepChange;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.BooleanDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.FloatDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.IntegerDataElement;
import c4sci.modelViewPresenterController.presenterControllerInterface.stepElements.dataParameterDataElements.LabelDataElement;
import c4sci.modelViewPresenterController.viewerPresenterInterface.Component;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily;
import c4sci.modelViewPresenterController.viewerPresenterInterface.ComponentFamily.StandardComponentSet;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.generics.TwoDimensionalChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.BooleanValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.FloatValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.IntegerValueChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.LabelChange;
import c4sci.modelViewPresenterController.viewerPresenterInterface.componentChanges.modificationChanges.StringValueChange;

public abstract class Presenter implements MvpcLayer<ComponentChange, StepChange>{

	private Map<Component, StepElement> componentElementMap;
	private Map<StepElement, Component> elementComponentMap;


	public Presenter(){
		componentElementMap = new ConcurrentHashMap<Component, StepElement>();
		elementComponentMap	= new ConcurrentHashMap<StepElement, Component>();
	}


	public Component getCorrespondingComponent(StepElement step_elt) throws NoSuchElementException{
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

	public StepElement getCorrespondingElement(Component comp_) throws NoSuchElementException{
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
				return reactionToBooleanValueChange(processing_cmd);
			}
		});
		_reactive_job_processor_factory.addChangePerformingAbility(FloatValueChange.class, new JobProcessor<ComponentChange, StepChange>() {
			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				return reactionToFloatValueChange(processing_cmd);
			}
		});
		_reactive_job_processor_factory.addChangePerformingAbility(IntegerValueChange.class, new JobProcessor<ComponentChange, StepChange>() {
			@Override
			public List<StepChange> processJob(ComponentChange processing_cmd) {
				return reactionToIntegerValueChange(processing_cmd);
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
